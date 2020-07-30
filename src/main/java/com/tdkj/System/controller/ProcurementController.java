package com.tdkj.System.controller;

import com.tdkj.System.service.ProcurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * (Procurement)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:50:58
 */
@Slf4j
@Controller
@RequestMapping("procurement")
public class ProcurementController {
    /**
     * 服务对象
     */
    @Resource
    private ProcurementService procurementService;




    @RequestMapping("/goselectpro")
    public String goselectpro() {
        return "page/prolist";
    }

}