package com.tdkj.System.service.impl;

import com.tdkj.System.dao.WarehouseDao;
import com.tdkj.System.entity.Warehouse;
import com.tdkj.System.service.WarehouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Warehouse)表服务实现类
 *
 * @author makejava
 * @since 2020-08-13 15:12:27
 */
@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Resource
    private WarehouseDao warehouseDao;

    /**
     * 通过ID查询单条数据
     *
     * @param warehouseid 主键
     * @return 实例对象
     */
    @Override
    public Warehouse queryById(String warehouseid) {
        return this.warehouseDao.queryById(warehouseid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Warehouse> queryAllByLimit(int offset, int limit) {
        return this.warehouseDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param warehouse 实例对象
     * @return 实例对象
     */
    @Override
    public Warehouse insert(Warehouse warehouse) {
        this.warehouseDao.insert(warehouse);
        return warehouse;
    }

    /**
     * 修改数据
     *
     * @param warehouse 实例对象
     * @return 实例对象
     */
    @Override
    public Warehouse update(Warehouse warehouse) {
        this.warehouseDao.update(warehouse);
        return this.queryById(warehouse.getWarehouseid());
    }

    /**
     * 通过主键删除数据
     *
     * @param warehouseid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String warehouseid) {
        return this.warehouseDao.deleteById(warehouseid) > 0;
    }

    /**
     * @Author houxuyang
     * @Description //根据名称查询库中是否存在商品
     * @Date 15:30 2020/8/13
     * @Param [goodsname]
     * @return com.tdkj.System.entity.Warehouse
     **/
    @Override
    public Warehouse queryBygoodsname(String goodsname) {
        return this.warehouseDao.queryBygoodsname(goodsname);
    }

    /**
     * @Author houxuyang
     * @Description //查询该公司的仓库列表
     * @Date 10:17 2020/8/14
     * @Param [warehouse]
     * @return java.util.List<com.tdkj.System.entity.Warehouse>
     **/
    @Override
    public List<Warehouse> queryAll(Warehouse warehouse) {
        return this.warehouseDao.queryAll(warehouse);
    }

}