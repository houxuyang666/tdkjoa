package com.tdkj.System.controller;

import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.entity.ect.ActProcessDefinitionEntity;
import com.tdkj.System.entity.ect.ActdeploymentEntity;
import com.tdkj.System.service.WorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tdkj.System.common.OAResultType.*;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 8:53
 */
@Slf4j
@Controller
@RequestMapping("workflow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private RepositoryService repositoryService;


    @RequestMapping("/goworkflow")
    public String goworkflow() {
        return "page/workflowlist";
    }


    /*查询流程部署信息以及流程定义信息
     * 返回两个数据 一个是流程部署的信息，一个是流程定义的信息， 需要在页面上分成两个表格进行显示*/
    @ResponseBody
    @RequestMapping("/selectworkflow")
    public OAResponseList querProcessDeploy() {
        /*查询流程部署信息*/
        /*查询出数据后*/
        long count = repositoryService.createDeploymentQuery().count();
        List<Deployment> list = repositoryService.createDeploymentQuery().listPage(0, 10);
        /*由于属性匹配不上那么我们创建新的对象来接收*/
        /*流程部署对象*/
        List<ActdeploymentEntity> actdeploymentEntityArrayList = new ArrayList<ActdeploymentEntity>();
        for (Deployment deployment : list) {
            ActdeploymentEntity entity = new ActdeploymentEntity();
            BeanUtils.copyProperties(deployment, entity);
            actdeploymentEntityArrayList.add(entity);
        }
        PageInfo pageInfo =new PageInfo();
        pageInfo.setTotal(count);
        pageInfo.setList(actdeploymentEntityArrayList);

        /*查询流程定义信息*/
        Set<String> deploymentIds = new HashSet<>();
        for (Deployment deployment : list) {
            deploymentIds.add(deployment.getId());
        }
        long count1 = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).count();
        List<ProcessDefinition> processDefinitions = this.repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).orderByProcessDefinitionVersion().desc().listPage(0, 10);
        /*由于属性匹配不上那么我们创建新的对象来接收*/
        /*流程定义对象*/
        List<ActProcessDefinitionEntity> actProcessDefinitionEntities = new ArrayList<ActProcessDefinitionEntity>();
        for (ProcessDefinition pro : processDefinitions) {
            ActProcessDefinitionEntity entity = new ActProcessDefinitionEntity();
            BeanUtils.copyProperties(pro, entity);
            actProcessDefinitionEntities.add(entity);
        }
        PageInfo pageInfo2 =new PageInfo();
        pageInfo2.setTotal(count1);
        pageInfo2.setList(actProcessDefinitionEntities);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo, pageInfo2);
    }


    @RequestMapping("/goadd")
    public String goadd() {
        return "page/Test/addworkflow";
    }


    /*添加流程部署及流程定义，但暂不可用，采用项目启动时自动加载流程定义*/
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(@RequestParam("file") MultipartFile file, String deploymentName) {
        try {
            this.workFlowService.addWorkFlow(file.getInputStream(), deploymentName);
            return OAResponse.setResult(200, ADD_SUCCESS);
        } catch (IOException io) {
            io.printStackTrace();
            return OAResponse.setResult(500, ADD_FAULT);
        }
    }


    /*根据流程部署ID删除流程部署及流程定义，在service中 第二个参数为true，为暴力删除*/
    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(String deploymentid) {
        this.workFlowService.deleteWorkFlow(deploymentid);
        return OAResponse.setResult(500, REMOVE_SUCCESS);
    }

    /*循环暴力删除流程定义，调用方法与上面一致*/
    @ResponseBody
    @RequestMapping("/batchdelete")
    public OAResponse batchdelete() {
        /*批量删除部署的流程*/
        String[] ids = {"2501", "27501", "30001", "32501"};
        for (String id : ids) {
            this.workFlowService.deleteWorkFlow(id);
        }
        return OAResponse.setResult(200, REMOVE_SUCCESS);
    }


    @RequestMapping("/goviewProcessImage")
    public ModelAndView goviewProcessImage(String deploymentId) {
        log.info(deploymentId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/Test/viewProcessImage");
        modelAndView.addObject("deploymentId",deploymentId);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/viewProcessImage")
    public void viewProcessImage(String deploymentId, HttpServletResponse response) {
        log.info(deploymentId);
        /*查看流程图*/
        InputStream stream = this.workFlowService.qureyProcessDeploymentImage(deploymentId);
        /*查询获取流之后转成Image流*/
        ServletOutputStream outputStream = null;
        try {
            BufferedImage image = ImageIO.read(stream);
            outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            stream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*根据请假单ID启动流程定义*/
    @Transactional
    @ResponseBody
    @RequestMapping("/startProcess")
    public OAResponse startProcess(Integer leavbillid) {
        try {
            this.workFlowService.startProcess(leavbillid);
            return OAResponse.setResult(200, "启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return OAResponse.setResult(500, "启动失败");
        }
    }


    /*跳转到我的待办任务页面*/
    @RequestMapping("/goTaskManger")
    public String goTaskManger() {
        log.info("goTaskManger");
        return "page/Test/taskManger";
    }


    /*查询我的代办任务*/
    @ResponseBody
    @RequestMapping("/loadCurrentUserTask")
    public OAResponseList queryCurrentUserTask() {
        log.info("loadCurrentUserTask");
        /*分页值需要页面传*/
        Integer page =0;
        Integer limit =10;
        return OAResponseList.setResult(0, FIND_SUCCESS,this.workFlowService.qureyCurrentUserTask(page,limit));
    }



    /*跳转到办理任务页面*/
    @RequestMapping("/goDoTask")
    public ModelAndView goDoTask(String taskId) {
        log.info("goDoTask");
        log.info(taskId);
        //1.根据任务ID查询请假单信息
        Leavebill leavebill =this.workFlowService.queryLeaveBillByTaskId(taskId);
        //2.根据任务ID查询连线信息
        List<String> outcomeName=this.workFlowService.queryOutComeByTaskId(taskId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/Test/dotaskManger");
        modelAndView.addObject("leavebill",leavebill);
        modelAndView.addObject("outcomes",outcomeName);
        return modelAndView;
    }




}
