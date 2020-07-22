package com.tdkj.System.common;

import lombok.Data;

/**
 * @Description RNS系统,统一响应格式
 * @ClassName OAResponse
 * @Author Chang
 * @date 2020.05.28 10:49
 */
@Data
public class OAResponse {

    private Integer ResponseCode;
    private String Message;
    private String Url;

    private Object Data;//数据 Data
    private Object Data2;//数据 Data2
    private Long total;

    public static OAResponse setResult(Integer ResponseCode, String message, Object data) {
        OAResponse OAResponse =new OAResponse();
        OAResponse.setResponseCode(ResponseCode);
        OAResponse.setMessage(message);
        OAResponse.setData(data);
        return OAResponse;
    }

    public static OAResponse setResult(Integer ResponseCode, String message) {
        OAResponse OAResponse =new OAResponse();
        OAResponse.setResponseCode(ResponseCode);
        OAResponse.setMessage(message);
        return OAResponse;
    }


    public static OAResponse setResult(Integer ResponseCode, String message, Object rows,Long total) {
        OAResponse OAResponse =new OAResponse();
        OAResponse.setResponseCode(ResponseCode);
        OAResponse.setMessage(message);
        OAResponse.setData(rows);
        OAResponse.setTotal(total);
        return OAResponse;
    }

    public static OAResponse setResult(Integer ResponseCode, String message,String url, Object Data) {
        OAResponse OAResponse =new OAResponse();
        OAResponse.setResponseCode(ResponseCode);
        OAResponse.setMessage(message);
        OAResponse.setData(Data);
        OAResponse.setUrl(url);
        return OAResponse;
    }

    public static OAResponse setResult(Integer ResponseCode, String message, Object Data,Object Data2) {
        OAResponse OAResponse =new OAResponse();
        OAResponse.setResponseCode(ResponseCode);
        OAResponse.setMessage(message);
        OAResponse.setData(Data);
        OAResponse.setData2(Data2);
        return OAResponse;
    }


}
