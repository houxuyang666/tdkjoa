package com.tdkj.System.controller;

import com.tdkj.System.Enum.CheckingStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Attendancesynthesis;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.service.AttendancesynthesisService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultType.ADD_SUCCESS;

/**
 * (Attendancesynthesis)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:06
 */
@RestController
@RequestMapping("attendancesynthesis")
public class AttendancesynthesisController {
    /**
     * 服务对象
     */
    @Resource
    private AttendancesynthesisService attendancesynthesisService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("goadd")
    public String goadd() {
        return "page/table/addattendancesynthesis";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String address, String applydate, String begandate, String enddate, Integer type, String desc) throws Exception {
        //获取用户的部门ID供下面使用
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Attendancesynthesis attendancesynthesis = new Attendancesynthesis();
        attendancesynthesis.setUserid(ShiroUtils.getPrincipal().getUserid());
        attendancesynthesis.setDeptid(employee.getDepartmentid());
        attendancesynthesis.setAddress(address);
        attendancesynthesis.setApplydate(DateUtil.getformatDate(applydate));
        attendancesynthesis.setBegandate(DateUtil.getformatDate(begandate));
        attendancesynthesis.setEnddate(DateUtil.getformatDate(enddate));
        attendancesynthesis.setType(type);
        attendancesynthesis.setDesc(desc);
        attendancesynthesis.setStatus(CheckingStatusEnmu.To_audit.getCode());

        return OAResponse.setResult(HTTP_RNS_CODE_200, ADD_SUCCESS);
    }

}