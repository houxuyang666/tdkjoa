package com.tdkj.System.controller;

import com.tdkj.System.entity.Vehicleorders;
import com.tdkj.System.service.VehicleordersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * (Vehicleorders)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:09
 */
@Controller
@RequestMapping("vehicleorders")
public class VehicleordersController {
    /**
     * 服务对象
     */
    @Resource
    private VehicleordersService vehicleordersService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Vehicleorders selectOne(String id) {
        return this.vehicleordersService.queryById(id);
    }

}