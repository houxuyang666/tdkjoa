package com.tdkj.System.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/5/25 17:06
 */
public class Md5Util {

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 5;

    /**
     * @Author houxuyang
     * @Description //用于密码加密
     * @Date 14:30 2020/5/27
     * @Param [username, password]
     * @return java.lang.String
     **/
    public static String Md5Password(String username, String password) {
        String source = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(source), HASH_ITERATIONS).toHex();
    }
}
