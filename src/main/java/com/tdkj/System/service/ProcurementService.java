package com.tdkj.System.service;

import com.tdkj.System.entity.Procurement;

import java.util.List;

/**
 * (Procurement)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:50:56
 */
public interface ProcurementService {

    /**
     * 通过ID查询单条数据
     *
     * @param proid 主键
     * @return 实例对象
     */
    Procurement queryById(String proid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Procurement> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param procurement 实例对象
     * @return 实例对象
     */
    Procurement insert(Procurement procurement);

    /**
     * 修改数据
     *
     * @param procurement 实例对象
     * @return 实例对象
     */
    Procurement update(Procurement procurement);

    /**
     * 通过主键删除数据
     *
     * @param proid 主键
     * @return 是否成功
     */
    boolean deleteById(String proid);

}