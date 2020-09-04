package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Warehouse;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.WarehouseService;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * (Warehouse)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
@Slf4j
@Controller
@RequestMapping("warehouse")
public class WarehouseController {
    /**
     * 服务对象
     */
    @Resource
    private WarehouseService warehouseService;
    @Autowired
    private EmployeeService employeeService;

    /*跳转仓库页面*/
    @RequestMapping("/goselectwarehouse")
    public String goselectwarehouse() {
        return "page/warehouse/warehouselist";
    }


    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //查询库存
     * @Date 10:18 2020/8/14
     * @Param [page, limit, goodsname]
     **/
    @ResponseBody
    @RequestMapping("/selectwarehouse")
    public OAResponseList selectwarehouse(Integer page, Integer limit, String goodsname) {
        log.info("/selectwarehouse");
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Warehouse warehouse = new Warehouse();
        if (null != goodsname) {
            warehouse.setGoodsname(goodsname);
        }
        warehouse.setCorpid(employee.getCorpid());
        PageHelper.startPage(page, limit, true);
        List<Warehouse> warehouseList = warehouseService.queryAll(warehouse);
        PageInfo<Warehouse> pageInfo = new PageInfo<>(warehouseList);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }


}