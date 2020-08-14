package com.tdkj.System.service;

import com.tdkj.System.entity.Warehouse;

import java.util.List;

/**
 * (Warehouse)表服务接口
 *
 * @author makejava
 * @since 2020-08-13 15:12:26
 */
public interface WarehouseService {

    /**
     * 通过ID查询单条数据
     *
     * @param warehouseid 主键
     * @return 实例对象
     */
    Warehouse queryById(String warehouseid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Warehouse> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param warehouse 实例对象
     * @return 实例对象
     */
    Warehouse insert(Warehouse warehouse);

    /**
     * 修改数据
     *
     * @param warehouse 实例对象
     * @return 实例对象
     */
    Warehouse update(Warehouse warehouse);

    /**
     * 通过主键删除数据
     *
     * @param warehouseid 主键
     * @return 是否成功
     */
    boolean deleteById(String warehouseid);

    /**
     * @Author houxuyang
     * @Description //根据名称查询仓库是否存在该商品
     * @Date 15:29 2020/8/13
     * @Param [goodsname]
     * @return com.tdkj.System.entity.Warehouse
     **/
    Warehouse queryBygoodsname(String goodsname);

    /**
     * @Author houxuyang
     * @Description //查询该公司的仓库列表
     * @Date 10:17 2020/8/14
     * @Param [warehouse]
     * @return java.util.List<com.tdkj.System.entity.Warehouse>
     **/
    List<Warehouse> queryAll(Warehouse warehouse);
}