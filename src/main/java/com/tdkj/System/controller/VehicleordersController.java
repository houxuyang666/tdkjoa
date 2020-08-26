package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.VehicleOrdersStatusEnmu;
import com.tdkj.System.Enum.VehicleStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.EmployeeVO;
import com.tdkj.System.entity.VO.VehicleordersVO;
import com.tdkj.System.entity.Vehicleinfo;
import com.tdkj.System.entity.Vehicleorders;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.VehicleinfoService;
import com.tdkj.System.service.VehicleordersService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * (Vehicleorders)表控制层
 *
 * @author makejava
 * @since 2020-08-07 17:01:48
 */
@Slf4j
@Controller
@RequestMapping("vehicleorders")
public class VehicleordersController {
    /**
     * 服务对象
     */
    @Resource
    private VehicleordersService vehicleordersService;
    @Autowired
    private VehicleinfoService vehicleinfoService;
    @Autowired
    private EmployeeService employeeService;

    @Value("${file.uploadvehicleordersImage")
    private String uploadFolder;


    @RequestMapping("/goaddvehicleorders")
    public String goaddvehicleorders()  {
        log.info("goaddvehicleorders");
        return "page/vehicleorders/addvehicleorders";
    }




    @Transactional
    @ResponseBody
    @RequestMapping("/addvehicleorders")
    public OAResponse addvehicleorders(Integer vehicleid, Integer vehicledriverid, String beganaddress,
                                       String destinationaddress, String endaddress, String orderdesc) {
        Vehicleinfo vehicleinfo=vehicleinfoService.queryById(vehicleid);
        if (VehicleStatusEnmu.Has_been_used.getCode()==vehicleinfo.getStatus()){
            return OAResponse.setResult(HTTP_RNS_CODE_500,"该车辆正在使用，请选择未使用车辆");
        }
        //如果车辆被申请使用则修改该车辆的状态
        vehicleinfo.setStatus(VehicleStatusEnmu.Has_been_used.getCode());
        //订单申请时将车辆状态改为已申请
        vehicleinfoService.update(vehicleinfo);
        Vehicleorders vehicleorders =new Vehicleorders();
        //生成4为随机数 第二个参数为是否要字母 第三个参数是否要数字
        String code= RandomStringUtils.random(4, false, true);
        vehicleorders.setOrderid(DateUtil.getDateFormat(new Date(),"yyyyMMddHHmmss")+code);
        vehicleorders.setVehicleid(vehicleid);
        vehicleorders.setEmployeeid(ShiroUtils.getPrincipal().getEmployeeid());
        vehicleorders.setVehicledriverid(vehicledriverid);
        vehicleorders.setBeganaddress(beganaddress);
        vehicleorders.setDestinationaddress(destinationaddress);
        vehicleorders.setEndaddress(endaddress);
        //Has_applied_for(0,"已申请"), Ongoing(1,"进行中"), Has_ended(2,"已结束");
        //状态改为0 进行中
        vehicleorders.setStatus(VehicleOrdersStatusEnmu.Has_applied_for.getCode());
        vehicleorders.setOrderdesc(orderdesc);
        //开始时间应为点击开始用车时的时间
        /*try {
            Date BeganTime=DateUtil.formatDate(beginDate);
            vehicleorders.setBegandate(BeganTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        vehicleorders.setCreatedate(new Date());
        vehicleordersService.insert(vehicleorders);
        return OAResponse.setResult(HTTP_RNS_CODE_200,"订单已生成");
    }


    @RequestMapping("/govehicleorders")
    public String govehicleorders() {
        return "page/vehicleorders/vehicleorderslist";
    }

    /**
     * @Author houxuyang
     * @Description //查询用户订单
     * @Date 15:42 2020/8/10
     * @Param [page, limit]
     * @return OAResponseList
     **/
    @ResponseBody
    @RequestMapping("/selecvehicleorders")
    public OAResponseList selecvehicleorders(Integer page, Integer limit) {
            PageHelper.startPage(page,limit,true);
            /*根据员工ID查询所申请的车辆订单*/
            List<VehicleordersVO> vehicleordersVOList=vehicleordersService.selecvehicleorders(ShiroUtils.getPrincipal().getEmployeeid());
            PageInfo<VehicleordersVO> pageInfo=new PageInfo<>(vehicleordersVOList);
            return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }

    @RequestMapping("/goupdatevehicleorders")
    public String goupdatevehicleorders()  {
        log.info("goupdatevehicleorders");
        return "page/table/updatevehicleorders";
    }



    /**
     * @Author houxuyang
     * @Description //获取所有员工 当司机
     * @Date 15:49 2020/8/26
     * @Param []
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/getdrivers")
    public OAResponse getdrivers() {
        Employee employee = this.employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        //获取本公司所有的员工
        Integer roleid =3;//普通员工
        List<EmployeeVO> employeeVOList =this.employeeService.queryByCorpID(employee.getCorpid(),roleid);

        return OAResponse.setResult(HTTP_RNS_CODE_200,"订单已生成");
    }




/*
    @Transactional
    @ResponseBody
    @RequestMapping("/updatevehicleorders")
    public OAResponse updatevehicleorders(String orderId,Integer vehicleId, String mileage,String orderDesc,
                                           @RequestParam("mileageBegan") MultipartFile mileageBegan,
                                           @RequestParam("mileageEnd") MultipartFile mileageEnd) {

        try{
            if(mileageBegan.isEmpty()&&mileageEnd.isEmpty()){
                return OAResponse.setResult(HTTP_RNS_CODE_500,"请上传图片");
            }
            Vehicleorders vehicleorders = vehicleordersService.queryById(orderId);
            vehicleorders.setMileage(mileage);
            //创建文件夹
            File path = new File(uploadFolder);
            if(!path.exists()){
                path.mkdirs();
            }
            //转换成MultipartFile对象
            MultipartFile mileageBeganfiles = mileageBegan;
            MultipartFile mileageEndfiles = mileageEnd;
            //获取文件后缀名
            String mileageBegansuffix =ShiroUtils.getfilesuffix(mileageBeganfiles);
            String mileageEndsuffix = ShiroUtils.getfilesuffix(mileageEndfiles);
            mileageBeganfiles.transferTo(new File(path+"/"+orderId+"mileageBegan"+"."+mileageBegansuffix));
            log.info(path+"/"+orderId+"mileageBegan"+"."+mileageBegansuffix);
            mileageEndfiles.transferTo(new File(path+"/"+orderId+"mileageEnd"+"."+mileageEndsuffix));
            log.info(path+"/"+orderId+"mileageEnd"+"."+mileageEndsuffix);
            vehicleorders.setMileageBeganUrl(orderId+"mileageBegan"+"."+mileageBegansuffix);
            log.info(vehicleorders.getMileageBeganUrl());
            vehicleorders.setMileageEndUrl(orderId+"mileageEnd"+"."+mileageEndsuffix);
            log.info(vehicleorders.getMileageEndUrl());
            vehicleorders.setOrderDesc(orderDesc);
            vehicleorders.setEndTime(new Date());
            vehicleorders.setOrderStatus(2);
            vehicleordersService.update(vehicleorders);
            Vehicleinfo vehicleinfo =vehicleinfoService.queryById(vehicleId);
            vehicleinfo.setVehicleStatus(1);
            vehicleinfoService.update(vehicleinfo);
        }catch (NullPointerException e){
            return RnsResponse.setResult(HTTP_RNS_CODE_500,"订单不存在");
        }catch (IOException e) {
            return RnsResponse.setResult(HTTP_RNS_CODE_500,"图片上传失败");
        }

        return OAResponse.setResult(HTTP_RNS_CODE_200,"订单已完成");
    }*/

}