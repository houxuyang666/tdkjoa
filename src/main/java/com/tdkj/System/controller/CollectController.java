package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Collect;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.CollectVO;
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
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

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
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //申请领用
     * @Date 10:47 2020/8/14
     * @Param [goodsname]
     **/
    @Transactional
    @ResponseBody
    @RequestMapping("/getgoods")
    public OAResponse getgoods(String warehouseid, Integer number) throws ParseException {
        log.info("/getgoods");
        //获取仓库中该产品的信息
        Warehouse warehouse = this.warehouseService.queryById(warehouseid);
        if (warehouse.getTotalnumbe() < number) {
            return OAResponse.setResult(HTTP_RNS_CODE_500, "库存不足");
        }
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Collect collect = new Collect();
        String code = RandomStringUtils.random(4, true, true);
        collect.setCollectid(DateUtil.getformatDate(new Date()) + code);
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
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseid(warehouseid);
        warehouse1.setTotalnumbe(warehouse.getTotalnumbe() - number);
        warehouse1.setModifydate(new Date());
        this.warehouseService.update(warehouse1);
        return OAResponse.setResult(HTTP_RNS_CODE_200, "领用成功");
    }


    /*跳转领用页面*/
    @RequestMapping("/goselectcollect")
    public String goselectcollect() {
        return "page/collect/collectlist";
    }


    /**
     * @return com.tdkj.System.common.OAResponseList
     * @Author houxuyang
     * @Description //TODO
     * @Date 13:44 2020/8/14
     * @Param [page, limit, name, goodsname]
     **/
    @ResponseBody
    @RequestMapping("/selectcollect")
    public OAResponseList selectcollect(Integer page, Integer limit, String name, String goodsname) {
        log.info("selectemployee");
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        //获取当前用户的所在公司
        CollectVO collectVO = new CollectVO();
        if (null != name) {
            collectVO.setName(name);
        }
        if (null != goodsname) {
            collectVO.setGoodsname(goodsname);
        }
        collectVO.setCorpid(employee.getCorpid());
        PageHelper.startPage(page, limit, true);
        //根据条件查询本公司的领用记录
        List<CollectVO> collectVOS = this.collectService.queryBycollectVO(collectVO);
        PageInfo<CollectVO> pageInfo = new PageInfo<>(collectVOS);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }

}