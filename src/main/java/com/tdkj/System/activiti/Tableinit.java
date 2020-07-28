package com.tdkj.System.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class Tableinit {


    public static void main(String[] args) {
        // 引擎配置
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        pec.setJdbcDriver("com.mysql.jdbc.Driver");
        pec.setJdbcUrl("jdbc:mysql://192.168.1.40:3306/activiti?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        pec.setJdbcUsername("tdkj");
        pec.setJdbcPassword("tdkj123");
        /**
         * false 不能自动创建表
         * create-drop 先删除表再创建表
         * true 自动创建和更新表
         */
        pec.setDatabaseSchemaUpdate("drop-create");
        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }
}
