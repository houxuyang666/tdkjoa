package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum EducationLevelEnmu {
    //学历
    postdoctoral(1,"博士后"),

    Dr(2,"博士"),

    graduate_student(3,"研究生"),

    master_degree(4,"硕士"),

    Undergraduate_course(5,"本科"),

    college(6,"大专"),

    Technical_secondary_school(7,"中专"),

    High_school(8,"高中"),

    other(9,"其他");

    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    EducationLevelEnmu(Integer code, String desc){
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
