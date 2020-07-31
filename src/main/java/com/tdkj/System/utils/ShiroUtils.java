package com.tdkj.System.utils;

import com.tdkj.System.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @Author houxuyang
     * @Description //获取Spring IOC容器中的service
     * @Date 9:48 2020/7/31
     * @Param []
     * @return javax.servlet.http.HttpServletRequest
     **/
    public static HttpServletRequest getCurrentServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }


    /**
     * @Author houxuyang
     * @Description //使用正则表达式来判断字符串中是否包含字母
     * @Date 9:48 2020/7/31
     * @Param [cardNum]
     * @return boolean
     **/
    public static boolean judgeContainsStr(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }
}
