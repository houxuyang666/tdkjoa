package com.tdkj.System.service.impl;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.entity.VO.WorkFlowVO;
import com.tdkj.System.entity.ect.ActProcessDefinitionEntity;
import com.tdkj.System.entity.ect.ActTaskEntity;
import com.tdkj.System.entity.ect.ActdeploymentEntity;
import com.tdkj.System.service.LeavebillService;
import com.tdkj.System.service.WorkFlowService;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
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
        String processDefinitionKey = Leavebill.class.getSimpleName(); //获取对象名称
        String businessKey =processDefinitionKey+":"+leavbillid; //"Leavbill:1"
        Map<String, Object> variables =new HashMap<>();
        /*设置流程变量获取当前用户 也就是谁提交的请假单（设置下个流程的办理人）*/
        variables.put("username", ShiroUtils.getPrincipal().getUsername());
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
        //更新请假单状态
        Leavebill leavebill =new Leavebill();
        leavebill.setId(leavbillid);
        leavebill.setState("1");
        leavebillService.update(leavebill);
    }

    @Override
    public PageInfo qureyCurrentUserTask(Integer page, Integer limit) {
        //1.得到办理人信息
        String assignee =ShiroUtils.getPrincipal().getUsername();
        //2.查询总数
        long count = this.taskService.createTaskQuery().taskAssignee(assignee).count();
        //3.查询集合
        List<Task> taskList = this.taskService.createTaskQuery().taskAssignee(assignee).listPage(page, limit);

        List<ActTaskEntity> actTaskEntities = new ArrayList<>();
        for (Task task : taskList) {
            ActTaskEntity entity = new ActTaskEntity();
            BeanUtils.copyProperties(task,entity);
            actTaskEntities.add(entity);
        }
        PageInfo pageInfo =new PageInfo();
        pageInfo.setTotal(count);
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
}