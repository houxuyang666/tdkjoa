package com.tdkj.System.service;

import com.tdkj.System.entity.VO.VehicleinfoVO;
import com.tdkj.System.entity.Vehicleinfo;

import java.util.List;

/**
 * (Vehicleinfo)表服务接口
 *
 * @author makejava
 * @since 2020-08-07 17:01:43
 */
public interface VehicleinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param vehicleinfoid 主键
     * @return 实例对象
     */
    Vehicleinfo queryById(Integer vehicleinfoid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Vehicleinfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param vehicleinfo 实例对象
     * @return 实例对象
     */
    Vehicleinfo insert(Vehicleinfo vehicleinfo);

    /**
     * 修改数据
     *
     * @param vehicleinfo 实例对象
     * @return 实例对象
     */
    Vehicleinfo update(Vehicleinfo vehicleinfo);

    /**
     * 通过主键删除数据
     *
     * @param vehicleinfoid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer vehicleinfoid);

    /**
     * @Author houxuyang
     * @Description //查询所有车辆 本公司优先
     * @Date 17:22 2020/8/7
     * @Param [vehicleinfo]
     * @return java.util.List<com.tdkj.System.entity.VO.VehicleinfoVO>
     **/
    List<VehicleinfoVO> queryAllvehicleinfo(Vehicleinfo vehicleinfo);
}