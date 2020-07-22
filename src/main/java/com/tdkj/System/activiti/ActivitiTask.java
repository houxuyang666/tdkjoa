package com.tdkj.System.activiti;

import lombok.Data;

import java.util.Date;

@Data
public class ActivitiTask {

    private String id;
    private String name;
    private Activiti activiti;
    private Date createTime;
}
