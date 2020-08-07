package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.VehicleinfoVO;
import com.tdkj.System.entity.Vehicleinfo;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.VehicleinfoService;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;
import static com.tdkj.System.common.OAResultType.REMOVE_SUCCESS;

/**
 * (Vehicleinfo)表控制层
 *
 * @author makejava
 * @since 2020-08-07 17:01:44
 */
@Controller
@RequestMapping("vehicleinfo")
public class VehicleinfoController {
    /**
     * 服务对象
     */
    @Resource
    private VehicleinfoService vehicleinfoService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("/goselectvehicleinfo")
    public String goselectvehicleinfo() {
        return "page/vehicleinfo/vehicleinfolist";
    }

    /**
     * 查询多条数据
     *
     * @param
     * @return 多条数据
     */
    @ResponseBody
    @RequestMapping("/selectvehicleinfo")
    public OAResponseList vehicleinfolist(Integer page, Integer limit, String vehiclenumber,String vehicletype,Integer corpid) {
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Vehicleinfo vehicleinfo = new Vehicleinfo();
        if (null!=vehiclenumber){
            vehicleinfo.setVehiclenumber(vehiclenumber);
        }
        if (null!=corpid){
            vehicleinfo.setVehicleaffiliationcorpbasicinfo(corpid);
        }
        if (null!=vehicletype){
            vehicleinfo.setVehicletype(vehicletype);
        }
        PageHelper.startPage(page,limit,true);
        List<VehicleinfoVO> vehicleinfoVOList=vehicleinfoService.queryAllvehicleinfo(vehicleinfo);
        PageInfo<VehicleinfoVO> pageInfo=new PageInfo<>(vehicleinfoVOList);

        /*查询所有司机*/
        //List<UserinfoVO> userinfoVOList=userinfoService.Alldriver();

        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
        //return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo,userinfoVOList);
    }
    /**
     * @Author houxuyang
     *
     * @Description //新增车辆信息
     * @Date 17:28 2020/7/1
     * @Param [vehicleType, vehicleSeatsNumber, vehicleNumber, vehicleAffiliationCompany, vehicleAffiliationPersonal]
     * @return com.tdkj.RNS.common.RnsResponse
     **/
  /*  @ResponseBody
    @RequestMapping("/addvehicleinfo")
    public OAResponseList addcompany(String vehicleType,Integer vehicleSeatsNumber,String vehicleNumber,Integer vehicleAffiliationCompany,Integer vehicleAffiliationPersonal) {
        Vehicleinfo vehicleinfo1 =vehicleinfoService.queryByvehicleNumber(vehicleNumber);
        if (vehicleinfo1 != null) {
            return OAResponseList.setResult(HTTP_RNS_CODE_500,"车辆已存在");
        }
        Vehicleinfo vehicleinfo=new Vehicleinfo();
        vehicleinfo.setVehicleType(vehicleType);
        vehicleinfo.setVehicleSeatsNumber(vehicleSeatsNumber);
        vehicleinfo.setVehicleNumber(vehicleNumber);
        vehicleinfo.setVehicleStatus(1); //刚注册都为未用
        vehicleinfo.setVehicleAffiliationCompany(vehicleAffiliationCompany);
        vehicleinfo.setVehicleAffiliationPersonal(vehicleAffiliationPersonal);
        vehicleinfo.setCreateTime(new Date());
        vehicleinfoService.insert(vehicleinfo);
        return OAResponseList.setResult(HTTP_RNS_CODE_200,ADD_SUCCESS);
    }*/

    /**
     * @Author houxuyang
     * @Description //根据id修改车辆信息
     * @Date 17:28 2020/7/1
     * @Param [vehicleinfoId, vehicleType, vehicleSeatsNumber, vehicleNumber, vehicleAffiliationCompany, vehicleAffiliationPersonal]
     * @return com.tdkj.RNS.common.RnsResponse
     **/
    /*@ResponseBody
    @RequestMapping("/updatevehicleinfo")
    public OAResponse updatevehicleinfo(Integer vehicleinfoId, String vehicleType, Integer vehicleSeatsNumber, String vehicleNumber, Integer vehicleAffiliationCompany, Integer vehicleAffiliationPersonal) {
        Vehicleinfo vehicleinfo =vehicleinfoService.queryById(vehicleinfoId);
//        if (vehicleinfo != null) {
//            return RnsResponse.setResult(HTTP_RNS_CODE_500,"车辆已存在");
//        }else
        System.out.println(vehicleinfo.getVehicleStatus()+"---1111111111111111111111111111111111");
        if(0==vehicleinfo.getVehicleStatus()){
            return OAResponse.setResult(HTTP_RNS_CODE_500,"车辆正在使用");
        }
        vehicleinfo.setVehicleType(vehicleType);
        vehicleinfo.setVehicleSeatsNumber(vehicleSeatsNumber);
        vehicleinfo.setVehicleNumber(vehicleNumber);
        vehicleinfo.setVehicleStatus(1); //刚注册都为未用
        vehicleinfo.setVehicleAffiliationCompany(vehicleAffiliationCompany);
        vehicleinfo.setVehicleAffiliationPersonal(vehicleAffiliationPersonal);
        vehicleinfo.setVehicleNumber(vehicleNumber);
        vehicleinfo.setModifyTime(new Date());
        vehicleinfoService.update(vehicleinfo);

        return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_SUCCESS);
    }*/
    /**
     * @Author houxuyang
     * @Description //删除车辆
     * @Date 17:33 2020/7/1
     * @Param [vehicleinfoId]
     * @return com.tdkj.RNS.common.RnsResponse
     **/
    @ResponseBody
    @RequestMapping("/delvehicleinfo")
    public OAResponse delvehicleinfo(Integer vehicleinfoId) {
        vehicleinfoService.deleteById(vehicleinfoId);
        return OAResponse.setResult(HTTP_RNS_CODE_200,REMOVE_SUCCESS);
    }
}