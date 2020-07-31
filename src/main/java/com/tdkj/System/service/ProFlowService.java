package com.tdkj.System.service;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.entity.VO.ProcurementVO;

import java.util.List;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/31 8:54
 */
public interface ProFlowService {

    /*根据采购单ID启动流程*/
    void startProcess(String proid);
    /*查询当前登陆用的采购待办任务*/
    PageInfo qureyCurrentUserTask(Integer page, Integer limit);
    /*根据任务ID查询请假单信息*/
    ProcurementVO queryProByTaskId(String taskId);
    /*根据任务ID查询连线信息*/
    List<String> queryOutComeByTaskId(String taskId);
    /*根据任务列表信息 办理任务*/
    void completeTask(String proid, String taskId, String comments, String outcome);
    /*根据采购单的ID 查询批注信息*/
    PageInfo queryCommentByProid(String proid);
}
