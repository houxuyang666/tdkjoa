package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Department;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.DepartmentVO;
import com.tdkj.System.service.DepartmentService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultType.*;

/**
 * (Department)表控制层
 *
 * @author makejava
 * @since 2020-07-28 13:00:32
 */
@Controller
@RequestMapping("department")
public class DepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    private static final Integer RoleID = 2;

    private static final Integer Code = 0;

    /*跳转部门列表*/
    @RequestMapping("goselectdepartment")
    public String goselectdepartment() {
        return "page/department/departmentlist";
    }

    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询部门列表
     * @Date 16:02 2020/8/3
     * @Param [page, limit]
     **/
    @ResponseBody
    @RequestMapping("/selectdepartment")
    public OAResponseList selectdepartment(Integer page, Integer limit) {

        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        PageHelper.startPage(page, limit, true);
        List<DepartmentVO> departmentVOList = departmentService.queryDeptByCorpId(employee.getCorpid());
        PageInfo<DepartmentVO> pageInfo = new PageInfo<>(departmentVOList);
        return OAResponseList.setResult(Code, FIND_SUCCESS, pageInfo);
    }

    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询本公司部门列表 不分页
     * @Date 16:02 2020/8/3
     * @Param [page, limit]s
     **/
    @ResponseBody
    @RequestMapping("/queryalldepartment")
    public OAResponseList queryalldepartment() {
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        List<DepartmentVO> departmentVOList = departmentService.queryDeptByCorpId(employee.getCorpid());
        return OAResponseList.setResult(Code, FIND_SUCCESS, departmentVOList);
    }


    /*跳转添加部门*/
    @RequestMapping("goadddepartment")
    public String goadddepartment() {
        return "page/department/adddepartment";
    }

    /**
     * @return com.tdkj.System.common.OAResponse
     * @Author houxuyang
     * @Description //添加部门
     * @Date 16:02 2020/8/3
     * @Param [deptname, deptheadid, deptdesc]
     **/
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String deptname, Integer deptheadid, String deptdesc) {
        Department department = new Department();
        department.setDeptname(deptname);
        department.setDeptheadid(deptheadid);
        department.setDeptdesc(deptdesc);
        /*获取当前登陆人员的公司ID*/
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        department.setCorpid(employee.getCorpid());
        department.setCreatedate(new Date());
        this.departmentService.insert(department);
        return OAResponse.setResult(HTTP_RNS_CODE_200, ADD_SUCCESS);
    }


    /*跳转修改部门*/
    @RequestMapping("goupdatedepartment")
    public String goupdatedepartment() {
        return "page/department/updatedepartment";
    }

    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询本公司权限为领导的人员
     * @Date 11:33 2020/8/5
     * @Param [page, limit]
     **/
    @ResponseBody
    @RequestMapping("/selectdeptemployee")
    public OAResponseList selectdeptemployee(Integer page, Integer limit) {
        /*获取当前登陆人员的公司ID*/
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        List<Employee> employeeList = employeeService.queryemployeeByRoleid(RoleID, employee.getCorpid());
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(employeeList);
        pageInfo.setTotal(employeeList.size());
        return OAResponseList.setResult(Code, FIND_SUCCESS, pageInfo);
    }


    /**
     * @return com.tdkj.System.common.OAResponse
     * @Author houxuyang
     * @Description //更新部门
     * @Date 16:03 2020/8/3
     * @Param [deptname, deptheadid, deptdesc]
     **/
    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(Integer deptid, String deptname, Integer deptheadid, String deptdesc) {
        Department department = new Department();
        department.setDeptid(deptid);
        department.setDeptname(deptname);
        department.setDeptheadid(deptheadid);
        department.setDeptdesc(deptdesc);
        department.setModifydate(new Date());
        this.departmentService.update(department);
        return OAResponse.setResult(HTTP_RNS_CODE_200, UPDATE_SUCCESS);
    }

    /**
     * @return com.tdkj.System.common.OAResponse
     * @Author houxuyang
     * @Description //删除部门
     * @Date 16:03 2020/8/3
     * @Param [deptid]
     **/
    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer deptid) {
        this.departmentService.deleteById(deptid);
        return OAResponse.setResult(HTTP_RNS_CODE_200, ADD_SUCCESS);
    }

}