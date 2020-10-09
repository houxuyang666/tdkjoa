package com.tdkj.APP.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.APP.common.APPResponse;
import com.tdkj.APP.entity.AppUser;
import com.tdkj.System.Enum.VehicleOrdersStatusEnmu;
import com.tdkj.System.Enum.VehicleStatusEnmu;
import com.tdkj.System.entity.VO.VehicleinfoVO;
import com.tdkj.System.entity.Vehicleinfo;
import com.tdkj.System.entity.Vehicleorders;
import com.tdkj.System.service.VehicleinfoService;
import com.tdkj.System.service.VehicleordersService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.tdkj.System.common.OAResultType.LOGINOUT;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/10/9 11:56
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class AppVehicleinfo {

    @Resource
    private VehicleinfoService vehicleinfoService;
    @Resource
    private VehicleordersService vehicleordersService;


    private final RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/app/getvehicleinfolist")
    public APPResponse vehicleinfolist(HttpServletRequest request, @RequestBody Map<String, String> map) {
        String token = request.getHeader("token");
        if (!redisUtil.hasKey(token)) {
            return new APPResponse().fail().message(LOGINOUT);
        }
        Vehicleinfo vehicleinfo = new Vehicleinfo();
        if (null != map.get("vehiclenumber")) {
            vehicleinfo.setVehiclenumber(String.valueOf(map.get("vehiclenumber")));
        }
        if (null != map.get("vehicletype")) {
            vehicleinfo.setVehicletype(String.valueOf(map.get("vehicletype")));
        }
        if (null != map.get("corpid")) {
            vehicleinfo.setVehicleaffiliationcorpbasicinfo(Integer.valueOf(map.get("corpid")));
        }
        PageHelper.startPage(Integer.valueOf(map.get("page")), Integer.valueOf(map.get("limit")), true);
        List<VehicleinfoVO> vehicleinfoVOList = vehicleinfoService.queryAllvehicleinfo(vehicleinfo);
        PageInfo<VehicleinfoVO> pageInfo = new PageInfo<>(vehicleinfoVOList);
        return new APPResponse().success().data(pageInfo);
    }


    @Transactional
    @ResponseBody
    @RequestMapping("/app/addvehicleorders")
    public APPResponse addvehicleorders(HttpServletRequest request, @RequestBody Map<String, String> map) {
        String token = request.getHeader("token");
        if (!redisUtil.hasKey(token)) {
            return new APPResponse().fail().message(LOGINOUT);
        }
        /*将json取出来*/
        AppUser appUser = JSONObject.toJavaObject(JSONObject.parseObject(redisUtil.get(token).toString()), AppUser.class);
        Integer vehicleid =Integer.valueOf(map.get("vehicleid"));

        Vehicleinfo vehicleinfo = vehicleinfoService.queryById(vehicleid);

        if (VehicleStatusEnmu.Has_been_used.getCode() == vehicleinfo.getStatus()) {
            return new APPResponse().fail().message("该车辆正在使用，请选择未使用车辆");
        }
        //如果车辆被申请使用则修改该车辆的状态
        vehicleinfo.setStatus(VehicleStatusEnmu.Has_been_used.getCode());
        //订单申请时将车辆状态改为已申请
        vehicleinfoService.update(vehicleinfo);

        Vehicleorders vehicleorders = new Vehicleorders();
        //生成4为随机数 第二个参数为是否要字母 第三个参数是否要数字
        String code = RandomStringUtils.random(4, false, true);
        vehicleorders.setOrderid(DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + code);
        vehicleorders.setVehicleid(vehicleid);
        vehicleorders.setEmployeeid(appUser.getUserid());
        if (null != map.get("beganaddress")) {
            vehicleorders.setBeganaddress(String.valueOf(map.get("beganaddress")));
        }
        if (null != map.get("destinationaddress")) {
            vehicleorders.setDestinationaddress(String.valueOf(map.get("destinationaddress")));
        }
        if (null != map.get("orderdesc")) {
            vehicleorders.setOrderdesc(String.valueOf(map.get("orderdesc")));
        }
        //Has_applied_for(0,"已申请"), Ongoing(1,"进行中"), Has_ended(2,"已结束");
        //状态改为0 进行中
        vehicleorders.setStatus(VehicleOrdersStatusEnmu.Has_applied_for.getCode());
        vehicleorders.setCreatedate(new Date());
        vehicleordersService.insert(vehicleorders);
        return new APPResponse().success().message("订单已生成");
    }
}
