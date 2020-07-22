package com.tdkj.System.controller;

import com.tdkj.System.service.CollectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * (Collect)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@Controller
@RequestMapping("system")
public class SystemController {
    /**
     * 服务对象
     */
    @Resource
    private CollectService collectService;


    @RequestMapping("/goicon")
    public  String  goicon(){
        return "page/icon";
    }

    @RequestMapping("/gosetting")
    public  String  gosetting(){
        return "page/setting";
    }

}