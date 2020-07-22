package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/22 10:33
 */
@Slf4j
public class HelloWorld {

    /*模拟请假流程*/

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*流程部署定义*/
    @Test
    public void deployProcess(){

        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        Deployment deploy=repositoryService.createDeployment().name("请假流程002") //流程设置一个名字
        .addClasspathResource("processes/leavebill.bpmn").deploy();
        /*部署流程 会插入三张表数据
            ACT_RE_PROCDEF
            ACT_RE_DEPLOYMENT
            ACT_GE_BYTEARRAY
          */
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*启动流程*/
    @Test
    public  void statrProcess(){
        RuntimeService runtimeService =this.processEngine.getRuntimeService();
        runtimeService.startProcessInstanceById("HelloWorld:3:40004");//可以使用id启动 act_re_procdef表中的ID
        String processDefinitionKey ="HelloWorld"; //可以使用Key启动 act_re_procdef表中的key 如果key相同则使用version较高的启动
        //runtimeService.startProcessInstanceByKey(processDefinitionKey);
        /*向以下表中插入数据
        * ACT_HI_ACTINST
        * ACT_HI_TASKINST
        * ACT_HI_PROCINST
        * ACT_HI_IDENTITYLINK
        * ACT_RU_EXECUTION
        * ACT_RU_TASK
        * ACT_RU_IDENTITYLINK
        * */

        log.info("流程启动成功");
    }

    /*查询任务*/
    @Test
    public void findTask(){
        TaskService taskService =this.processEngine.getTaskService();
        /*查询ACT_RU_TASK */
        String assignee="张三";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        if(null!=list&&list.size()>0){
            for (Task task : list) {
                log.info("任务ID："+task.getId());
                log.info("流程实例ID："+task.getProcessInstanceId());
                log.info("执行实例ID："+task.getExecutionId());
                log.info("流程定义ID："+task.getProcessDefinitionId());
                log.info("任务名称："+task.getName());
                log.info("任务办理人："+task.getAssignee());
                log.info("########################");

            }
        }

    }

    //任务ID：2505
    /*办理任务*/
    @Test
    public void compleTask(){
        TaskService taskService =this.processEngine.getTaskService();
        String taskID ="32502";
        taskService.complete(taskID);
        log.info("任务完成");
    }
}
