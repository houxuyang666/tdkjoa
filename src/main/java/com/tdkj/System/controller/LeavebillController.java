package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.entity.VO.LeavebillVO;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.LeavebillService;
import com.tdkj.System.service.WorkFlowService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.tdkj.System.common.OAResultType.*;

/**
 * (Leavebill)表控制层
 *
 * @author makejava
 * @since 2020-07-23 21:08:49
 */
@Slf4j
@Controller
@RequestMapping("leavebill")
public class LeavebillController {
    /**
     * 服务对象
     */
    @Resource
    private LeavebillService leavebillService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    private String processDefinitionKey = "Leavebill";

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Leavebill selectOne(Integer id) {
        return this.leavebillService.queryById(id);
    }


    @RequestMapping("goselectleavebill")
    public String goselectleavebill() {
        return "page/leavebill/leavebilllist";
    }

    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询考勤申请
     * @Date 10:45 2020/8/14
     * @Param [page, limit]
     **/
    @ResponseBody
    @RequestMapping("/selectleavebill")
    public OAResponseList selectleavebill(Integer page, Integer limit) {
        log.info("selectleavebill");
        /*如果是超级管理员那么可以查询所有 ，但是公司需要区分*/
        //获取当前用户的id
        Leavebill leavebill = new Leavebill();
        leavebill.setUserid(ShiroUtils.getPrincipal().getUserid());
        PageHelper.startPage(page, limit, true);
        List<Leavebill> leavebillList = leavebillService.queryAllLeavebill(leavebill);
        PageInfo<Leavebill> pageInfo = new PageInfo<>(leavebillList);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }


    @RequestMapping("goadd")
    public String goadd() {
        return "page/leavebill/addleavebill";
    }


    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String title, String content, Double days, String leavetime) throws Exception {
        log.info("addleavebill");
        /*请假流程单 生成时状态为未申请*/
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Leavebill leavebill = new Leavebill(title, content, days, DateUtil.formatDate(leavetime), AuditStatusEnmu.Under_review.getCode(), ShiroUtils.getPrincipal().getUserid(), employee.getCorpid());
        Leavebill insert = leavebillService.insert(leavebill);
        this.workFlowService.startProcess(insert.getId());
        /*启动后通过查询流程实例的business_key 找到流程定义实例 再通过流程实例*/
        //拼接流程定义Key
        //String processDefinitionKey = "LeavebillOr";
        String businessKey = processDefinitionKey + ":" + insert.getId();
        Execution execution = this.runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
        Task task = this.taskService.createTaskQuery().executionId(execution.getProcessInstanceId()).singleResult();
        this.workFlowService.completeTask(insert.getId(), task.getId(), "提交申请", "提交");
        return OAResponse.setResult(200, ADD_SUCCESS);
    }

    @RequestMapping("goupdate")
    public String goupdate() {
        return "page/leavebill/updateleavebill";
    }

    //直接提交并启动 走不到修改
    /*@ResponseBody
    @RequestMapping("/update")
    public OAResponse update(Integer id, String title, String content, Double days, String leavetime) throws Exception {
        log.info("updateleavebill");
        *//*修改请假单内容*//*
        Leavebill leavebill = new Leavebill(title, content, days, DateUtil.formatDate(leavetime), AuditStatusEnmu.To_audit.getCode(), ShiroUtils.getPrincipal().getUserid());
        leavebill.setId(id);
        leavebillService.update(leavebill);
        return OAResponse.setResult(0, UPDATE_SUCCESS);
    }*/

    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer id) {
        log.info("deleteleavebill");
        /*删除请假单*/
        leavebillService.deleteById(id);
        return OAResponse.setResult(0, REMOVE_SUCCESS);
    }


    /**
     * @return java.lang.String
     * @Author houxuyang
     * @Description //跳转页面
     * @Date 16:26 2020/8/14
     * @Param []
     **/
    @RequestMapping("goapproval")
    public String goapproval() {
        return "page/leavebill/approvallist";
    }


    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询本公司考勤申请
     * @Date 10:45 2020/8/14
     * @Param [page, limit]
     **/
    @ResponseBody
    @RequestMapping("/selectapproval")
    public OAResponseList selectapproval(Integer page, Integer limit, String deptname, String name) {
        log.info("selectapproval");
        /*查询本公司所有的考勤申请*/
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        LeavebillVO leavebillVO = new LeavebillVO();
        if (null != deptname) {
            leavebillVO.setDeptname(deptname);
        }
        if (null != name) {
            leavebillVO.setName(name);
        }
        leavebillVO.setCorpid(employee.getCorpid());
        PageHelper.startPage(page, limit, true);
        /*根据条件查询该公司的所有考勤申请*/
        List<LeavebillVO> leavebillVOList = leavebillService.queryAllByLeavebillVO(leavebillVO);
        PageInfo<LeavebillVO> pageInfo = new PageInfo<>(leavebillVOList);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }
}