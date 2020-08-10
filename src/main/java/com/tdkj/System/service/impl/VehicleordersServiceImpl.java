package com.tdkj.System.service.impl;

import com.tdkj.System.dao.VehicleordersDao;
import com.tdkj.System.entity.VO.VehicleordersVO;
import com.tdkj.System.entity.Vehicleorders;
import com.tdkj.System.service.VehicleordersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Vehicleorders)表服务实现类
 *
 * @author makejava
 * @since 2020-08-07 17:01:48
 */
@Service("vehicleordersService")
public class VehicleordersServiceImpl implements VehicleordersService {
    @Resource
    private VehicleordersDao vehicleordersDao;

    /**
     * 通过ID查询单条数据
     *
     * @param orderid 主键
     * @return 实例对象
     */
    @Override
    public Vehicleorders queryById(String orderid) {
        return this.vehicleordersDao.queryById(orderid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Vehicleorders> queryAllByLimit(int offset, int limit) {
        return this.vehicleordersDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param vehicleorders 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicleorders insert(Vehicleorders vehicleorders) {
        this.vehicleordersDao.insert(vehicleorders);
        return vehicleorders;
    }

    /**
     * 修改数据
     *
     * @param vehicleorders 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicleorders update(Vehicleorders vehicleorders) {
        this.vehicleordersDao.update(vehicleorders);
        return this.queryById(vehicleorders.getOrderid());
    }

    /**
     * 通过主键删除数据
     *
     * @param orderid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String orderid) {
        return this.vehicleordersDao.deleteById(orderid) > 0;
    }

    @Override
    public List<VehicleordersVO> selecvehicleorders(Integer employeeid) {
        return   this.vehicleordersDao.selecvehicleorders(employeeid);
    }
}