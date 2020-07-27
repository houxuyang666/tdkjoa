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

    private String deploymentName;
    private String deploymentid;
    private Integer id;
    private String taskId;

}
