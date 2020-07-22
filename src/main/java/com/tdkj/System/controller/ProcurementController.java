package com.tdkj.System.controller;

import com.tdkj.System.entity.Procurement;
import com.tdkj.System.service.ProcurementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Procurement)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:50:58
 */
@RestController
@RequestMapping("procurement")
public class ProcurementController {
    /**
     * 服务对象
     */
    @Resource
    private ProcurementService procurementService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Procurement selectOne(String id) {
        return this.procurementService.queryById(id);
    }

}