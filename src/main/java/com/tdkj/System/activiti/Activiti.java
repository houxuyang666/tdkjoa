package com.tdkj.System.activiti;

import lombok.Data;

import java.util.Date;

@Data
public class Activiti {



    /**
     * 申请人
     */
    private String applyUser;  //申请人
    private int days;         //申请天数
    private String reason;   //原因
    private Date applyTime;  //申请时间
    private String applyStatus; //申请状态

    /**
     * 审核人
     */
    private String auditor;     //审核人
    private String result;    //同意 或者不同意
    private Date auditTime;   //审核时间

}
