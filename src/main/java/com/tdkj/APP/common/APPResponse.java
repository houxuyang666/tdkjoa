package com.tdkj.APP.common;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author MrBird
 */
public class APPResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public APPResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public APPResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public APPResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public APPResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public APPResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public APPResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
