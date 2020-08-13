package com.tdkj.System.service;

import com.tdkj.System.entity.Role;

import java.util.List;

/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleid 主键
     * @return 实例对象
     */
    Role queryById(Integer roleid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Role> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param roleid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer roleid);

    /**
     * @Author houxuyang
     * @Description //获取除超级管理员以外的权限
     * @Date 14:43 2020/8/13
     * @Param []
     * @return java.util.List<com.tdkj.System.entity.Role>
     **/
    List<Role> queryAll(Role role);
}