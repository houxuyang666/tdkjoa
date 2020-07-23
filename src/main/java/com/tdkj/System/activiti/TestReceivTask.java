package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/22 11:42
 */
@Slf4j
public class TestReceivTask {

    /*接收任务*/

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*流程部署定义*/
    @Test
    public void deployProcess(){
        //通过路径部署流程信息
        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        Deployment deploy=repositoryService.createDeployment().name("汇总销售额") //流程设置一个名字
                .addClasspathResource("processes/Receive.bpmn").deploy();
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*启动流程*/
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Receive");// act_re_procdef表中的KEY
        log.info("流程启动成功："+processInstance.getId()+"    "+processInstance.getName()+
                "  流程定义ID："+processInstance.getProcessDefinitionId()+
                "  流程实例ID："+processInstance.getProcessInstanceId());

    }

    @Test
    public void executionTask(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        /*查询执行对象ID*/
        String processInstanceId ="7501";
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery()//创建执行对象查询
                .processInstanceId(processInstanceId)//使用流程实例id查询
                .activityId("_3") //当前活动的id，对应receiveTask.bpmn文件中的
                .singleResult();

        System.out.println("执行实例ID:"+execution.getId());
        System.out.println("流程ID:"+execution.getProcessInstanceId());
        System.out.println("活动ID:"+execution.getActivityId());

        /*使用流程变量设置当日销售额，用来传递业务参数*/
        int value =10000; //数据库查询
        runtimeService.setVariable(execution.getId(),"当前销售额",value);

        /*向后执行一步，如果流程处于等待状态，使得流程得以继续*/
        runtimeService.signalEventReceived(execution.getId());
    }





}
