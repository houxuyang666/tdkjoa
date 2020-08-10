package com.tdkj.System.controller;

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


    /**
     * @Author houxuyang
     * @Description //重置某个用户密码
     * @Date 15:50 2020/5/26
     * @Param [id, oldpassword, newpassword]
     * @return java.lang.String
     **/
    @ResponseBody
    @RequestMapping("/setpsd")
    public OAResponse setpsd(Integer employeeid) {
        log.info("重置密码");
        Integer rid =1;
        if(rid.equals(ShiroUtils.getPrincipal().getRoleid())){
            User user =userService.queryByemployeeid(employeeid);
            user.setPassword(Md5Util.Md5Password(user.getSalt(),"123456"));
            user.setModifydate(new Date());
            userService.update(user);
            log.info("密码修改成功");
            return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_SUCCESS);
        }
        return OAResponse.setResult(HTTP_RNS_CODE_500,UPDATE_FAULT+":只有超级管理员可以重置用户密码");
    }

}