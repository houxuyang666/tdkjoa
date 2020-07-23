package com.tdkj.System.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestHistoryQuery {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /*查询历史流程实例*/
    @Test
    public void historyProcessInstince(){
        HistoryService historyService = this.processEngine.getHistoryService();

        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                //条件
//        .processDefinitionId(processDefinitionId)

//        .processDefinitionIds(processDefinitionIds) //根据流程定义的ID查询
//        .processDefinitionKey(processDefinitionKey) //根据流程定义的key查询
//        .processDefinitionKeyIn()
//        .processDefinitionName()
//        .processDefinitionVersion()
//        .processInstanceBusinessKey() //流程业务ID
//        .processInstanceId()
//        .processInstanceIds()
                //排序
//        .orderByProcessDefinitionId()//流程定义ID排序
//        .orderByProcessInstanceBusinessKey()//业务ID排序
//        .orderByProcessInstanceDuration()//持续时间排序
//        .orderByProcessInstanceStartTime()//开始时间
//        .orderByProcessInstanceId() //流程实例ID
                //结果集
                .list();
        if (null != list && list.size() > 0) {
            for (HistoricProcessInstance hi : list) {
                System.out.println("历史流程实例ID："+hi.getId());
                System.out.println("流程定义ID："+hi.getProcessDefinitionId());
                System.out.println("历史流程实例的业务ID："+hi.getBusinessKey());
                System.out.println("流程部署ID："+hi.getDeploymentId());
                System.out.println("流程定义KEY："+hi.getProcessDefinitionKey());
                System.out.println("开始活动ID："+hi.getStartActivityId());
                System.out.println("结束活动ID："+hi.getEndActivityId());
                System.out.println("***********************");

            }

        }
    }

    /*查询历史活动  act_hi_actinst      意义不大*/
    @Test
    public void queryHistoryAct(){
        HistoryService historyService = this.processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        if(null!=list&&list.size()>0){
            for (HistoricActivityInstance hi : list) {
                System.out.println("ID: "+hi.getId());
                System.out.println("流程定义ID: "+hi.getProcessDefinitionId()); //processdefinitionid 流程定义ID
                System.out.println("流程实例id: "+hi.getProcessInstanceId()); //ProcessInstanceId  流程实例id
                System.out.println("执行实例id: "+hi.getExecutionId()); //执行实例id
                System.out.println("活动id: "+hi.getActivityId()); //活动id
                System.out.println("任务Id: "+hi.getTaskId()); //任务Id
                System.out.println("活动名称: "+hi.getActivityName()); //活动名称
                System.out.println("活动类型: "+hi.getActivityType());//活动类型
                System.out.println("办理人名称: "+hi.getAssignee()); //办理人名称
                System.out.println("开始时间: "+hi.getStartTime().toLocaleString()); //开始时间
                System.out.println("结束时间: "+hi.getEndTime().toLocaleString()); //结束时间
                System.out.println("办理时长: "+hi.getDurationInMillis()); //办理时长
            }
        }

    }

    /*查询历史任务  act_hi_taskinst*/
    @Test
    public void queryHistoryTask(){
        HistoryService historyService = this.processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().orderByHistoricTaskInstanceStartTime().asc().list();
        if(null!=list&&list.size()>0){
            for (HistoricTaskInstance hi : list) {
                System.out.println("ID: "+hi.getId());
                System.out.println("流程定义ID: "+hi.getProcessDefinitionId()); //processdefinitionid 流程定义ID
                System.out.println("流程实例id: "+hi.getProcessInstanceId()); //ProcessInstanceId  流程实例id
                System.out.println("执行实例id: "+hi.getExecutionId()); //执行实例id

                System.out.println("办理人名称: "+hi.getAssignee()); //办理人名称
                System.out.println("开始时间: "+hi.getStartTime().toLocaleString()); //开始时间
                System.out.println("结束时间: "+hi.getEndTime().toLocaleString()); //结束时间
                System.out.println("办理时长: "+hi.getDurationInMillis()); //办理时长
            }
        }

    }
}
