package com.tdkj.System.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
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
}
