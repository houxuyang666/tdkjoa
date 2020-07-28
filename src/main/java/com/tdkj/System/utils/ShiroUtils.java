package com.tdkj.System.utils;

import com.tdkj.System.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/5/26 17:11
 */
public class ShiroUtils {

    /**
     * @Author houxuyang
     * @Description //获取当前用户的session
     * @Date 16:48 2020/5/26
     * @Param []
     * @return User
     **/
    public static User getPrincipal(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 得到request
     */
    public static HttpServletRequest getCurrentServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }
}
