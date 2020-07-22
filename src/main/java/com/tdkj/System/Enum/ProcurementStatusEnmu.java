package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum ProcurementStatusEnmu {
    //采购状态

    To_audit(0,"待审核"),

    Review_completed(1,"审核通过"),

    rejected(2,"驳回"),

    Procurement_complete(3,"采购完成"),

    Acceptance_completed(4,"验收完成"),

    Acceptance_failure(5,"验收失败");


    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    ProcurementStatusEnmu(Integer code, String desc){
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
