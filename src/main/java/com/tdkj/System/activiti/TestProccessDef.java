package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/22 11:42
 */
@Slf4j
public class TestProccessDef {

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

    /*
     * @Author houxuyang
     * @Description //使用zip部署
     * @Date 11:43 2020/7/22
     * @Param
     * @return
     **/
    @Test
    public void deployProcessByZip(){
        InputStream inputStream =this.getClass().getResourceAsStream("/HelloWorld.zip");

        //通过路径部署流程信息
        RepositoryService repositoryService= this.processEngine.getRepositoryService();
        ZipInputStream zipInputStream =new ZipInputStream(inputStream);
        Deployment deploy=repositoryService.createDeployment().name("请假流程001") //流程设置一个名字
                .addZipInputStream(zipInputStream).deploy();
        log.info("部署成功：流程部署ID："+deploy.getId());
    }

    /*查询流程部署信息 act_re_deployment
    * */
    @Test
    public void queryProcessDef(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        //创建部署信息的查询
        String deploumentID="2501";
        Deployment deployment =repositoryService.createDeploymentQuery() //查询结果
        //条件
        .deploymentId(deploumentID) //根据部署ID查询
//        .deploymentName(name)//根据部署名称去查询
//        .deploymentTenantId(tenantid) //根据tenantid查询
//        .deploymentNameLike(namelike) //根据部署名称模糊查询
//        .deploymentTenantIdLike(tenantidlike) //根据tenantid模糊查询
        //排序
//        .orderByDeploymentId().asc()//根据部署ID升序  .desc（）降序  升降序一定要跟在orderByDeployment*()的后面
//        .orderByDeploymenTime().desc() //根据部署时间降序
//        .orderByDeploymentName().asc() //根据部署名称升序
        //结果集  只能有一个
//        .list()//查询返回list
//        .listPage(rsResult,maxResults) //分页查询返回list集合
        .singleResult(); //返回单个对象   单条数据无需排序
//          .count(); //返回总数

        log.info("部署ID："+deployment.getId());
        log.info("部署名称："+deployment.getName());
        log.info("部署时间："+deployment.getDeploymentTime());
    }

    /*查询流程定义信息 act_re_procdef
    * */
    @Test
    public void queryProcDef(){
        RepositoryService repositoryService =this.processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                //条件
//        .deploymentId(deploymentId) //根据部署ID查询
//        .deploymentIds(deploymentIds)//根据部署ID的集合查询Set<String> deploymentIds
//        .processDefinitionId(processDefinitionId)//根据流程定义ID HelloWorld1：4
//        .processDefinitionIds(processDefinitionIds) //根据流程定义的IDS查询
//        .processDefinitionKey(processDefinitionKey) //根据流程定义的key查询
//        .processDefinitionKeyLike(processDefinitionKeyLike)//根据流程定义的key模糊查询
//        .processDefinitionName(processDefinitionName)//根据流程定义的名称查询
//        .processDefinitionNameLike(processDefinitionNameLike)//根据流程定义的名称模糊查询
//        .processDefinitionResourceName(resourceName)//根据流程图的BPMN文件名查询
//        .processDefinitionResourceNameLike(resourceNameLike)//根据流程图的BPMN文件名模糊查询
//        .processDefinitionVersion(processDefinitionVersion)//根据流程定义的版本查询
//        .processDefinitionVersionGreaterThan(processDefinitionVersion)//version>num
//        .processDefinitionVersionGreaterThanOrEquals(processDefinitionVersion)//version>=num
//        .processDefinitionVersionLowerThan(processDefinitionVersion)//version<num
//        .processDefinitionVersionLowerThanOrEquals(processDefinitionVersion)//version<=num
                //排序 
//        .orderByDeploymentId()
//        .orderByProcessDefinitionId()
//        .orderByProcessDefinitionKey()
//        .orderByProcessDefinitionName()
//        .orderByProcessDefinitionVersion()
                //结果集
                .list();
//            .listPage(firstResult, maxResults)
//        .count()
//        .singleResult() 一个结果
        if(null!=list&&list.size()>0){
            for (ProcessDefinition pd : list) {
                log.info("流程定义ID："+pd.getId());
                log.info("流程部署ID："+pd.getDeploymentId());
                log.info("流程定义KEY："+pd.getKey());
                log.info("流程定义的名称："+pd.getName());
                log.info("流程定义的BPMN文件："+pd.getResourceName()); //bpmn文件名
                log.info("流程图片名称："+pd.getDiagramResourceName());//png的文件名
                log.info("流程版本号"+pd.getVersion());
            }
        }
    }

    /*删除流程定义*/
    @Test
    public void deleteProcessDef(){
        RepositoryService repositoryService =this.processEngine.getRepositoryService();
        String deploymentId="40001";  //该值为act_re_procdef 中的DEPLOYMENT_ID_ 以及act_re_deployment 中的id
        //根据流程部署ID删除流程定义，如果当前的ID的流程正在执行，那么会报错。
        //repositoryService.deleteDeployment(deploymentId);

        //根据流程部署ID删除流程定义，如果当前的ID的流程正在执行，会把正在执行的流程数据删除 act_ru_* 和act_hi_*表的数据
        repositoryService.deleteDeployment(deploymentId,true);  //false 则报错 等同于上面的方法
        log.info("删除数据");
    }

    /*修改流程定义*/
    /*修改流程图之后重新部署，只要KEY不变，他的版本号就会+1*/

    /*输出流程图*/
    @Test
    public void viewProcessImg(){
        RepositoryService repositoryService =this.processEngine.getRepositoryService();
        //根据流程部署ID查询流程定义对象
        String deploymentId="5001";
        ProcessDefinition processDefinition =repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        //从流程定义对象里面查询出流程定义id
        String processDefinitionId=processDefinition.getId();
        InputStream inputStream =repositoryService.getProcessDiagram(processDefinitionId);
        File file =new File("D:/"+processDefinition.getDiagramResourceName());

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            int len=0;
            byte[] b =new byte[1024];
            while ((len=inputStream.read(b))!=-1){
                outputStream.write(b,0,len);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
            log.info("输出成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*查询最新的流程定义*/
    @Test
    public void queryNewProcessDef(){
        Map<String,ProcessDefinition> map =new HashMap<>();
        //查询所有的流程定义根据版本号升序
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        if(null!=list&&list.size()>0){
            for (ProcessDefinition processDefinition : list) {
                map.put(processDefinition.getKey(),processDefinition);
            }
        }
        //循环map集合
        Collection<ProcessDefinition> values = map.values();
        for (ProcessDefinition pd : values) {
                log.info("流程定义ID："+pd.getId());
                log.info("流程部署ID："+pd.getDeploymentId());
                log.info("流程定义KEY："+pd.getKey());
                log.info("流程定义的名称："+pd.getName());
                log.info("流程定义的BPMN文件："+pd.getResourceName()); //bpmn文件名
                log.info("流程图片名称："+pd.getDiagramResourceName());//png的文件名
                log.info("流程版本号"+pd.getVersion());
        }
    }

    /*已知Key
    * 删除流程定义（删除key相同的所有不同版本的流程定义）
    * */
    @Test
    public void deleteAllSameVersion(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        //根据流程定义的key查询流程集合
        String processDefinitionKey="leavebill";
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();
        if(null!=list&&list.size()>0){
            for (ProcessDefinition pd : list) {
                repositoryService.deleteDeployment(pd.getDeploymentId(),true);
                log.info("删除流程定义："+pd.getId()+"成功");
            }
        }
    }

}
