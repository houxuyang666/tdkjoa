package com.tdkj.System.service;

import com.tdkj.System.entity.Department;
import com.tdkj.System.entity.VO.DepartmentVO;

import java.util.List;

/**
 * (Department)表服务接口
 *
 * @author makejava
 * @since 2020-07-28 13:00:31
 */
public interface DepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param deptid 主键
     * @return 实例对象
     */
    Department queryById(Integer deptid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Department> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department insert(Department department);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department update(Department department);

    /**
     * 通过主键删除数据
     *
     * @param deptid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer deptid);

    /**
     * @Author houxuyang
     * @Description //查询VO
     * @Date 15:55 2020/8/3
     * @Param []
     * @return java.util.List<com.tdkj.System.entity.VO.DepartmentVO>
     **/
    List<DepartmentVO> queryDeptByCorpId(Integer corpid);
}