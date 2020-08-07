package com.tdkj.System.controller;

import com.tdkj.System.entity.Vehicleinfo;
import com.tdkj.System.service.VehicleinfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * (Vehicleinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
@Controller
@RequestMapping("vehicleinfo")
public class VehicleinfoController {
    /**
     * 服务对象
     */
    @Resource
    private VehicleinfoService vehicleinfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Vehicleinfo selectOne(Integer id) {
        return this.vehicleinfoService.queryById(id);
    }

}