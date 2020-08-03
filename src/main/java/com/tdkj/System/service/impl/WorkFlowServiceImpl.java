package com.tdkj.System.service.impl;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.entity.VO.WorkFlowVO;
import com.tdkj.System.entity.ect.ActCommentEntity;
import com.tdkj.System.entity.ect.ActProcessDefinitionEntity;
import com.tdkj.System.entity.ect.ActTaskEntity;
import com.tdkj.System.entity.ect.ActdeploymentEntity;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.LeavebillService;
import com.tdkj.System.service.ProcurementService;
import com.tdkj.System.service.WorkFlowService;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 8:55
 */
@Slf4j
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FormService formService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private LeavebillService leavebillService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProcurementService procurementService;


    public OAResponseList querProcessDeploy(WorkFlowVO workFlowVO) {
        PageInfo<ActdeploymentEntity> pageInfo = new PageInfo<ActdeploymentEntity>();
        /*查询流程部署信息*/
        /*查询出数据后*/
        long count = repositoryService.createDeploymentQuery().deploymentNameLike(workFlowVO.getDeploymentName()).count();
        List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike(workFlowVO.getDeploymentName()).listPage(workFlowVO.getPage(), workFlowVO.getLimit());
        /*由于属性匹配不上那么我们创建新的对象来接收*/
        /*流程部署对象*/
        List<ActdeploymentEntity> actdeploymentEntityArrayList = new ArrayList<ActdeploymentEntity>();
        for (Deployment deployment : list) {
            ActdeploymentEntity entity = new ActdeploymentEntity();
            BeanUtils.copyProperties(deployment, entity);
            actdeploymentEntityArrayList.add(entity);
        }
        pageInfo.setTotal(count);
        pageInfo.setList(actdeploymentEntityArrayList);

        /*查询流程定义信息*/

        Set<String> deploymentIds = new HashSet<>();
        for (Deployment deployment : list) {
            deploymentIds.add(deployment.getId());
        }
        long count1 = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).count();
        List<ProcessDefinition> processDefinitions = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).listPage(workFlowVO.getPage(), workFlowVO.getLimit());
        /*由于属性匹配不上那么我们创建新的对象来接收*/
        /*流程定义对象*/
        List<ActProcessDefinitionEntity> actProcessDefinitionEntities = new ArrayList<ActProcessDefinitionEntity>();
        for (ProcessDefinition pro : processDefinitions) {
            ActProcessDefinitionEntity entity = new ActProcessDefinitionEntity();
            BeanUtils.copyProperties(pro, entity);
            actProcessDefinitionEntities.add(entity);
        }
        PageInfo<ActProcessDefinitionEntity> pageInfo2 = new PageInfo<ActProcessDefinitionEntity>();
        pageInfo2.setTotal(count1);
        pageInfo2.setList(actProcessDefinitionEntities);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo, pageInfo2);
    }

    /*部署流程*/
    @Override
    public void addWorkFlow(InputStream inputStream, String deploymentName) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        log.info(zipInputStream.toString());
        /*暂时无法正确插入 只能插入部署表信息 无法插入流程定义表信息*/
        repositoryService.createDeployment().addZipInputStream(zipInputStream).name(deploymentName).deploy();
        /*快捷键 选中需要抛出异常的代码 ctrl+alt+T*/
        try {
            inputStream.close();
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkFlow(String deploymentid) {
        //设置为true 会暴力删除 否则 如果当前流程定义被使用那么就会报错。
        this.repositoryService.deleteDeployment(deploymentid,true);
    }
    /**
     * @Author houxuyang
     * @Description //根据流程部署ID查询流程图
     * @Date 17:02 2020/7/24
     * @Param [deploymentid]
     * @return java.io.InputStream
     **/
    @Override
    public InputStream qureyProcessDeploymentImage(String deploymentid) {
        /*1.根据部署ID查询流程定义对象*/
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deploymentid).singleResult();
        //2.根据查询的流程定义对象 获取到对应的图片名称
        String resourceName=processDefinition.getDiagramResourceName();
        //3.根据流程定义ID和图片名称获取图片流返回
        InputStream resourceAsStream = this.repositoryService.getResourceAsStream(deploymentid, resourceName);
        return resourceAsStream;
    }

    /**
     * @Author houxuyang
     * @Description //根据请假单ID启动流程
     * @Date 8:59 2020/7/27
     * @Param [leavbillid]
     * @return void
     **/
    @Override
    public void startProcess(Integer leavbillid) {
        //找到流程的KEY
        //String processDefinitionKey = Leavebill.class.getSimpleName(); //获取对象名称
        String processDefinitionKey = "LeavebillOr"; //获取对象名称
        String businessKey =processDefinitionKey+":"+leavbillid; //"Leavbill:1"
        Map<String, Object> variables =new HashMap<>();
        /*设置流程变量获取当前用户 也就是谁提交的请假单（设置下个流程的办理人）*/
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        variables.put("username", employee.getName());
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        //更新请假单状态
        //添加请假单后直接启动流程 所以这里可以省略更新业务的状态
        /*Leavebill leavebill =new Leavebill();
        leavebill.setId(leavbillid);
        leavebill.setStatus(AuditStatusEnmu.Under_review.getCode());
        leavebillService.update(leavebill);*/
    }

    @Override
    public PageInfo qureyCurrentUserTask(Integer page, Integer limit) {
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        //1.得到办理人信息
        String assignee =employee.getName();
        //2.查询总数
        //long count = this.taskService.createTaskQuery().taskAssignee(assignee).count();
        //3.查询集合
        List<Task> taskList = this.taskService.createTaskQuery().taskAssignee(assignee).listPage(page, limit);

        /*添加判断*/

        List<ActTaskEntity> actTaskEntities = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String businessKey = processInstance.getBusinessKey();
            String businessid =businessKey.split(":")[1];
            if (ShiroUtils.judgeContainsStr(businessid)){
                taskList.remove(this);
            }else{
                ActTaskEntity entity = new ActTaskEntity();
                BeanUtils.copyProperties(task,entity);
                actTaskEntities.add(entity);
            }
            log.info(processInstance.toString());
        }

        /*添加判断结束*/
       /* List<ActTaskEntity> actTaskEntities = new ArrayList<>();
        for (Task task : taskList) {
            ActTaskEntity entity = new ActTaskEntity();
            BeanUtils.copyProperties(task,entity);
            actTaskEntities.add(entity);
        }*/
        PageInfo pageInfo =new PageInfo();
        pageInfo.setTotal(actTaskEntities.size());
        pageInfo.setList(actTaskEntities);
        return pageInfo;
    }

    @Override
    public Leavebill queryLeaveBillByTaskId(String taskId) {
        //1.根据任务ID 查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.从任务里面去除流程实例id
        String processInstanceId= task.getProcessInstanceId();
        //3.根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //4.去除Business_key
        String businessKey= processInstance.getBusinessKey();//leaveBill:9 只需要后面的数字
        String leaveBillId =businessKey.split(":")[1];
        return leavebillService.queryById(Integer.valueOf(leaveBillId));
    }

    @Override
    public List<String> queryOutComeByTaskId(String taskId) {
        List<String> names= new ArrayList<>();
        //1.根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.取出流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //3.取出流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //4.根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //5.根据流程定义ID查询流程定义的xml信息
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(processDefinitionId);
        //6.从流程实例对象中取出当前活动节点ID
        String activityId = processInstance.getActivityId();// usertask1
        // 7,使用活动ID取出xml和当前活动ID相关节点数据
        ActivityImpl activityImpl = processDefinition.findActivity(activityId);
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
    public PageInfo queryCommentByTaskId(String taskId) {
        //1.根据任务ID 查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.从任务里面取出流程实例ID
        String processInstanceID =task.getProcessInstanceId();
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceID);
        List<ActCommentEntity> data =new ArrayList<>();
        if (null!=comments&&comments.size()>0) {
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

    @Override
    public void completeTask(Integer leavebillId,String taskId,String comments, String outcome) {
        log.info("completeTask");
        log.info("任务ID："+taskId);
        log.info("批注信息："+comments);
        log.info("操作："+outcome);
        log.info("请假单ID："+leavebillId.toString());
        Leavebill leavebill =new Leavebill();
        leavebill.setId(leavebillId);

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
        }else if ("放弃".equals(outcome)){
            this.runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "放弃");
        }
        //判断流程是否结束
        /*act_ru_task*/
        //已知流程实例ID
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceID).singleResult();
        if(null==processInstance) {
            /*说明流程结束*/
            log.info("流程已结束");
            if (outcome.equals("放弃")) {
                leavebill.setStatus(AuditStatusEnmu.give_up.getCode());
            }else if("驳回".equals(outcome)){
                leavebill.setStatus(AuditStatusEnmu.rejected.getCode());
            }else{
                leavebill.setStatus(AuditStatusEnmu.Review_completed.getCode());
            }
            this.leavebillService.update(leavebill);
        }
    }

    @Override
    public ProcessDefinition queryPrcessDefinitionByTaskID(String taskId) {
        //1.根据任务ID查询任务实例
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.取出流程实例对象
        String processInstanceId = task.getProcessInstanceId();
        //3.根据流程实例ID查询流程实例对象
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //4.取出流程部署对象
        String processDefinitionId = processInstance.getProcessDefinitionId();
        //查询流程定义对象
        ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    @Override
    public Map<String, Object> queryTaskCoordinateByTaskId(String taskId) {
        Map<String,Object> coordinate =new HashMap<>();
        //1.根据任务ID查询任务实例
        Task task =this.taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.取出流程定义ID
        String processDefinitionId =task.getProcessDefinitionId();
        //3.取出实例ID
        String processInstanceId = task.getProcessInstanceId();
        //4.根据流程实例ID查询流程实例
        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //5.根据流程定义ID查询流程定义的xml信息
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)this.repositoryService.getProcessDefinition(processDefinitionId);
        //6.从流程实例对象里面取出当前活动节点ID
        String activityId = processInstance.getActivityId();
        //7.使用活动id取出xml和当前活动相关的节点数据
        ActivityImpl activity = processDefinition.findActivity(activityId);
        //8.才能够activity取出坐标信息
        coordinate.put("x", activity.getX());
        coordinate.put("y", activity.getY());
        coordinate.put("width", activity.getWidth());
        coordinate.put("height", activity.getHeight());
        return coordinate;
    }

    @Override
    public PageInfo queryCommentByLeavebillid(Integer id) {
        //组装businessKey
        //String businessKey =Leavebill.class.getSimpleName()+":"+id;
        String businessKey ="LeavebillOr"+":"+id;

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

    @Override
    public PageInfo queryCurrentUserHistoryTask(String name) {
        PageInfo pageInfo =new PageInfo();
        pageInfo.setTotal(this.historyService.createHistoricTaskInstanceQuery().taskAssignee(name).count());
        List<HistoricTaskInstance> list = this.historyService.createHistoricTaskInstanceQuery().taskAssignee(name).list();
        pageInfo.setList(list);
        return pageInfo;
    }
}
