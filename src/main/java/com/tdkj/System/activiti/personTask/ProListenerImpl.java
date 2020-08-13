package com.tdkj.System.activiti.personTask;

import com.tdkj.System.entity.Department;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.service.DepartmentService;
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
public class ProListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进来了ProListenerImpl");

        //取出IOC容器
        System.out.println(ShiroUtils.getPrincipal().getEmployeeid());
        HttpServletRequest request= ShiroUtils.getCurrentServletRequest();
        ApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext());
        //从IOC容器里面取出UserService
        EmployeeService employeeService=applicationContext.getBean(EmployeeService.class);
        DepartmentService departmentService=applicationContext.getBean(DepartmentService.class);

        //1.获取综合办公室领导的ID
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        //模糊查询综合办公室
        String deptname ="综合办公室";
        Department department =departmentService.queryDeptLikeName(deptname,employee.getCorpid());
        Employee deptemployee =employeeService.queryById(department.getDeptheadid());

        //2.指定办理人
        delegateTask.setAssignee(deptemployee.getName());
    }
}
