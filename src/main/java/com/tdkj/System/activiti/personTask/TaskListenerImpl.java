package com.tdkj.System.activiti.personTask;

import com.tdkj.System.entity.Employee;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.utils.ShiroUtils;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

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
        System.out.println("进来了TaskListenerImpl");

        //取出IOC容器
        System.out.println(ShiroUtils.getPrincipal().getEmployeeid());
        HttpServletRequest request= ShiroUtils.getCurrentServletRequest();
        ApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext());
        //从IOC容器里面取出UserService
        EmployeeService employeeService=applicationContext.getBean(EmployeeService.class);

        //1.根据用户id获取当前用户的上级领导
        Employee employee =employeeService.querySuperById(ShiroUtils.getPrincipal().getEmployeeid());
        //2.指定办理人
        delegateTask.setAssignee(employee.getName());
    }
}
