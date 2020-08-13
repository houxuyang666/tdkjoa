package com.tdkj.System.dao;

import com.tdkj.System.entity.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Warehouse)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-13 15:12:25
 */
public interface WarehouseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param warehouseid 主键
     * @return 实例对象
     */
    Warehouse queryById(String warehouseid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Warehouse> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param warehouse 实例对象
     * @return 对象列表
     */
    List<Warehouse> queryAll(Warehouse warehouse);

    /**
     * 新增数据
     *
     * @param warehouse 实例对象
     * @return 影响行数
     */
    int insert(Warehouse warehouse);

    /**
     * 修改数据
     *
     * @param warehouse 实例对象
     * @return 影响行数
     */
    int update(Warehouse warehouse);

    /**
     * 通过主键删除数据
     *
     * @param warehouseid 主键
     * @return 影响行数
     */
    int deleteById(String warehouseid);

    /**
     * @Author houxuyang
     * @Description //根据名称查询库中是否存在商品
     * @Date 15:30 2020/8/13
     * @Param [goodsname]
     * @return com.tdkj.System.entity.Warehouse
     **/
    Warehouse queryBygoodsname(String goodsname);
}