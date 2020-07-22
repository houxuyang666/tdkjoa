package com.tdkj.System.controller;

import com.tdkj.System.entity.Log;
import com.tdkj.System.service.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志表(Log)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@RestController
@RequestMapping("log")
public class LogController {
    /**
     * 服务对象
     */
    @Resource
    private LogService logService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Log selectOne(Integer id) {
        return this.logService.queryById(id);
    }

}