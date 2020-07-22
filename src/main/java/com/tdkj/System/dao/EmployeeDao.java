package com.tdkj.System.dao;

import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.EmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Employee)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
public interface EmployeeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param employeeid 主键
     * @return 实例对象
     */
    Employee queryById(Integer employeeid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Employee> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param employee 实例对象
     * @return 对象列表
     */
    List<Employee> queryAll(Employee employee);

    /**
     * 新增数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    int insert(Employee employee);

    /**
     * 修改数据
     *
     * @param employee 实例对象
     * @return 影响行数
     */
    int update(Employee employee);

    /**
     * 通过主键删除数据
     *
     * @param employeeid 主键
     * @return 影响行数
     */
    int deleteById(Integer employeeid);

    /**
     * @Author houxuyang
     * @Description //获取用户姓名
     * @Date 15:20 2020/7/17
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
     **/
    List<EmployeeVO> queryAllEmployee(Integer corpid);
    /**
     * @Author houxuyang
     * @Description //获取用户的部门ID
     * @Date 16:10 2020/7/20
     * @Param [userid]
     * @return int
     **/
    int queryByUserIdGetDeptID(Integer employeeid);
}