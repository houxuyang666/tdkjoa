package com.tdkj.System.controller;

import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.service.FileinfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Fileinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@RestController
@RequestMapping("fileinfo")
public class FileinfoController {
    /**
     * 服务对象
     */
    @Resource
    private FileinfoService fileinfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Fileinfo selectOne(Integer id) {
        return this.fileinfoService.queryById(id);
    }

}