package com.tdkj.System.common;

import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/6 11:36
 */
@Data
public class OAResponseList {

    private Integer code;
    private String Message;
    private Object Data;//数据 Data
    private Object Data1;//数据 Data

    public static OAResponseList setResult(Integer code, String message, Object data) {
        OAResponseList OAResponseList =new OAResponseList();
        OAResponseList.setCode(code);
        OAResponseList.setMessage(message);
        OAResponseList.setData(data);
        return OAResponseList;
    }


    public static OAResponseList setResult(Integer code, String message, Object data,Object data1) {
        OAResponseList OAResponseList =new OAResponseList();
        OAResponseList.setCode(code);
        OAResponseList.setMessage(message);
        OAResponseList.setData(data);
        OAResponseList.setData1(data1);
        return OAResponseList;
    }


}
