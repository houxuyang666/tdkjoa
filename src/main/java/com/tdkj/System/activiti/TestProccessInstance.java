package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/22 11:42
 */
@Slf4j
public class TestProccessInstance {

    /*总结*/
    /* Execution 执行对象
        act_ru_execution 正在执行的信息   流程启动一次只要没执行完就会有一条数据
        act_hi_procinst  已经执行完的历史流程实例信息
     *  act_hi_actinst   存放历史所有完成的信息 历史活动节点表
     **/
    /* ProcessInstance 流程实例
     *   如果是单线流程，执行对象DI就是实例ID
     *   如果一个流程有分支和聚合，那么执行对象ID和流程实例ID就不同
     *   一个流程中，流程实例只有一个，执行对象可以存在多个
     * */
    /*Task 任务
     * 执行到某任务环节时生成的任务信息
     * act_ru_task 正在执行的任务信息
     * act_hi_taskinst 已经执行完的历史任务信息
     * */
    /*相关ID总结
     * 部署ID        act_re_deployment
     * 流程定义ID  act_re_procdef
     * 流程实例ID  act_ru_execution
     * 执行实例ID  act_ru_task execution_id
     * 任务ID     act_ru_task id
     * */

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /*流程部署定义*/
    @Test
    public void deployProcess(){
        //通过路径部署流程信息
        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        Deployment deploy=repositoryService.createDeployment().name("请假流程单") //流程设置一个名字
                .addClasspathResource("processes/leavebill.bpmn").deploy();
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*启动流程*/
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
//        runtimeService.startProcessInstanceById("leavebill:1:4");// act_re_procdef表中的ID
        /*参数1：流程定义ID
        * 参数2：Map<String,Object> 流程变量
        * */
//       runtimeService.startProcessInstanceById(processDefinitionID,Variables);
        /*参数1：流程定义ID
         * 参数2：String 业务ID 把业务ID 和流程执行实例进行绑定
         * */
//        runtimeService.startProcessInstanceById(processDefinitionID,businessKey);
        /*参数1：流程定义ID
         * 参数2：String 业务ID 把业务ID 和流程执行实例进行绑定
         * 参数3：Map<String,Object> 流程变量
         * */
//        runtimeService.startProcessInstanceById(processDefinitionID,businessKey,Variables);



        //-----------------
//        runtimeService.startProcessInstanceByKey("leavebill");// act_re_procdef表中的KEY
        /*参数1：流程定义ID
         * 参数2：Map<String,Object> 流程变量
         * */
//       runtimeService.startProcessInstanceByKey(processDefinitionKey,Variables);
        /*参数1：流程定义ID
         * 参数2：String 业务ID 把业务ID 和流程执行实例进行绑定
         * */
//        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey);
        /*参数1：流程定义ID
         * 参数2：String 业务ID 把业务ID 和流程执行实例进行绑定
         * 参数3：Map<String,Object> 流程变量
         * */
//        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,Variables);

        /*常用*/
//        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey);
//        runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,Variables);


        ProcessInstance leavebill = runtimeService.startProcessInstanceByKey("leavebill");// act_re_procdef表中的KEY
        log.info("流程启动成功："+leavebill.getId()+"    "+leavebill.getName()+
                "  流程定义ID："+leavebill.getProcessDefinitionId()+
                "  流程实例ID："+leavebill.getProcessInstanceId());
    }
        /*查询我的个人任务 act_ru_task*/
    @Test
    public void queryMyTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String assignee="王五"; //执行人
        List<Task> list = taskService.createTaskQuery()
                //条件
                .taskAssignee(assignee) //根据任务办理人查询条件
//                .deploymentId(deploymentId)根据部署ID 查询
//                .deploymentIdIn(deploymentIds) 根据部署ID集合查询（效率很低）
//                .executionId(executionId)  根据执行实例ID
//                .processDefinitionId(processDefinitionId)根据流程定义ID
//                .processDefinitionKey(processDefinitionKey)根据流程定义的key
//                .processDefinitionKeyIn(processDefinitionKeys)
//                .processDefinitionKeyLike(processDefinitionKeyLike)
//                .processDefinitionName(processDefinitionName)
//                .processDefinitionNameLike(processDefinitionNameLike)
//                .processInstanceBusinessKey(processInstanceBusinessKey)
                //排序
                .orderByTaskCreateTime().desc()  //时间越接近的在上面
                //结果集
                .list();
//                .listPage(firstResult, maxResults) 分页查
//                .count() 总数
//                .singleResult() 单条数据
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
        String taskid="12502";
        //根据ID去完成任务
        taskService.complete(taskid);
        //根据ID去完成任务 并指定流程变量
//        taskService.complete(taskId, variables);
        log.info("任务完成");

    }

    /*判断流程是否结束 作用：更新业务表中的状态*/
    @Test
    public void isComplete(){
        /*act_ru_task*/
        //已知流程实例ID
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String processInstanceId="2505";
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
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().orderByProcessInstanceId().asc().list();
        if(null!=list&&list.size()>0){
            for (HistoricTaskInstance hi : list) {
                log.info("任务ID:"+hi.getId());
                log.info("任务办理人:"+hi.getAssignee());
                log.info("执行实例ID:"+hi.getExecutionId());
                log.info("任务名称:"+hi.getName());
                log.info("流程定义ID:"+hi.getProcessDefinitionId());
                log.info("流程实例ID:"+hi.getProcessInstanceId());
                log.info("任务创建时间:"+hi.getCreateTime());
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
