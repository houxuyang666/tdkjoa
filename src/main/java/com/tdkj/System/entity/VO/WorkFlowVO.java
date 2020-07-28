package com.tdkj.System.entity.VO;

import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 9:01
 */
@Data
public class WorkFlowVO {

    //批量删除使用
    private String[] ids;

    private Integer page;
    private Integer limit;

    /*流程部署名称*/
    private String deploymentName;
    /*流程部署ID*/
    private String deploymentid;
    /*请假单ID*/
    private Integer id;
    /*任务ID*/
    private String taskId;
    /*连接名称*/
    private String outcome;

}
