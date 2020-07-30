package com.tdkj.System.service;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.entity.Leavebill;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 8:55
 */
public interface WorkFlowService {
    /*添加流程部署*/
    public void addWorkFlow(InputStream inputStream, String deploymentName);
    //根据流程部署ID删除流程定义
    public void deleteWorkFlow(String deploymentid);
    //根据流程部署ID查询流程图
    InputStream qureyProcessDeploymentImage(String deploymentid);
    /*根据请假单ID启动流程*/
    void startProcess(Integer leavbillid);
    /*查询当前登陆用的待办任务*/
    PageInfo qureyCurrentUserTask(Integer page, Integer limit);
    //根据任务ID查询请假单信息
    Leavebill queryLeaveBillByTaskId(String taskId);
    /*根据任务ID 查询连线信息*/
    List<String> queryOutComeByTaskId(String taskId);
    /*根据任务ID 查询批注信息*/
    PageInfo queryCommentByTaskId(String taskId);
    /*完成任务*/
    void completeTask(Integer leavebillId,String taskId,String comments, String outcome);
    /*根据任务ID查询流程定义对象*/
    ProcessDefinition queryPrcessDefinitionByTaskID(String taskId);
    /*根据任务ID查询节点坐标*/
    Map<String, Object> queryTaskCoordinateByTaskId(String taskId);
    /*查询历史批注信息*/
    PageInfo queryCommentByLeavebillid(Integer id);
    /*根据当前用户名称查询用户审批记录*/
    PageInfo queryCurrentUserHistoryTask(String name);
}
