package com.tdkj.System.service;

import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.EmployeeVO;

import java.util.List;

/**
 * (Employee)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
public interface EmployeeService {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeid 主键
     * @return 实例对象
     */
    Employee queryById(Integer employeeid);

    /**
     * @Author houxuyang
     * @Description //获取vo
     * @Date 11:15 2020/8/11
     * @Param [employeeid]
     * @return com.tdkj.System.entity.VO.EmployeeVO
     **/
    EmployeeVO queryemployeeVOById(Integer employeeid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Employee> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    Employee insert(Employee employee);

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 实例对象
     */
    Employee update(Employee employee);

    /**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer employeeid);

    /**
     * @Author houxuyang
     * @Description //获取用户姓名
     * @Date 15:19 2020/7/17
     * @Param [employeeid]
     * @return java.lang.String
     **/
    String getName(Integer employeeid);


    /**
     * @Author houxuyang
     * @Description //查询所有用户
     * @Date 14:30 2020/7/20
     * @Param []
     * @return java.util.List<com.tdkj.System.entity.VO.EmployeeVO>
     *@param corpid
     * @param name
     * @param  */
    List<EmployeeVO> queryAllEmployee(int corpid, String name, String idcardnumber);


    /**
     * @Author houxuyang
     * @Description //获取用户的部门ID
     * @Date 16:08 2020/7/20
     * @Param [userid]
     * @return int
     **/
    int queryByUserIdGetDeptID(Integer employeeid);

    /**
     * 获取当前用户的上级领导
     * @param employeeid
     * @return
     */
    Employee querySuperById(Integer employeeid);

    /**
     * @Author houxuyang
     * @Description //查询角色为领导的员工
     * @Date 16:51 2020/8/3
     * @Param [roleid]
     * @return java.util.List<com.tdkj.System.entity.Employee>
     **/
    List<Employee> queryemployeeByRoleid(Integer roleid, Integer corpid);
    //根据公司的ID查询员工的通讯录
   List<EmployeeVO> queryMyMail(Integer corpid,String name,String cellphone);

   //根据公司的id,姓名，电话号码查询全部员工的通讯录
    List<EmployeeVO> queryAllMail(Integer corpid, String name, String cellphone);

    //获取本公司所有的员工
    List<EmployeeVO> queryByCorpID(Integer corpid, Integer roleid);
}

