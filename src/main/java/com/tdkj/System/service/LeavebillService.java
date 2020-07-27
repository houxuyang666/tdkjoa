package com.tdkj.System.service;

import com.tdkj.System.entity.Leavebill;

import java.util.List;

/**
 * (Leavebill)表服务接口
 *
 * @author makejava
 * @since 2020-07-24 08:36:13
 */
public interface LeavebillService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Leavebill queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Leavebill> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param leavebill 实例对象
     * @return 实例对象
     */
    Leavebill insert(Leavebill leavebill);

    /**
     * 修改数据
     *
     * @param leavebill 实例对象
     * @return 实例对象
     */
    Leavebill update(Leavebill leavebill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
    /*查询本人请假单*/

    List<Leavebill> queryAllLeavebill(Leavebill leavebill);
}