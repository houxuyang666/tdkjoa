package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
import com.tdkj.System.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;

/**
 * (Collect)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@Slf4j
@Controller
@RequestMapping("collect")
public class CollectController {
    /**
     * 服务对象
     */
    @Resource
    private CollectService collectService;

    /*跳转领用页面*/
    @RequestMapping("/gogetgoods")
    public String gogetgoods() {
        return "page/warehouse/getgoods";
    }


    /**
     * @Author houxuyang
     * @Description //申请领用
     * @Date 10:47 2020/8/14
     * @Param [goodsname]
     * @return com.tdkj.System.common.OAResponseList
     **/
    @ResponseBody
    @RequestMapping("/getgoods")
    public OAResponse getgoods(String warehouseid, Integer number) {
        log.info("/getgoods");
        /*Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Warehouse warehouse =new Warehouse();

        List<Warehouse> warehouseList= warehouseService.queryAll(warehouse);
        PageInfo<Warehouse> pageInfo=new PageInfo<>(warehouseList);*/
        return OAResponse.setResult(HTTP_RNS_CODE_200,"领用成功");
    }
}