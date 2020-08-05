package com.tdkj.System.service.impl;

import com.tdkj.System.dao.DepartmentDao;
import com.tdkj.System.entity.Department;
import com.tdkj.System.entity.VO.DepartmentVO;
import com.tdkj.System.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Department)表服务实现类
 *
 * @author makejava
 * @since 2020-07-28 13:00:31
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentDao departmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param deptid 主键
     * @return 实例对象
     */
    @Override
    public Department queryById(Integer deptid) {
        return this.departmentDao.queryById(deptid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Department> queryAllByLimit(int offset, int limit) {
        return this.departmentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department insert(Department department) {
        this.departmentDao.insert(department);
        return department;
    }

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department update(Department department) {
        this.departmentDao.update(department);
        return this.queryById(department.getDeptid());
    }

    /**
     * 通过主键删除数据
     *
     * @param deptid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer deptid) {
        return this.departmentDao.deleteById(deptid) > 0;
    }

    @Override
    public List<DepartmentVO> queryDeptByCorpId(Integer corpid) {
        return this.departmentDao.queryDeptByCorpId(corpid);
    }

}