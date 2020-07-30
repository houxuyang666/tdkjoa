package com.tdkj.System.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Configuration
@PropertySource("classpath:application-dev.properties")//此处路径需要按需修改
public class ActivitiConfig {
    //private Logger logger = LoggerFactory.getLogger(ActivitiConfig.class);

    @Value("${spring.activiti.database-schema-update}")
    private String databaseSchemaUpdate;

    @Value("${spring.activiti.db-identity-used}")
    private boolean dbIdentityUsed;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    /*Activiti数据库支持：
    Activiti的后台是有数据库的支持，所有的表都以ACT_开头。 第二部分是表示表的用途的两个字母标识。 用途也和服务的API对应。
    ACT_RE_*: ‘RE’表示repository。 这个前缀的表包含了流程定义和流程静态资源 （图片，规则，等等）。
    ACT_RU_*: ‘RU’表示runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Activiti只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
    ACT_ID_*: ‘ID’表示identity。 这些表包含身份信息，比如用户，组等等。
    ACT_HI_*: ‘HI’表示history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
    ACT_GE_*: 通用数据， 用于不同场景下，如存放资源文件。
    */

    @Bean
    public ProcessEngine processEngine(DataSourceTransactionManager transactionManager, DataSource dataSource) throws IOException {
        log.info("==========activiti=======开始==============");
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();

    /*  自动部署已有的流程文件
    作用相当于 (据bpmn文件部署流程repositoryService.createDeployment().addClasspathResource("singleAssignee.bpmn").deploy();)*/

        //Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes/*.bpmn");
        configuration.setTransactionManager(transactionManager);
        //设置数据源
        configuration.setDataSource(dataSource);
        //是否每次都更新数据库
        //configuration.setDatabaseSchemaUpdate(databaseSchemaUpdate);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        //configuration.setAsyncExecutorActivate(false);
        //configuration.setDeploymentResources(resources);
        //设置是否使用activti自带的用户体系
        configuration.setDbIdentityUsed(dbIdentityUsed);
        return configuration.buildProcessEngine();
    }


    /**
     * 工作流仓储服务
     * 可以用来部署我们的流程图，还可以创建我们的流程部署查询对象，用于查询刚刚不熟的流程列表，便于我们管理流程
     *
     *       act_ge_bytearray  二进制文件表
     *       act_re_deployment 流程部署表
     *       act_re_procdef    流程定义
     *       act_ge_property   工作流的ID算法和版本信息表
     *
     * @param processEngine
     * @return
     */

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }


    /**
     * 工作流运行服务
     *  运行流程服务主要用来开启流程实例，一个流程实例对应多个任务，也就是多个流程节点
     *  好比如请假审批是一个流程实例，部门主管，部门经理，总经理都是节点，我们开启服务是通过流程定义key或者流程定义ID来开启的
     *
     *  备注：当我们用仓储服务部署了流程图之后，就会产生一个流程部署ID，一个流程部署ID对应一个流程定义，一个流程定义对应多个流程实例
     *  ，一个流程实例对应多个任务节点，这样的逻辑应该明白，比如我设计了一个手机图纸（流程定义），是可以共多个人生产出手机并
     *  去使用的，这些人就是流程实例，手机里面的各种功能就是任务节点。
     * @param processEngine
     * @return
     */

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    /**
     * 工作流任务服务
     *
     *  用来领取、完成、查询任务列表功能
     *
     *  act_ru_execution 流程启动一次只要没有执行完，就会有一条数据
     *  act_ru_task      可能有多条数据
     *  act_ru_veriable  记录流程运行时的流程变量
     *  act_ru_identitylink   存放流程办理人的信息
     * @param processEngine
     * @return
     */

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }


    /**
     * 工作流历史数据服务
     *
     *  可以查看审批人曾经审批完成了那些项目，审批项目总花费了多少时间，以及在哪个环节比较耗费时间，便于审批人查看历史信息
     * @param processEngine
     * @return
     */

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }


    /**
     * 工作流管理服务
     *
     * @param processEngine
     * @return
     */

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }


    /**
     * 工作流唯一服务
     *  主要操作用户信息，用户分组信息等，组信息包括如部门表和职位表，我一般都是自己建好表来存储用户信息和组信息。
     * @param processEngine
     * @return
     */

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }


    /**
     * 表单操作流
     *
     * @param processEngine
     * @return
     */

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }
}

