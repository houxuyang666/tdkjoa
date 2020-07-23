package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
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
public class TestParallelGateWay {

    /*并行网关*/

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*流程部署定义*/
    @Test
    public void deployProcess(){
        //通过路径部署流程信息
        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        Deployment deploy=repositoryService.createDeployment().name("购物流程") //流程设置一个名字
                .addClasspathResource("pro/ParallelGateWay.bpmn").deploy();
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*启动流程*/
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        ProcessInstance leavebill = runtimeService.startProcessInstanceByKey("ParallelGateWay");// act_re_procdef表中的KEY
        log.info("流程启动成功："+leavebill.getId()+"    "+leavebill.getName()+
                "  流程定义ID："+leavebill.getProcessDefinitionId()+
                "  流程实例ID："+leavebill.getProcessInstanceId());
    }
        /*查询我的个人任务 act_ru_task*/
    @Test
    public void queryMyTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String assignee="买家"; //执行人
        List<Task> list = taskService.createTaskQuery()
                //条件
                .taskAssignee(assignee) //根据任务办理人查询条件

                //排序
                .orderByTaskCreateTime().desc()  //时间越接近的在上面
                //结果集
                .list();

        if(null!=list&&list.size()>0){
            for (Task task : list) {
                log.info("任务ID:"+task.getId());
                log.info("任务办理人:"+task.getAssignee());
                log.info("执行实例ID:"+task.getExecutionId());
                log.info("任务名称:"+task.getName());
                log.info("流程定义ID:"+task.getProcessDefinitionId());
                log.info("流程实例ID:"+task.getProcessInstanceId());
                log.info("任务创建时间:"+task.getCreateTime());
                log.info("**********************");

            }
        }

    }

    /*办理任务*/
    @Test
    public void completeTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskid="17502";
        //根据ID去完成任务
        taskService.complete(taskid);
        //根据ID去完成任务 并指定流程变量
//        taskService.complete(taskId, variables);
        log.info("任务完成");

    }

    /*办理任务 并使用流程变量 指定流程走向*/
    @Test
    public void completeTask2(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskid="2505";
        //根据ID去完成任务
        //taskService.complete(taskid);
        //根据ID去完成任务 并指定流程变量
        Map<String, Object> variables =new HashMap<>();
        variables.put("money",1200);
        taskService.complete(taskid, variables);
        log.info("任务完成");

    }

    /*判断流程是否结束 作用：更新业务表中的状态*/
    @Test
    public void isComplete(){
        /*act_ru_task*/
        //已知流程实例ID
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String processInstanceId="2501";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if(null!=processInstance){
            log.info("流程未结束");
        }else{
            log.info("流程已结束");
        }

    }
    /*查询流程实例 act_ru_execution*/
    @Test
    public void queryProcessInstance(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        if(null!=list&&list.size()>0){
            for (ProcessInstance processInstance : list) {
                log.info("执行实例ID:"+processInstance.getId());
                log.info("流程定义ID:"+processInstance.getProcessDefinitionId());
                log.info("流程实例ID:"+processInstance.getProcessInstanceId());
                log.info("********************");
            }
        }

    }


    /*查询历史任务 act_hi_taskinst*/
    @Test
    public void queryHistoryTask(){
        HistoryService historyService = this.processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().orderByHistoricTaskInstanceStartTime().asc().list();
        if(null!=list&&list.size()>0){
            for (HistoricTaskInstance hi : list) {
                log.info("任务ID:"+hi.getId());
                log.info("任务办理人:"+hi.getAssignee());
                log.info("执行实例ID:"+hi.getExecutionId());
                log.info("任务名称:"+hi.getName());
                log.info("流程定义ID:"+hi.getProcessDefinitionId());
                log.info("流程实例ID:"+hi.getProcessInstanceId());
                log.info("任务创建时间:"+hi.getCreateTime());
                log.info("任务创建时间:"+hi.getStartTime().toLocaleString());
                log.info("任务结束时间:"+hi.getEndTime());
                log.info("任务持续时间:"+hi.getDurationInMillis());
                log.info("********************");
            }
        }
    }

    /*查询历史流程实例 act_hi_procinst*/
    @Test
    public void queryHistoryProcessInstance(){
        HistoryService historyService = this.processEngine.getHistoryService();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        if(null!=list&&list.size()>0){
            for (HistoricProcessInstance hi : list) {
                log.info("任务ID:"+hi.getId());
                log.info("任务名称:"+hi.getName());
                log.info("流程定义ID:"+hi.getProcessDefinitionId());
                log.info("任务结束时间:"+hi.getEndTime());
                log.info("任务持续时间:"+hi.getDurationInMillis());
                log.info("********************");

            }
        }


    }






























}
