package com.tdkj.System.controller;

import com.tdkj.System.entity.Collect;
import com.tdkj.System.service.CollectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Collect)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@RestController
@RequestMapping("collect")
public class CollectController {
    /**
     * 服务对象
     */
    @Resource
    private CollectService collectService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Collect selectOne(Integer id) {
        return this.collectService.queryById(id);
    }

}