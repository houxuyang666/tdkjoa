package com.tdkj.System.service.impl;

import com.tdkj.System.dao.VehicleinfoDao;
import com.tdkj.System.entity.VO.VehicleinfoVO;
import com.tdkj.System.entity.Vehicleinfo;
import com.tdkj.System.service.VehicleinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Vehicleinfo)表服务实现类
 *
 * @author makejava
 * @since 2020-08-07 17:01:43
 */
@Service("vehicleinfoService")
public class VehicleinfoServiceImpl implements VehicleinfoService {
    @Resource
    private VehicleinfoDao vehicleinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param vehicleinfoid 主键
     * @return 实例对象
     */
    @Override
    public Vehicleinfo queryById(Integer vehicleinfoid) {
        return this.vehicleinfoDao.queryById(vehicleinfoid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Vehicleinfo> queryAllByLimit(int offset, int limit) {
        return this.vehicleinfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param vehicleinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicleinfo insert(Vehicleinfo vehicleinfo) {
        this.vehicleinfoDao.insert(vehicleinfo);
        return vehicleinfo;
    }

    /**
     * 修改数据
     *
     * @param vehicleinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicleinfo update(Vehicleinfo vehicleinfo) {
        this.vehicleinfoDao.update(vehicleinfo);
        return this.queryById(vehicleinfo.getVehicleinfoid());
    }

    /**
     * 通过主键删除数据
     *
     * @param vehicleinfoid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer vehicleinfoid) {
        return this.vehicleinfoDao.deleteById(vehicleinfoid) > 0;
    }

    /**
     * @Author houxuyang
     * @Description //查询所有车辆 本公司优先
     * @Date 17:22 2020/8/7
     * @Param [vehicleinfo]
     * @return java.util.List<com.tdkj.System.entity.VO.VehicleinfoVO>
     **/
    @Override
    public List<VehicleinfoVO> queryAllvehicleinfo(Vehicleinfo vehicleinfo) {
        return this.vehicleinfoDao.queryAllvehicleinfo(vehicleinfo);
    }


}