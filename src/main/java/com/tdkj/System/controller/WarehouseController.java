package com.tdkj.System.controller;

import com.tdkj.System.service.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * (Warehouse)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
@Controller
@RequestMapping("warehouse")
public class WarehouseController {
    /**
     * 服务对象
     */
    @Resource
    private WarehouseService warehouseService;

    /*跳转仓库页面*/
    @RequestMapping("/goselectwarehouse")
    public String goselectwarehouse() {
        return "page/warehouse/warehouselist";
    }


}