package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.EmployeeStatusEnmu;
import com.tdkj.System.Enum.EmployeeTypeEnmu;
import com.tdkj.System.Enum.FileTypeEnmu;
import com.tdkj.System.Enum.UserStatusEnmu;
import com.tdkj.System.activiti.TestParallelGateWay;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.*;
import com.tdkj.System.entity.VO.EmployeeVO;
import com.tdkj.System.service.*;
import com.tdkj.System.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.ADD_SUCCESS;
import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * (Employee)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
@Slf4j
@Controller
@RequestMapping("employee")
@PropertySource("classpath:application-dev.properties")//此处路径需要按需修改
public class EmployeeController {
    /**
     * 服务对象
     */
    @Resource
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private FileinfoService fileinfoService;
    @Autowired
    private CorpbasicinfoService corpbasicinfoService;

    @Value("${file.uploadImageFolder}")
    private String uploadImageFolder;

    @Value("${file.uploadFile}")
    private String uploadFile;



    @RequestMapping("/goemployee")
    public String goemployee() {
        return "page/employeelist";
    }

    @ResponseBody
    @RequestMapping("/selectemployee")
    public OAResponseList selectemployee(Integer page, Integer limit) {
        log.info("selectemployee");
        //获取当前用户的所在公司
        Corpbasicinfo corpbasicinfo=corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        PageHelper.startPage(page,limit,true);
        List<EmployeeVO> employeeVOList=employeeService.queryAllEmployee(corpbasicinfo.getCorpid());
        PageInfo<EmployeeVO> pageInfo=new PageInfo<>(employeeVOList);
        Log log = LogUtils.setLog("查看用户");
        logService.insert(log);
        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }

    @RequestMapping("goadd")
    public String goadd() {
        return "page/table/addemployee";
    }

    /**
     * @Author houxuyang
     * @Description //添加新入职员工，上传头像，身份证照片，合同等信息
     * @Date 13:11 2020/7/20
     * @Param [username, password, corpid, departmentid, name, jobtitle, idcardtype, idcardnumber, gender, age, nation, edulevel, birthday, address, entrydate, regulardate, headimage, politicstype, cellphone, email, authorizationcode, urgentlinkman, urgentlinkmanphone, positiveidcardimage, negativeidcardimage, laborcontract]
     * @return com.tdkj.System.common.OAResponse
     **/
    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String username,
                          Integer departmentid,String name, String jobtitle,Integer idcardtype,String idcardnumber,
                          Integer gender,Integer age,String nation,Integer edulevel,String birthday,String address,
                          String entrydate, String regulardate,@RequestParam("headimage") MultipartFile headimage,Integer politicstype,
                          String cellphone,String email,String authorizationcode,String urgentlinkman,String urgentlinkmanphone,
                          @RequestParam("positiveidcardimage") MultipartFile positiveidcardimage,
                          @RequestParam("negativeidcardimage") MultipartFile negativeidcardimage,
                          @RequestParam("laborcontract") MultipartFile laborcontract) throws Exception {
        User olduser = userService.findByName(username);
        if (null != olduser) {
            return OAResponse.setResult(HTTP_RNS_CODE_500,"用户名已存在");
        }
        Corpbasicinfo corpbasicinfo=corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        Fileinfo fileinfo =new Fileinfo();
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
        if(0!=laborcontract.getSize()){
            //合同
            String laborcontractUrl = fileuploadUtils.Fileupload(laborcontract,uploadFile,"劳动合同",name);
            log.info("附件上传成功");
            fileinfo.setCorpid(corpbasicinfo.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Labor_contract.getCode());
            fileinfo.setName(name+"劳动合同");
            fileinfo.setUrl(laborcontractUrl);
            fileinfo.setCreatdate(new Date());
            fileinfo= fileinfoService.insert(fileinfo);
            log.info("附件插入成功");
        }
        Employee employee =new Employee();
        if(null!=fileinfo.getFileinfoid()){
            employee.setFileinfoid(fileinfo.getFileinfoid());
        }
        employee.setCorpid(corpbasicinfo.getCorpid());
        employee.setDepartmentid(departmentid);
        /*上级领导的employee的ID*/
        employee.setSuperid(1);
        employee.setName(name);
        employee.setJobtitle(jobtitle);
        employee.setIdcardtype(idcardtype);
        employee.setIdcardnumber(idcardnumber);
        employee.setStatus(EmployeeStatusEnmu.on_the_job.getCode());
        employee.setGender(gender);
        employee.setAge(age);
        employee.setNation(nation);
        employee.setEdulevel(edulevel);
        employee.setBirthday(DateUtil.getformatDate(birthday));
        employee.setAddress(address);
        employee.setEntrydate(DateUtil.getformatDate(entrydate));
        employee.setRegulardate(DateUtil.getformatDate(regulardate));
        if(0!=headimage.getSize()){
            //上传头像照 并返回url
            String headimageurl = fileuploadUtils.Fileupload(headimage,uploadImageFolder,"头像",name);
            employee.setHeadimageurl(headimageurl);
            log.info("头像上传成功");
        }
        employee.setPoliticstype(politicstype);
        employee.setCellphone(cellphone);
        employee.setEmail(email);
        employee.setAuthorizationcode(authorizationcode);
        employee.setUrgentlinkman(urgentlinkman);
        employee.setUrgentlinkmanphone(urgentlinkmanphone);
        if(0!=positiveidcardimage.getSize()){
            //上传身份证正面照，并返回url
            String positiveidcardimageurl = fileuploadUtils.Fileupload(positiveidcardimage,uploadImageFolder,"身份证正面照",name);
            employee.setPositiveidcardimageurl(positiveidcardimageurl);
            log.info("身份证正面上传成功");
        }
        if(0!=negativeidcardimage.getSize()){
            //上传身份证反面照，并返回url
            String negativeidcardimageurl = fileuploadUtils.Fileupload(negativeidcardimage,uploadImageFolder,"身份证反面照",name);
            employee.setNegativeidcardimageurl(negativeidcardimageurl);
            log.info("身份证反面上传成功");
        }
        employee.setCreatedate(new Date());
        employee=employeeService.insert(employee);
        log.info("员工信息插入成功");
        User user =new User();
        if(null!=employee.getEmployeeid()){
            user.setEmployeeid(employee.getEmployeeid());
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUsername(username);
        user.setPassword(Md5Util.Md5Password(uuid,"123456"));
        user.setStatus(UserStatusEnmu.Normal.getCode());
        user.setSalt(uuid);
        //后期可修改
        user.setRoleid(EmployeeTypeEnmu.Admin.getCode());
        user.setCreatedate(new Date());
        userService.insert(user);
        log.info("账号生成成功");
        /*使用do while会更好*/
        int value=0;
        int i=0;
        do{
            value=EmailUtils.sandEmail("smtp.163.com","houxuyang0801@163.com","KXIRPGFLESJBQYAO",email,corpbasicinfo.getCorpname(),"尊敬的"+name+"你好！ 欢迎加入"+corpbasicinfo.getCorpname()+"，您的职位为"+jobtitle+",OA地址为："+"固定网址"+";账号为："+username+";密码为：123456;请尽快登录修改密码");
            if(1==value){
                log.info("邮件发送成功");
                break;
            }else{
                i++;
                if(i==3){
                    log.info("发送三次失败");
                    break;
                }
            }
        }while(1!=value);
        return OAResponse.setResult(HTTP_RNS_CODE_200,ADD_SUCCESS);
    }
}