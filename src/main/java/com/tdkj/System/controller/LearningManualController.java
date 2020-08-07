package com.tdkj.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/7 16:34
 */

@Controller
@RequestMapping("learningmanual")
public class LearningManualController {


    /*跳转公司文化页面*/
    @RequestMapping("/goculture")
    public String goculture() {
        return "page/learningmanual/culture";
    }


    /*跳转公司制度页面*/
    @RequestMapping("/gosystem")
    public String gosystem() {
        return "page/learningmanual/system";
    }

    /*跳转公司架构页面*/
    @RequestMapping("/goframework")
    public String goframework() {
        return "page/learningmanual/framework";
    }


}
