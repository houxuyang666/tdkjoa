package com.tdkj.System.exception;

/**
 * @Description 系统异常
 * @ClassName RNSException
 * @Author Chang
 * @date 2020.06.16 16:06
 */
public class OAException extends RuntimeException {

    public OAException(String message) {
        super(message);
    }
}
