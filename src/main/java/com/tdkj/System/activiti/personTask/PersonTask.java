package com.tdkj.System.activiti.personTask;

import com.tdkj.System.activiti.Tableinit;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
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
 * @date 2020/7/23 14:44
 */
@Slf4j
public class PersonTask {
    /*使用流程变量指定下一个任务办理人
    * 启动流程变量的时候需要指定谁去申请这个任务
    * 当该人申请过后，需要指定谁来审批
    * 以此类推，之后结束整个流程*/

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /*tableinit*/
    @Test
    public void tableinit(){
        String[] args=null;
        Tableinit.main(args);
    }
    /*流程部署定义*/
    @Test
    public void deployProcess(){
        //通过路径部署流程信息
        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        Deployment deploy=repositoryService.createDeployment().name("变量流程单") //流程设置一个名字
                .addClasspathResource("processes/variable/Listener.bpmn").deploy();
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*启动变量流程并指定下一个办理人*/
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        Map<String,Object> variables =new HashMap<>();
        /*启动实例的时候指定下一个办理人 也就是谁提交申请*/
        variables.put("username","张三");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Listener",variables);// act_re_procdef表中的KEY
        log.info("流程启动成功："+processInstance.getId()+"    "+processInstance.getName()+
                "  流程定义ID："+processInstance.getProcessDefinitionId()+
                "  流程实例ID："+processInstance.getProcessInstanceId());
    }

    @Test
    public void completeTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskID="10002";
        /*完成任务的时候 指定下一个需要完成任务的人*/
        Map<String, Object> variables=new HashMap<>();
        variables.put("username","王五");
        taskService.complete(taskID,variables);
       // taskService.complete(taskID);
        System.out.println("任务完成");

    }




    /*查询我的个人任务 act_ru_task*/
    @Test
    public void queryMyTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String assignee="王五"; //执行人
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






}
