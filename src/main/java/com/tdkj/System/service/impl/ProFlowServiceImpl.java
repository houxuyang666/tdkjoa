package com.tdkj.System.service.impl;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.Enum.ProcurementStatusEnmu;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.entity.Warehouse;
import com.tdkj.System.entity.ect.ActCommentEntity;
import com.tdkj.System.entity.ect.ActTaskEntity;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.ProFlowService;
import com.tdkj.System.service.ProcurementService;
import com.tdkj.System.utils.DateUtil;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private WarehouseServiceImpl warehouseService;


    /*启动采购流程*/
    @Override
    public void startProcess(String proid) {
        //找到流程的KEY
        String processDefinitionKey = "Pro"; //获取对象名称
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
        List<Task> taskList = this.taskService.createTaskQuery().taskAssignee(employee.getName()).orderByTaskCreateTime().desc().listPage(page, limit);
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
        if(ProcurementStatusEnmu.rejected.getDesc().equals(outcome)){
            this.runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"驳回");
        }
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
            if(ProcurementStatusEnmu.rejected.getDesc().equals(outcome)){ //驳回
                procurement.setStatus(AuditStatusEnmu.rejected.getCode());
            }else if (ProcurementStatusEnmu.Purchase_complete.getDesc().equals(outcome)){ //购买完成
                procurement.setStatus(ProcurementStatusEnmu.Purchase_complete.getCode());
            }
            procurement.setModifydate(new Date());
            //获取该订单信息插入到仓库表中
            Procurement procurement1 = this.procurementService.queryById(proid);
            Warehouse warehouse = this.warehouseService.queryBygoodsname(procurement1.getGoodsname());
            if (null==warehouse){//证明数据库中不存在
                Warehouse warehouse1=new Warehouse();
                String code= RandomStringUtils.random(4, true, true);
                warehouse1.setWarehouseid(DateUtil.getformatDate(new Date())+code);
                warehouse1.setWarehouseid(procurement1.getProid());
                warehouse1.setCorpid(procurement1.getCorpid());
                warehouse1.setGoodsname(procurement1.getGoodsname());
                warehouse1.setUnit(procurement1.getUnit());
                warehouse1.setType(procurement1.getType());
                warehouse1.setTotalnumbe(procurement1.getNumber());
                warehouse1.setPrice(procurement1.getPrice());
                warehouse1.setTotalamount(procurement1.getTotalamount());
                warehouse1.setCreatedate(new Date());
                this.warehouseService.insert(warehouse1);
            }else{
                Warehouse warehouse2=new Warehouse();
                warehouse2.setWarehouseid(warehouse.getWarehouseid());
                warehouse2.setTotalnumbe(procurement1.getNumber()+warehouse.getTotalnumbe());
                warehouse2.setTotalamount(procurement1.getTotalamount().add(warehouse.getTotalamount()));
                warehouse2.setModifydate(new Date());
                this.warehouseService.update(warehouse2);
            }
            this.procurementService.update(procurement);
            /*购买完成后 插入仓库表*/
        }
    }

    /**
     * @Author houxuyang
     * @Description //购买完成后插入仓库表
     * @Date 15:04 2020/8/13
     * @Param [proid]
     * @return void
     **/
    public void insertWarehouse(String proid) throws Exception {

    }

    @Override
    public PageInfo queryCommentByProid(String proid) {
        //组装businessKey
        //String businessKey =Leavebill.class.getSimpleName()+":"+id;
        String businessKey ="Pro"+":"+proid;

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
