package com.tdkj.System.activiti;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 14:13
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    RepositoryService repositoryService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Resource[] resources = null;
        try {
            //resources = new PathMatchingResourcePatternResolver().getResources("classpath:processes/Leavebill.bpmn"); LeavebillOr
            resources = new PathMatchingResourcePatternResolver().getResources("classpath:processes/*.bpmn");

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Resource r : resources) {
            String addr = "processes/" + r.getFilename();
            repositoryService.createDeployment().addClasspathResource(addr).name("流程").deploy();
        }*/
    }
}
