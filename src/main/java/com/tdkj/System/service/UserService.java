package com.tdkj.System.service;

import com.tdkj.System.entity.User;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userid 主键
     * @return 实例对象
     */
    User queryById(Integer userid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userid);

    /**
     * @Author houxuyang
     * @Description //根据名称查询对象信息
     * @Date 15:11 2020/7/17
     * @Param [username]
     * @return com.tdkj.System.entity.User
     **/
    User findByName(String username);

    /**
     * @Author houxuyang
     * @Description //根据员工id获取登陆信息
     * @Date 10:00 2020/8/10
     * @Param [employeeid]
     * @return com.tdkj.System.entity.User
     **/
    User queryByemployeeid(Integer employeeid);
}