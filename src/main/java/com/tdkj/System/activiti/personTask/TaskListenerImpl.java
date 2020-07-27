package com.tdkj.System.activiti.personTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/23 15:26
 */
@Component
public class TaskListenerImpl implements TaskListener {

    /*监听器*/
    /* 修改流程图

    申请人不变， 删除部门审核以及总经理审核的变量*/
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进来了");
        /*指定办理人*/
        delegateTask.setAssignee("李四");
    }
}
