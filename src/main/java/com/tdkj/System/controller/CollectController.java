package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Collect;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Warehouse;
import com.tdkj.System.service.CollectService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.WarehouseService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Date;

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
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private EmployeeService employeeService;

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
    @Transactional
    @ResponseBody
    @RequestMapping("/getgoods")
    public OAResponse getgoods(String warehouseid, Integer number)throws ParseException {
        log.info("/getgoods");
        //获取仓库中该产品的信息
        Warehouse warehouse = this.warehouseService.queryById(warehouseid);
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Collect collect =new Collect();
        String code= RandomStringUtils.random(4, true, true);
        collect.setCollectid(DateUtil.getformatDate(new Date())+code);
        collect.setCorpid(employee.getCorpid());
        collect.setDeptid(employee.getDepartmentid());
        collect.setEmployeeid(employee.getEmployeeid());
        collect.setGoodsname(warehouse.getGoodsname());
        collect.setUnit(warehouse.getUnit());
        collect.setType(warehouse.getType());
        collect.setNumber(number);
        collect.setCreatedate(new Date());
        //插入领用表
        this.collectService.insert(collect);
        ////减少仓库库存数量
        Warehouse warehouse1 =new Warehouse();
        warehouse1.setWarehouseid(warehouseid);
        warehouse1.setTotalnumbe(warehouse.getTotalnumbe()-number);
        warehouse1.setModifydate(new Date());
        this.warehouseService.update(warehouse1);
        return OAResponse.setResult(HTTP_RNS_CODE_200,"领用成功");
    }
}