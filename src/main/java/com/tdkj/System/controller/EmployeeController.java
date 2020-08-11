package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.EmployeeStatusEnmu;
import com.tdkj.System.Enum.EmployeeTypeEnmu;
import com.tdkj.System.Enum.FileTypeEnmu;
import com.tdkj.System.Enum.UserStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.entity.User;
import com.tdkj.System.entity.VO.EmployeeVO;
import com.tdkj.System.service.*;
import com.tdkj.System.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.*;

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


    private String upload ="/upload/images/";

    /**
     * @Author houxuyang
     * @Description //跳转员工列表页面
     * @Date 14:24 2020/8/10
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/goemployee")
    public String goemployee() {
        return "page/employee/employeelist";
    }

    //跳转到人事档案界面
    @RequestMapping("/renshidangan")
    public String renshidangan() {
        return "page/employee/danganlist";
    }

    //跳转到人事档案详情界面
    @RequestMapping("/renshidangandetail")
    public String renshidangandetail() {
        return "page/employee/dangandetail";
    }

    @ResponseBody
    @RequestMapping("/selectemployee")
    public OAResponseList selectemployee(Integer page, Integer limit,String name,String idcardnumber) {
        log.info("selectemployee");
        //获取当前用户的所在公司
        Corpbasicinfo corpbasicinfo=corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        PageHelper.startPage(page,limit,true);
        List<EmployeeVO> employeeVOList=employeeService.queryAllEmployee(corpbasicinfo.getCorpid(),name,idcardnumber);
        PageInfo<EmployeeVO> pageInfo=new PageInfo<>(employeeVOList);
        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }

    @RequestMapping("goadd")
    public String goadd() {
        return "page/employee/addemployee";
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
                          String entrydate, String regulardate,@RequestParam( value ="headimage",required = false) MultipartFile headimage,Integer politicstype,
                          String cellphone,String email,String authorizationcode,String urgentlinkman,String urgentlinkmanphone,
                          @RequestParam(value ="positiveidcardimage",required = false) MultipartFile positiveidcardimage,
                          @RequestParam(value ="negativeidcardimage",required = false) MultipartFile negativeidcardimage,
                          @RequestParam(value ="file",required = false) MultipartFile laborcontract) throws Exception {
        User olduser = userService.findByName(username);
        if (null != olduser) {
            return OAResponse.setResult(HTTP_RNS_CODE_500,"用户名已存在");
        }
        Corpbasicinfo corpbasicinfo=corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        Fileinfo fileinfo =new Fileinfo();
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
        if(null!=laborcontract&&laborcontract.getSize()>0){
            //合同
            String laborcontractUrl = fileuploadUtils.Fileupload(laborcontract,uploadFile,"劳动合同",name);
            log.info("附件上传成功");
            fileinfo.setCorpid(corpbasicinfo.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Labor_contract.getCode());
            fileinfo.setName(name+"劳动合同");
            fileinfo.setUrl(laborcontractUrl);
            fileinfo.setCreatedate(new Date());
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
        employee.setSuperid(1); //上级领导ID
        employee.setName(name);
        employee.setJobtitle(jobtitle);
        employee.setIdcardtype(idcardtype);
        employee.setIdcardnumber(idcardnumber);
        employee.setStatus(EmployeeStatusEnmu.on_the_job.getCode()); //在职
        employee.setGender(gender);
        employee.setAge(age);
        employee.setNation(nation);
        employee.setEdulevel(edulevel);
        if(null!=birthday&&birthday.length()>0){
            employee.setBirthday(DateUtil.getformatDate(birthday));
        }
        employee.setAddress(address);
        if(null!=entrydate&&entrydate.length()>0){
            employee.setEntrydate(DateUtil.getformatDate(entrydate));
        }
        if(null!=regulardate&&regulardate.length()>0){
            employee.setRegulardate(DateUtil.getformatDate(regulardate));
        }
        if(null!=headimage&&headimage.getSize()>0){
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
        if(null!=positiveidcardimage&&positiveidcardimage.getSize()>0){
            //上传身份证正面照，并返回url
            String positiveidcardimageurl = fileuploadUtils.Fileupload(positiveidcardimage,uploadImageFolder,"身份证正面照",name);
            employee.setPositiveidcardimageurl(positiveidcardimageurl);
            log.info("身份证正面上传成功");
        }
        if(null!=negativeidcardimage&&negativeidcardimage.getSize()>0){
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

    /**
     * @Author houxuyang
     * @Description //跳转个人信息页面
     * @Date 14:25 2020/8/10
     * @Param []
     * @return java.lang.String
     **/
  /*  @RequestMapping("/goemployeeinfo")
    public String goemployeeinfo() {
        return "page/employee/employeeinfo";
    }
*/
    /**
     * @Author houxuyang
     * @Description //跳转个人信息页面 获取员工个人信息返回到页面
     * @Date 14:32 2020/8/11
     * @Param []
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping("/goemployeeinfo")
    public ModelAndView goemployeeinfo(){
        log.info("goemployeeinfo");
        EmployeeVO employeeVO =employeeService.queryemployeeVOById(ShiroUtils.getPrincipal().getEmployeeid());
        employeeVO.setHeadimageurl(upload+employeeVO.getHeadimageurl());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/employee/employeeinfo");
        modelAndView.addObject("employee",employeeVO);
        return modelAndView;
    }


    /**
     * @Author houxuyang
     * @Description //修改员工头像
     * @Date 14:33 2020/8/11
     * @Param [headimage]
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/updateemployeeimage")
    public OAResponse updateemployeeimage(@RequestParam( value ="headimage",required = false) MultipartFile headimage) {
        log.info("修改头像");
        Employee employee =this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        String headimageurl=null;
        if(null!=headimage&&headimage.getSize()>0){
            //上传头像照 并返回url
            FileuploadUtils fileuploadUtils =new FileuploadUtils();
            //删除原始头像
            fileuploadUtils.Filedelete(uploadImageFolder,employee.getHeadimageurl());
            //上传新头像
            headimageurl = fileuploadUtils.Fileupload(headimage,uploadImageFolder,"头像",employee.getName());
            employee.setHeadimageurl(headimageurl);
            log.info("头像修改成功");
            employeeService.update(employee);
            return OAResponse.setResult(HTTP_RNS_CODE_200,"头像修改成功",upload+headimageurl);
        }
        return OAResponse.setResult(HTTP_RNS_CODE_500,"头像修改失败，未找到头像");
    }




    @ResponseBody
    @RequestMapping("/updateemployee")
    public OAResponse updateemployee(Integer employeeid,String name,Integer gender,Integer age,String cellphone,
                                     String idcardnumber,String email ,String address) {
        log.info("修改个人信息");
        Employee employee =this.employeeService.queryById(employeeid);
        if(null!=employee){
            employee.setName(name);
            employee.setGender(gender);
            employee.setAge(age);
            employee.setCellphone(cellphone);
            employee.setIdcardnumber(idcardnumber);
            employee.setEmail(email);
            employee.setAddress(address);
            employeeService.update(employee);
            return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_SUCCESS);
        }
        return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_FAULT);
    }


}