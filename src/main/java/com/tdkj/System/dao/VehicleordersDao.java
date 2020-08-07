package com.tdkj.System.dao;

import com.tdkj.System.entity.Vehicleorders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Vehicleorders)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-07 17:01:47
 */
public interface VehicleordersDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderid 主键
     * @return 实例对象
     */
    Vehicleorders queryById(String orderid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Vehicleorders> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param vehicleorders 实例对象
     * @return 对象列表
     */
    List<Vehicleorders> queryAll(Vehicleorders vehicleorders);

    /**
     * 新增数据
     *
     * @param vehicleorders 实例对象
     * @return 影响行数
     */
    int insert(Vehicleorders vehicleorders);

    /**
     * 修改数据
     *
     * @param vehicleorders 实例对象
     * @return 影响行数
     */
    int update(Vehicleorders vehicleorders);

    /**
     * 通过主键删除数据
     *
     * @param orderid 主键
     * @return 影响行数
     */
    int deleteById(String orderid);

}