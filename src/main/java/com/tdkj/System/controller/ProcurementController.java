package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.ProcurementStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.FileinfoService;
import com.tdkj.System.service.ProcurementService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.FileuploadUtils;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultType.*;

/**
 * (Procurement)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:50:58
 */
@Slf4j
@Controller
@RequestMapping("procurement")
public class ProcurementController {
    /**
     * 服务对象
     */
    @Resource
    private ProcurementService procurementService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FileinfoService fileinfoService;

    @Value("${file.uploadFile}")
    private String uploadFile;




    @RequestMapping("/goselectpro")
    public String goselectpro() {
        return "page/prolist";
    }

    @ResponseBody
    @RequestMapping("/selectpro")
    public OAResponseList selectpro(Integer page, Integer limit) {
        log.info("selectleavebill");
        //如果是超级管理员那么可以查询所有 ，但是公司需要区分
        PageHelper.startPage(0,10,true);
        /*根据用户id查询所申请的采购单*/
        List<ProcurementVO> procurementList=procurementService.queryByApplicantId(ShiroUtils.getPrincipal().getUserid());
        PageInfo<ProcurementVO> pageInfo=new PageInfo<>(procurementList);

        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }


    @RequestMapping("/goadd")
    public String goadd() {
        return "page/procurement/addpro";
    }


    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String prodate, Integer protype, String goodsname, String unit, String type, Integer number, BigDecimal price,
                          BigDecimal totalamount, String prodesc ) throws  Exception { /*@RequestParam("file") MultipartFile file*/
        Employee employee =this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        String desc ="采购订单";
        Fileinfo fileinfo =new Fileinfo();
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
       /* if(0!=file.getSize()){
            //合同
            String fileUrl = fileuploadUtils.Fileupload(file,uploadFile,"采购订单",goodsname);
            log.info("附件上传成功");
            fileinfo.setCorpid(employee.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Procurement_contract.getCode());
            fileinfo.setName(goodsname+desc);
            fileinfo.setUrl(fileUrl);
            fileinfo.setCreatdate(new Date());
            fileinfo= fileinfoService.insert(fileinfo);
            log.info("附件插入成功");
        }*/
        Procurement procurement =new Procurement();
        //生成4为随机数 第二个参数为是否要字母 第三个参数是否要数字
        String code= RandomStringUtils.random(4, true, true);
        procurement.setProid(DateUtil.getformatDate(new Date())+code);
        procurement.setCorpid(employee.getCorpid());
        procurement.setProdate(DateUtil.formatDate(prodate));
        procurement.setProtype(protype);
        procurement.setGoodsname(goodsname);
        procurement.setUnit(unit);
        procurement.setType(type);
        procurement.setNumber(number);
        procurement.setPrice(price);
        procurement.setTotalamount(totalamount);
        procurement.setProdesc(prodesc);
        procurement.setApplicantid(ShiroUtils.getPrincipal().getUserid());
        procurement.setApplicationdeptid(employee.getDepartmentid());
        procurement.setStatus(ProcurementStatusEnmu.To_audit.getCode());
        procurement.setFileinfoid(fileinfo.getFileinfoid());
        procurement.setCreatedate(new Date());

        this.procurementService.insert(procurement);
        return OAResponse.setResult(200,ADD_SUCCESS);
    }




    @RequestMapping("/goupdate")
    public ModelAndView goupdate(String proid){
        ProcurementVO procurementVO = this.procurementService.queryProVOByProId(proid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/procurement/updatepro");
        modelAndView.addObject("procurement",procurementVO);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(String proid,String prodate, Integer protype, String goodsname, String unit, String type, Integer number,
                             BigDecimal price, BigDecimal totalamount, String prodesc) throws Exception{
        Procurement procurement = this.procurementService.queryById(proid);
        procurement.setProdate(DateUtil.formatDate(prodate));
        procurement.setProtype(protype);
        procurement.setGoodsname(goodsname);
        procurement.setUnit(unit);
        procurement.setType(type);
        procurement.setNumber(number);
        procurement.setPrice(price);
        procurement.setTotalamount(totalamount);
        procurement.setProdesc(prodesc);
        procurement.setModifydate(new Date());
        this.procurementService.update(procurement);
        return OAResponse.setResult(200,UPDATE_SUCCESS);
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse add(String proid){
        Procurement procurement = procurementService.queryById(proid);
        /*判断是否有附件 如果有也一起删除附件 如果没有只删除订单*/
        if (null!=procurement.getFileinfoid()){
            this.procurementService.deleteById(proid);
            this.fileinfoService.deleteById(procurement.getFileinfoid());
        }else{
            this.procurementService.deleteById(proid);
        }
        return OAResponse.setResult(200,ADD_SUCCESS);
    }


}