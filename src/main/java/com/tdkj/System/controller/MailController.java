package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.VO.EmployeeVO;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

@Controller
@RequestMapping("mail")
public class MailController {
@Autowired
private EmployeeService employeeService;


    //跳转到公司通讯页面
    @RequestMapping("gomymaillist")
    public String gomymaillist() {
        return "page/mymaillist";
    }


    //查询公司通讯

    @RequestMapping("/selectmymail")
    @ResponseBody
    public OAResponseList selectmymail(Integer page, Integer limit,String name,String cellphone) {
        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());

        PageHelper.startPage(page,limit,true);
        List<EmployeeVO> employeeVOList=employeeService.queryMyMail(employee.getCorpid(),name,cellphone);
        PageInfo<EmployeeVO> pageInfo=new PageInfo<>(employeeVOList);
        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);

    }

//    //跳转到员工通讯页面
//    @RequestMapping("goallmaillist")
//    public String goallmaillist() {
//        return "page/allmaillist";
//    }
//
//
//    //查询员工通讯录
//    @RequestMapping("/selectallmail")
//    @ResponseBody
//    public OAResponseList selectallmail(Integer page, Integer limit, String name, String cellphone) {
//        Employee employee = employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
//
//        PageHelper.startPage(page,limit,true);
//        List<EmployeeVO> employeeVOList=employeeService.queryAllMail(employee.getCorpid(),name,cellphone);
//        PageInfo<EmployeeVO> pageInfo=new PageInfo<>(employeeVOList);
//        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
//    }

}