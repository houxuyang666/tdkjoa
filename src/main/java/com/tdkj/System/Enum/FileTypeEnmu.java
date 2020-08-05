package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum FileTypeEnmu {
    //附件类型

    Labor_contract(1,"劳动合同"),

    Procurement_contract(2,"采购合同"),

    Other_contracts(3,"公司合同"),

    Contract_template(4,"合同模板");


    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    FileTypeEnmu(Integer code, String desc){
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
