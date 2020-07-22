package com.tdkj.System.Enum;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/15 16:41
 */
public enum CheckingTypeEnmu {
    //考勤类型

    Work_overtime(0,"加班"),

    Expatriate(1,"外派"),

    Business_travel(2,"出差"),

    Compassionate_leave(3,"事假"),

    Sick_leave(4,"病假"),

    Marriage_holiday(5,"婚假"),

    Maternity_leave(6,"产假"),

    Bereavement_leave(7,"丧假"),

    Annual_leave(8,"年假"),

    Compensatory_leave(9,"调休"),

    Paternity_leave(10,"陪产假"),

    Other(11,"其他");

    private String desc;//文字描述

    private Integer code; //对应的代码

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    CheckingTypeEnmu(Integer code, String desc){
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
