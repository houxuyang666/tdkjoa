package com.tdkj.System.service.impl;

import com.tdkj.System.dao.EmployeeDao;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.EmployeeVO;
import com.tdkj.System.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Employee)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeDao employeeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param employeeid 主键
     * @return 实例对象
     */
    @Override
    public Employee queryById(Integer employeeid) {
        return this.employeeDao.queryById(employeeid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Employee> queryAllByLimit(int offset, int limit) {
        return this.employeeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    @Override
    public Employee insert(Employee employee) {
        this.employeeDao.insert(employee);
        return employee;
    }

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    @Override
    public Employee update(Employee employee) {
        this.employeeDao.update(employee);
        return this.queryById(employee.getEmployeeid());
    }

    /**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer employeeid) {
        return this.employeeDao.deleteById(employeeid) > 0;
    }

    @Override
    public String getName(Integer employeeid) {
        return this.employeeDao.getName(employeeid);
    }

    @Override
    public List<EmployeeVO> queryAllEmployee(int corpid) {
        return this.employeeDao.queryAllEmployee(corpid);
    }

    @Override
    public int queryByUserIdGetDeptID(Integer employeeid) {
        return this.employeeDao.queryByUserIdGetDeptID(employeeid);
    }

    @Override
    public Employee querySuperById(Integer employeeid) {
        return this.employeeDao.querySuperById(employeeid);
    }

    @Override
    public List<Employee> queryemployeeByRoleid(Integer roleid) {
        return this.employeeDao.queryemployeeByRoleid(roleid);
    }

    @Override
    public List<EmployeeVO> queryMyMail(Integer corpid,String name,String cellphone) {
        return this.employeeDao.queryMyMail(corpid,name,cellphone);
    }

    @Override
    public List<EmployeeVO> queryAllMail(Integer corpid, String name, String cellphone) {
        return this.employeeDao.queryAllMail(corpid,name,cellphone);
    }

}