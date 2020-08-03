package com.tdkj.System.dao;

import com.tdkj.System.entity.Department;
import com.tdkj.System.entity.VO.DepartmentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Department)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-28 13:00:30
 */
public interface DepartmentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param deptid 主键
     * @return 实例对象
     */
    Department queryById(Integer deptid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Department> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param department 实例对象
     * @return 对象列表
     */
    List<Department> queryAll(Department department);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int insert(Department department);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int update(Department department);

    /**
     * 通过主键删除数据
     *
     * @param deptid 主键
     * @return 影响行数
     */
    int deleteById(Integer deptid);

    /**
     * @Author houxuyang
     * @Description //查询VO
     * @Date 15:56 2020/8/3
     * @Param []
     * @return java.util.List<com.tdkj.System.entity.VO.DepartmentVO>
     **/
    List<DepartmentVO> queryAlldept();
}