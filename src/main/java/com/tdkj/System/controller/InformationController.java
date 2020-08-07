package com.tdkj.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/7 16:34
 */
@Controller
@RequestMapping("information")
public class InformationController {



    /*跳转通知公告页面*/
    @RequestMapping("/goinformation")
    public String goinformation() {
        return "page/information/informationlist";
    }



    /*跳转信息发布页面*/
    @RequestMapping("/goadd")
    public String goadd() {
        return "page/information/addinformation";
    }



}