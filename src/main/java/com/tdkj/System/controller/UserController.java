package com.tdkj.System.controller;

import com.tdkj.System.Enum.UserStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.User;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.UserService;
import com.tdkj.System.utils.Md5Util;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.UPDATE_FAULT;
import static com.tdkj.System.common.OAResultType.UPDATE_SUCCESS;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;


    private static final Integer roleid =1;


    /**
     * @Author houxuyang
     * @Description //重置某个用户密码
     * @Date 11:17 2020/8/10
     * @Param [employeeid]
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/setpsd")
    public OAResponse setpsd(Integer employeeid) {
        log.info("重置密码");
        if(roleid.equals(ShiroUtils.getPrincipal().getRoleid())){
            User user =userService.queryByemployeeid(employeeid);
            user.setPassword(Md5Util.Md5Password(user.getSalt(),"123456"));
            user.setModifydate(new Date());
            userService.update(user);
            log.info("重置密码成功");
            return OAResponse.setResult(HTTP_RNS_CODE_200,"重置密码成功");
        }
        return OAResponse.setResult(HTTP_RNS_CODE_500,UPDATE_FAULT+":只有超级管理员可以重置用户密码");
    }


    /**
     * @Author houxuyang
     * @Description //修改账号状态
     * @Date 11:17 2020/8/10
     * @Param [id, status]
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/setuserstatus")
    public OAResponse setuserstatus(Integer employeeid) {
        log.info("修改账号状态");
        User user =userService.queryByemployeeid(employeeid);
        if(roleid.equals(ShiroUtils.getPrincipal().getRoleid())){
            //0,"注销" 1,"正常");
            if (UserStatusEnmu.Normal.getCode()==user.getStatus()){
                user.setStatus(0);
                user.setModifydate(new Date());
                userService.update(user);
                return OAResponse.setResult(HTTP_RNS_CODE_200,"注销成功");
            }else if (UserStatusEnmu.Cancellation.getCode()==user.getStatus()){
                user.setStatus(1);
                user.setModifydate(new Date());
                userService.update(user);
                return OAResponse.setResult(HTTP_RNS_CODE_200,"启用成功");
            }else{
                return OAResponse.setResult(HTTP_RNS_CODE_200,"未知错误");
            }

        }
        return OAResponse.setResult(HTTP_RNS_CODE_500,UPDATE_FAULT+":只有超级管理员可以操作");
    }

    /**
     * @Author houxuyang
     * @Description //跳转修改密码页面
     * @Date 14:25 2020/8/10
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/goupdatepsd")
    public String goupdatepsd() {
        return "page/employee/updatepsd";
    }


    @ResponseBody
    @RequestMapping("/updatepsd")
    public OAResponse updatepsd(String oldpassword,String newpassword) {
        log.info("修改密码");
        //根据用户id查询出来用户信息
        User user =userService.queryById(ShiroUtils.getPrincipal().getUserid());
        //将输入的原密码进行加密后 与数据库密码进行对比
        String dbpassword = Md5Util.Md5Password(user.getSalt(), oldpassword);
        if (!dbpassword.equals(user.getPassword())){
            return OAResponse.setResult(HTTP_RNS_CODE_500,"原密码错误");
        }
        //密码正确后进入  将新密码进行加密
        newpassword=Md5Util.Md5Password(user.getSalt(), newpassword);
        user.setPassword(newpassword);
        user.setModifydate(new Date());
        userService.update(user);
        log.info("密码修改成功");
        return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_SUCCESS);
    }




}