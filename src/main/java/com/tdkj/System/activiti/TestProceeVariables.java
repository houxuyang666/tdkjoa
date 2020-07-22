package com.tdkj.System.activiti;


import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*流程变量测试
* act_ru_variable  记录流程运行时的流程变量
* act_hi_varinst  历史流程变量表
*
* */
@Slf4j
public class TestProceeVariables {

    /*总结
     * 在流程执行或者任务执行的过程中，用于设置和获取变量，使用流程变量在流程传递的过程中传递业务参数
     * 对应的表
     * act_ru_variable
     * act_hi_varinst
     *
     * 将对象设置为流程变量时需要注意 对象需要实现序列化接口
     * */

    //得到流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /*部署流程*/
    @Test
    public void deployProcess(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().name("请假流程").addClasspathResource("processes/leavebill.bpmn").deploy();
        log.info("流程部署成功：流程ID"+deploy.getId());
    }

    /*启动流程*/
    @Test
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        //ProcessInstance leavebill = runtimeService.startProcessInstanceByKey("leavebill");// act_re_procdef表中的KEY
        /*流程启动的时候可以设置流程变量*/
        Map<String, Object> variables =new HashMap<>();
        variables.put("请假天数",5);
        variables.put("请假原因","约会");
        variables.put("请假时间",new Date());
        ProcessInstance leavebill = runtimeService.startProcessInstanceByKey("leavebill", variables);// act_re_procdef表中的KEY
        log.info("流程启动成功："+leavebill.getId()+"    "+leavebill.getName()+
                "  流程定义ID："+leavebill.getProcessDefinitionId()+
                "  流程实例ID："+leavebill.getProcessInstanceId());
    }


    /*设置流程变量 act_ru_variable*/
    @Test
    public void setVariables(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String executionID="2501";  //流程实例ID
        Map<String, Object> variables =new HashMap<>();
        variables.put("请假天数",5);
        variables.put("请假原因","约会");
        variables.put("请假时间",new Date());
        variables.put("用户对象",new Users(1,"小明"));  //传对象必须实现Serializable接口
                                        //插入对象后会在二进制表中插入两条数据 一个为运行时对象 一个位历史对象
        runtimeService.setVariables(executionID,variables);
        System.out.println("流程变量设置成功");
    }

    /*设置流程变量2 act_ru_variable*/
    @Test
    public void setVariables2(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskID="2508";  //任务ID
        Map<String, Object> variables =new HashMap<>();
        variables.put("任务id设置的",9);
        taskService.setVariables(taskID,variables);
        System.out.println("流程变量设置成功");
    }


    /*获取流程变量 act_ru_variable*/
    @Test
    public void getVariables(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        String executionID="2501";  //流程实例ID
        Integer day= (Integer) runtimeService.getVariable(executionID,"请假天数");
        Date date= (Date) runtimeService.getVariable(executionID,"请假时间");
        Users users = (Users) runtimeService.getVariable(executionID, "用户对象");
        System.out.println(day);
        System.out.println(date.toLocaleString());
        System.out.println(users.getId()+"     "+users.getName());
    }

    /*查询历史的流程变量*/
    @Test
    public void getHistoryVariables(){
        HistoryService historyService = this.processEngine.getHistoryService();
        String processInstanceId="2501";  //流程实例ID
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if(null!=list&&list.size()>0){
            for (HistoricVariableInstance hi : list) {
                System.out.println("id "+hi.getId());
                System.out.println("变量类型"+hi.getVariableTypeName());
                System.out.println("变量名 "+hi.getVariableName());
                System.out.println("变量值 "+hi.getValue());

                System.out.println("***************************** ");

            }
        }

    }


}
