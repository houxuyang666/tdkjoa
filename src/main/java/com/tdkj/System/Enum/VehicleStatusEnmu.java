package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum VehicleStatusEnmu {
    //车辆状态

    To_be_used(0,"待使用"),

    Has_been_used(1,"已使用");


    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    VehicleStatusEnmu(Integer code, String desc){
        this.desc=desc;
        this.code=code;
    }
    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getDesc(){
        return desc;
    }

    /**
     * 定义方法,返回代码,跟常规类的定义没区别
     * @return
     */
    public int getCode(){
        return code;
    }


}
