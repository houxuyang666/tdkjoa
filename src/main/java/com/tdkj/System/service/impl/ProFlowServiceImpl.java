package com.tdkj.System.service.impl;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.entity.ect.ActCommentEntity;
import com.tdkj.System.entity.ect.ActTaskEntity;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.ProFlowService;
import com.tdkj.System.service.ProcurementService;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/31 8:55
 */
@Slf4j
@Service("proflowService")
public class ProFlowServiceImpl implements ProFlowService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;


    /*启动采购流程*/
    @Override
    public void startProcess(String proid) {
        //找到流程的KEY
        String processDefinitionKey = "LeavebillOr"; //获取对象名称
        String businessKey =processDefinitionKey+":"+proid;
        Map<String, Object> variables =new HashMap<>();
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        variables.put("username", employee.getName());
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        //更新采购单状态
        /*Procurement procurement =new Procurement();
        procurement.setProid(proid);
        procurement.setStatus(ProcurementStatusEnmu.Review_completed.getCode());
        procurementService.update(procurement);*/
    }


    @Override
    public PageInfo qureyCurrentUserTask(Integer page, Integer limit) {
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        //1.得到办理人信息
        //String assignee =employee.getName();

        //3.查询集合
        List<Task> taskList = this.taskService.createTaskQuery().taskAssignee(employee.getName()).listPage(page, limit);
        List<ActTaskEntity> actTaskEntities = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String businessKey = processInstance.getBusinessKey();
            String businessid =businessKey.split(":")[1];
            if (ShiroUtils.judgeContainsStr(businessid)){
                ActTaskEntity entity = new ActTaskEntity();
                BeanUtils.copyProperties(task,entity);
                actTaskEntities.add(entity);
            }else{
                taskList.remove(this);
            }
            log.info(processInstance.toString());
        }
        PageInfo pageInfo =new PageInfo();
        pageInfo.setTotal(actTaskEntities.size());
        pageInfo.setList(actTaskEntities);
        return pageInfo;
    }

    @Override
    public ProcurementVO queryProByTaskId(String taskId) {
        //1.根据任务ID 查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.从任务里面去除流程实例id
        //String processInstanceId= task.getProcessInstanceId();
        //3.根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //4.去除Business_key
        String businessKey= processInstance.getBusinessKey();
        String proId =businessKey.split(":")[1];
        return procurementService.queryProVOByProId(proId);

    }

    @Override
    public List<String> queryOutComeByTaskId(String taskId) {
        List<String> names= new ArrayList<>();
        //1.根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.取出流程定义ID
        //String processDefinitionId = task.getProcessDefinitionId();
        //3.取出流程实例ID
        //String processInstanceId = task.getProcessInstanceId();
        //4.根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //5.根据流程定义ID查询流程定义的xml信息
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //6.从流程实例对象中取出当前活动节点ID
        //String activityId = processInstance.getActivityId();// usertask1
        // 7,使用活动ID取出xml和当前活动ID相关节点数据
        ActivityImpl activityImpl = processDefinition.findActivity(processInstance.getActivityId());
        // 8,从activityImpl取出连线信息
        List<PvmTransition> transitions = activityImpl.getOutgoingTransitions();
        if (null != transitions && transitions.size() > 0) {
            // PvmTransition就是连接对象
            for (PvmTransition pvmTransition : transitions) {
                String name = pvmTransition.getProperty("name").toString();
                names.add(name);
            }
        }
        return names;
    }

    @Override
    public void completeTask(String proid, String taskId, String comments, String outcome) {

        Procurement procurement =new Procurement();
        procurement.setProid(proid);

        //1.根据任务ID 查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.从任务里面取出流程实例ID
        String processInstanceID =task.getProcessInstanceId();
        log.info("userid："+ShiroUtils.getPrincipal().getUserid());
        //设置批注人
        String name = this.employeeService.getName(ShiroUtils.getPrincipal().getEmployeeid());
        //使用了ThreadLocal的线程局部变量

        Authentication.setAuthenticatedUserId(name);
        //3.添加批注信息
        taskService.addComment(taskId,processInstanceID,"["+outcome+"]"+comments);

        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", outcome);
        this.taskService.complete(taskId, variables);
        if("驳回".equals(outcome)){
            this.runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"驳回");
        }
        //创建时就直接提交到经理 所以没有放弃选项
        /*else if ("放弃".equals(outcome)){
            this.runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "放弃");
        }*/
        //判断流程是否结束
        /*act_ru_task*/
        //已知流程实例ID
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceID).singleResult();
        if(null==processInstance) {
            /*说明流程结束*/
            log.info("流程已结束");
            /*if (outcome.equals("放弃")) {
                procurement.setStatus(AuditStatusEnmu.give_up.getCode());
            }else if("驳回".equals(outcome)){
                procurement.setStatus(AuditStatusEnmu.rejected.getCode());
            }else{
                procurement.setStatus(AuditStatusEnmu.Review_completed.getCode());
            }*/
            if("驳回".equals(outcome)){
                procurement.setStatus(AuditStatusEnmu.rejected.getCode());
            }else{
                procurement.setStatus(AuditStatusEnmu.Review_completed.getCode());
            }
            this.procurementService.update(procurement);
        }
    }

    @Override
    public PageInfo queryCommentByProid(String proid) {
        //组装businessKey
        //String businessKey =Leavebill.class.getSimpleName()+":"+id;
        String businessKey ="LeavebillOr"+":"+proid;

        HistoricProcessInstance historicProcessInstance = this.historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        /*获取历史流程实例id*/
        //使用taskservice+流程实例ID查询批注
        List<Comment> comments = this.taskService.getProcessInstanceComments(historicProcessInstance.getId());
        List<ActCommentEntity> data= new ArrayList<>();
        if(null!=comments&& comments.size()>0){
            for (Comment comment : comments) {
                ActCommentEntity entity =new ActCommentEntity();
                BeanUtils.copyProperties(comment,entity);
                data.add(entity);
            }
        }
        PageInfo pageInfo =new PageInfo();
        pageInfo.setList(data);
        pageInfo.setTotal(data.size());
        return pageInfo;
    }
}
