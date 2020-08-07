package com.tdkj.System.service;

import com.tdkj.System.entity.Vehicleorders;

import java.util.List;

/**
 * (Vehicleorders)表服务接口
 *
 * @author makejava
 * @since 2020-08-07 17:01:47
 */
public interface VehicleordersService {

    /**
     * 通过ID查询单条数据
     *
     * @param orderid 主键
     * @return 实例对象
     */
    Vehicleorders queryById(String orderid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Vehicleorders> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param vehicleorders 实例对象
     * @return 实例对象
     */
    Vehicleorders insert(Vehicleorders vehicleorders);

    /**
     * 修改数据
     *
     * @param vehicleorders 实例对象
     * @return 实例对象
     */
    Vehicleorders update(Vehicleorders vehicleorders);

    /**
     * 通过主键删除数据
     *
     * @param orderid 主键
     * @return 是否成功
     */
    boolean deleteById(String orderid);

}