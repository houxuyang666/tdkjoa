package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.service.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 8:53
 */
@Slf4j
@Controller
@RequestMapping("proflow")
public class ProFlowController {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private LeavebillService leavebillService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProFlowService proflowService;
    @Autowired
    private ProcurementService procurementService;


    /*根据采购ID启动流程定义*/
    /*@Transactional
    @ResponseBody
    @RequestMapping("/startProcess")
    public OAResponse startProcess(String proid) {
        try {
            this.proflowService.startProcess(proid);
            return OAResponse.setResult(200, "启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return OAResponse.setResult(500, "启动失败");
        }
    }*/

    /*跳转到我的待办任务页面*/
    @RequestMapping("/goProTaskManger")
    public String goTaskManger() {
        log.info("goTaskManger");
        return "page/procurement/protaskManger";
    }

    /*查询我的代办任务*/
    @ResponseBody
    @RequestMapping("/loadCurrentUserTask")
    public OAResponseList queryCurrentUserTask() {
        log.info("loadCurrentUserTask");
        /*分页值需要页面传*/
        Integer page =0;
        Integer limit =10;
        return OAResponseList.setResult(0, FIND_SUCCESS,this.proflowService.qureyCurrentUserTask(page,limit));
    }


    /*跳转到办理任务页面*/
    @RequestMapping("/goDoProTask")
    public ModelAndView goDoTask(String taskId) {
        /*log.info("goDoTask");
        log.info(taskId);*/
        //1.根据任务ID查询采购单信息
        ProcurementVO procurement =this.proflowService.queryProByTaskId(taskId);
        //2.根据任务ID查询连线信息
        List<String> outcomeName=this.proflowService.queryOutComeByTaskId(taskId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/procurement/doProTask");
        modelAndView.addObject("procurement",procurement);
        modelAndView.addObject("outcomes",outcomeName);
        modelAndView.addObject("taskId",taskId);
        return modelAndView;
    }

    /*查询批注信息*/
    @ResponseBody
    @RequestMapping("/loadAllCommentByTaskId")
    public OAResponseList loadAllCommentByTaskId(String taskId) {
        log.info("loadAllCommentByTaskId");
        log.info(taskId);
        return OAResponseList.setResult(0,FIND_SUCCESS,this.workFlowService.queryCommentByTaskId(taskId));
    }


    /*完成任务*/
    @Transactional
    @ResponseBody
    @RequestMapping("/doTask")
    public OAResponse doTask(String proid,String taskId,String comments,String outcome) {
        try{
            this.proflowService.completeTask(proid,taskId,comments,outcome);
            return OAResponse.setResult(200,"任务完成成功");
        }catch (Exception e){
            e.printStackTrace();
            return OAResponse.setResult(500,"任务完成失败");
        }
    }

    /*根据采购单ID查询审批批注信息和采购单信息*/
    @RequestMapping("/viewSpProcess")
    public ModelAndView viewSpProcess (String proid){
        ModelAndView modelAndView = new ModelAndView();
        /*查询请假单信息*/
        ProcurementVO procurementVO = this.procurementService.queryProVOByProId(proid);
        modelAndView.setViewName("page/procurement/viewProView");
        modelAndView.addObject("procurement",procurementVO);
        return modelAndView;
    }


    /*根据采购单的ID 查询批注信息*/
    @ResponseBody
    @RequestMapping("/loadCommentByProid")
    public OAResponseList loadCommentByProid (String proid){
        return OAResponseList.setResult(0,FIND_SUCCESS,this.proflowService.queryCommentByProid(proid));
    }


}
