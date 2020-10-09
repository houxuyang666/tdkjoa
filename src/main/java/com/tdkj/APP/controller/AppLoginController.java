package com.tdkj.APP.controller;

import com.alibaba.fastjson.JSONObject;
import com.tdkj.APP.common.APPResponse;
import com.tdkj.APP.entity.AppUser;
import com.tdkj.System.entity.User;
import com.tdkj.System.service.UserService;
import com.tdkj.System.utils.Md5Util;
import com.tdkj.System.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.UUID.randomUUID;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/9/25 10:27
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class AppLoginController  {

    @Autowired
    private UserService userService;

    private final RedisUtil redisUtil;

    /**
     * @Author hxy
     * @Description //app登陆接口
     * @Date 11:48 2020/10/9
     * @Param [user]
     * @return com.tdkj.System.common.OAResponse
     **/
    @PostMapping(value="/app/login" ,produces = "application/json")
    public APPResponse loginapp(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            User newuser = userService.findByName(user.getUsername());
            if (0 == newuser.getStatus()) {
                return new APPResponse().fail().message("账户已被冻结，请联系管理员");
            }
            String password = Md5Util.Md5Password(newuser.getSalt(), user.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), password);
            subject.login(token);

            String apptoken =randomUUID().toString()+System.currentTimeMillis();
            AppUser appUser =new AppUser();
            /*将第一个对象的值给到第二个对象，前提是得有相同的属性去接收*/
            BeanUtils.copyProperties(newuser, appUser);
            appUser.setToken(apptoken);
            /*Key为生成的KEY，键为user对象*/
            redisUtil.set(apptoken, JSONObject.toJSON(appUser),86400L);
            return new APPResponse().success().data(appUser);
        } catch (UnknownAccountException e) {
            return new APPResponse().code(HttpStatus.UNAUTHORIZED).message("用户名或密码错误！");
        } catch (IncorrectCredentialsException e) {
            return new APPResponse().code(HttpStatus.UNAUTHORIZED).message("用户名或密码错误！");
        } catch (NullPointerException e) {
            return new APPResponse().code(HttpStatus.UNAUTHORIZED).message("用户名或密码错误！");
        }
    }
}
