package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Attendance;
import com.tdkj.System.service.AttendanceService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;

/**
 * (Attendance)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:09
 */
@Slf4j
@Controller
@RequestMapping("attendance")
public class AttendanceController {
    /**
     * 服务对象
     */
    @Resource
    private AttendanceService attendanceService;
    @Autowired
    private EmployeeService employeeService;


    //上班打卡时间
    private final String data1 =DateUtil.getToday()+" 08:00:00";

    private final String data2 =DateUtil.getToday()+" 09:00:00";


    //下班打卡时间
    private final String data3 =DateUtil.getToday()+" 17:30:00";
    //String data4 =DateUtil.getToday()+" 18:00:00";

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Attendance selectOne(Integer id) {
        return this.attendanceService.queryById(id);
    }


    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add() throws Exception {
        //根据当前用户获取到部门ID
        Attendance oldattendance = attendanceService.queryByIdAndData(ShiroUtils.getPrincipal().getUserid(), DateUtil.getToday());
        if (null == oldattendance) {
            //如果今天没有数据 那么表明今天没有签到 可以签到
            int deptID = employeeService.queryByUserIdGetDeptID(ShiroUtils.getPrincipal().getEmployeeid());
            log.info(ShiroUtils.getPrincipal().getUserid().toString());
            Attendance attendance =new Attendance();
            attendance.setUserid(ShiroUtils.getPrincipal().getUserid());
            attendance.setDeptid(deptID);
            attendance.setWorkdate(new Date());
            attendance.setCreatedate(new Date());
            if (DateUtil.isBeforeAndAfter(data1,data2)){ //在此时间段内签到为正常签到
                attendanceService.insert(attendance);
                return OAResponse.setResult(HTTP_RNS_CODE_200, "签到成功");
            }else{
                //否则为迟到签到
                attendance.setAttendanceDesc("迟到");
                attendanceService.insert(attendance);
                return OAResponse.setResult(HTTP_RNS_CODE_200, "迟到签到成功");
            }
        }else if(DateUtil.isbefore(data3)){ /*DateUtil.isbefore(data3)&&null==oldattendance.getClosedate()*/
            //签退时 判断是否在设置好的下班点之后
            if(null==oldattendance.getClosedate()){ //如果下班时间为空那么证明还没有签退 可以签退
                oldattendance.setClosedate(new Date());
                oldattendance.setModifydate(new Date());
                attendanceService.update(oldattendance);
                return OAResponse.setResult(HTTP_RNS_CODE_200, "签退成功");
            }else{ //否则 提示请勿重复签退
                return OAResponse.setResult(HTTP_RNS_CODE_500, "请勿重复签退");
            }
        }else{
            return OAResponse.setResult(HTTP_RNS_CODE_500, "请勿重复签到");
        }
    }
}