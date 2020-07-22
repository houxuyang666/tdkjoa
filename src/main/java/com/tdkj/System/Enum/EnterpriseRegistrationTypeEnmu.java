package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum EnterpriseRegistrationTypeEnmu {
    //企业注册类型

    State_owned_enterprise(0,"国有企业"),

    Collective_enterprises(1,"集体企业"),

    Cooperative_stock_enterprise(2,"股份合作企业"),

    Joint_venture(3,"联营企业"),

    Company_with_limited_liability(4,"有限责任公司"),

    Limited_company(5,"股份有限公司"),

    Private_enterprise(6,"私营企业"),

    Other_enterprises(7,"其他企业");

    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    EnterpriseRegistrationTypeEnmu(Integer code, String desc){
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
