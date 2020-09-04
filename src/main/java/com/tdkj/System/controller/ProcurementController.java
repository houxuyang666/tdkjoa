package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.FileTypeEnmu;
import com.tdkj.System.Enum.ProcurementStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.FileinfoService;
import com.tdkj.System.service.ProFlowService;
import com.tdkj.System.service.ProcurementService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.FileuploadUtils;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProFlowService proflowService;

    @Value("${file.uploadFile}")
    private String uploadFile;

    private static final String desc = "采购订单";


    @RequestMapping("/goselectpro")
    public String goselectpro() {
        return "page/procurement/prolist";
    }

    @ResponseBody
    @RequestMapping("/selectpro")
    public OAResponseList selectpro(Integer page, Integer limit) {
        //如果是超级管理员那么可以查询所有 ，但是公司需要区分
        PageHelper.startPage(page, limit, true);
        /*根据用户id查询所申请的采购单*/
        List<ProcurementVO> procurementList = procurementService.queryByApplicantId(ShiroUtils.getPrincipal().getUserid());
        PageInfo<ProcurementVO> pageInfo = new PageInfo<>(procurementList);

        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }


    @RequestMapping("/goadd")
    public String goadd() {
        return "page/procurement/addpro";
    }


    /*@Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String prodate, Integer protype, String goodsname, String unit, String type, Integer number, BigDecimal price,
                          BigDecimal totalamount, String prodesc,@RequestParam("file") MultipartFile file ) throws  Exception {
        Employee employee =this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Fileinfo fileinfo =new Fileinfo();
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
       if(null!=file&&file.getSize()>0){
            //合同
            String fileUrl = fileuploadUtils.Fileupload(file,uploadFile,desc,goodsname);
            log.info("附件上传成功");
            fileinfo.setCorpid(employee.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Procurement_contract.getCode());
            fileinfo.setName(goodsname+desc);
            fileinfo.setUrl(fileUrl);
            fileinfo.setCreatedate(new Date());
            fileinfo= fileinfoService.insert(fileinfo);
            log.info("附件插入成功");
        }
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
        procurement.setStatus(ProcurementStatusEnmu.Under_review.getCode());
        procurement.setFileinfoid(fileinfo.getFileinfoid());
        procurement.setCreatedate(new Date());
        *//*插入采购订单表*//*
        Procurement insert = this.procurementService.insert(procurement);
        *//*直接启动流程实例*//*
        this.proflowService.startProcess(insert.getProid());
        *//*启动后通过查询流程实例的business_key 找到流程定义实例 再通过流程实例*//*
        //拼接流程定义Key
        String processDefinitionKey = "LeavebillOr";

        try{
            String businessKey =processDefinitionKey+":"+insert.getProid();
            Execution execution = this.runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
            Task task = this.taskService.createTaskQuery().executionId(execution.getProcessInstanceId()).singleResult();
            *//*直接跳过自己提交申请的步骤 提交到上级领导*//*
            this.proflowService.completeTask(insert.getProid(),task.getId(),"提交申请","提交");
        }catch (Exception e){
            return OAResponse.setResult(500,"您没有上级，无法添加采购订单");
        }

        return OAResponse.setResult(200,ADD_SUCCESS);
    }*/

    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String prodate, Integer protype, String goodsname, String unit, String type, Integer number, BigDecimal price,
                          BigDecimal totalamount, String prodesc, @RequestParam("file") MultipartFile file) throws Exception {
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Fileinfo fileinfo = new Fileinfo();
        FileuploadUtils fileuploadUtils = new FileuploadUtils();
        if (null != file && file.getSize() > 0) {
            //合同
            String fileUrl = fileuploadUtils.Fileupload(file, uploadFile, desc, goodsname);
            log.info("附件上传成功");
            fileinfo.setCorpid(employee.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Procurement_contract.getCode());
            fileinfo.setName(goodsname + desc);
            fileinfo.setUrl(fileUrl);
            fileinfo.setCreatedate(new Date());
            fileinfo = fileinfoService.insert(fileinfo);
            log.info("附件插入成功");
        }
        Procurement procurement = new Procurement();
        //生成4为随机数 第二个参数为是否要字母 第三个参数是否要数字
        String code = RandomStringUtils.random(4, true, true);
        procurement.setProid(DateUtil.getformatDate(new Date()) + code);
        procurement.setCorpid(employee.getCorpid());
        procurement.setProdate(DateUtil.formatDate(prodate));
        procurement.setProtype(protype);
        procurement.setGoodsname(goodsname);
        procurement.setUnit(unit);
        procurement.setType(type);
        procurement.setNumber(number);
        procurement.setPrice(price);
        if (String.valueOf(totalamount).length() > 7) {
            return OAResponse.setResult(500, "金额太大，请分批提交");
        }
        procurement.setTotalamount(totalamount);
        procurement.setProdesc(prodesc);
        procurement.setApplicantid(ShiroUtils.getPrincipal().getUserid());
        procurement.setApplicationdeptid(employee.getDepartmentid());
        procurement.setStatus(ProcurementStatusEnmu.Under_review.getCode());
        procurement.setFileinfoid(fileinfo.getFileinfoid());
        procurement.setCreatedate(new Date());
        /*插入采购订单表*/
        Procurement insert = this.procurementService.insert(procurement);
        /*直接启动流程实例*/
        this.proflowService.startProcess(insert.getProid());
        /*启动后通过查询流程实例的business_key 找到流程定义实例 再通过流程实例*/
        //拼接流程定义Key
        String processDefinitionKey = "Pro";

        try {
            String businessKey = processDefinitionKey + ":" + insert.getProid();
            Execution execution = this.runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
            Task task = this.taskService.createTaskQuery().executionId(execution.getProcessInstanceId()).singleResult();
            /*直接跳过自己提交申请的步骤 提交到上级领导*/
            this.proflowService.completeTask(insert.getProid(), task.getId(), "提交申请", "提交");
        } catch (Exception e) {
            return OAResponse.setResult(500, "您没有上级，无法添加采购订单");
        }

        return OAResponse.setResult(200, ADD_SUCCESS);
    }


    @RequestMapping("/goupdate")
    public ModelAndView goupdate(String proid) {
        ProcurementVO procurementVO = this.procurementService.queryProVOByProId(proid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/procurement/updatepro");
        modelAndView.addObject("procurement", procurementVO);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(String proid, String prodate, Integer protype, String goodsname, String unit, String type, Integer number,
                             BigDecimal price, BigDecimal totalamount, String prodesc) throws Exception {
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
        return OAResponse.setResult(200, UPDATE_SUCCESS);
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(String proid) {
        Procurement procurement = procurementService.queryById(proid);
        /*判断是否有附件 如果有也一起删除附件 如果没有只删除订单*/
        if (null != procurement.getFileinfoid()) {
            this.procurementService.deleteById(proid);
            this.fileinfoService.deleteById(procurement.getFileinfoid());
        } else {
            this.procurementService.deleteById(proid);
        }
        return OAResponse.setResult(200, REMOVE_SUCCESS);
    }


}