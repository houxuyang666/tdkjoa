package com.tdkj.System.utils;


import com.github.pagehelper.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/5/15 13:41
 */
public class IpUtils {

    /**
     * @Author houxuyang
     * @Description //获取当前的request
     * @Date 16:24 2020/5/26
     * @Param []
     * @return javax.servlet.http.HttpServletRequest
     **/
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
    }
        /**
         * @Author houxuyang
         * @Description //获取用ip
         * @Date 16:24 2020/5/26
         * @Param []
         * @return java.lang.String
         **/
        public static String getIp() {
            HttpServletRequest request = getRequest();
            if (request == null)
                return "";
            String ip = request.getHeader("X-Requested-For");
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            if("0:0:0:0:0:0:0:1".equals(ip)){
                return "127.0.0.1";
            }
            return ip;
        }


        public static boolean isLocalHost(String ip){
            return "127.0.0.1".equals(ip) || "localhost".equals(ip);
        }
}
