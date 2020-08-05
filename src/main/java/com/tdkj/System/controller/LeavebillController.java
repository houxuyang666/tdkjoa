package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Leavebill;
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

    @ResponseBody
    @RequestMapping("/selectleavebill")
    public OAResponseList selectleavebill(Integer page, Integer limit) {
        log.info("selectleavebill");
        /*如果是超级管理员那么可以查询所有 ，但是公司需要区分*/
        //获取当前用户的id
        Leavebill leavebill = new Leavebill();
        leavebill.setUserid(ShiroUtils.getPrincipal().getUserid());
        PageHelper.startPage(0, 10, true);
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

        Leavebill leavebill = new Leavebill(title, content, days, DateUtil.formatDate(leavetime), AuditStatusEnmu.Under_review.getCode(), ShiroUtils.getPrincipal().getUserid());
        Leavebill insert = leavebillService.insert(leavebill);
        this.workFlowService.startProcess(insert.getId());
        /*启动后通过查询流程实例的business_key 找到流程定义实例 再通过流程实例*/
        //拼接流程定义Key
        String processDefinitionKey = "LeavebillOr";
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

    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(Integer id, String title, String content, Double days, String leavetime) throws Exception {
        log.info("updateleavebill");
        /*修改请假单内容*/
        Leavebill leavebill = new Leavebill(title, content, days, DateUtil.formatDate(leavetime), AuditStatusEnmu.To_audit.getCode(), ShiroUtils.getPrincipal().getUserid());
        leavebill.setId(id);
        leavebillService.update(leavebill);
        return OAResponse.setResult(0, UPDATE_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer id) {
        log.info("deleteleavebill");
        /*删除请假单*/
        leavebillService.deleteById(id);
        return OAResponse.setResult(0, REMOVE_SUCCESS);
    }
}