package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum ProcurementTypeEnmu {
    //采购类型

    Furniture(0,"家具用品"),

    Special_equipment(1,"专用设备"),

    Electrical_equipment(2,"电器设备"),

    Electronic_products_and_communication_equipment(3,"电子产品及通讯设备"),

    Instrument_and_standards(4,"仪器仪表、计量标准器"),

    Transportation_equipment(5,"交通运输设备"),

    Other(6,"其他");

    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    ProcurementTypeEnmu(Integer code, String desc){
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
