package com.tdkj.System.entity.ect;

import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 10:01
 */
@Data
public class ActProcessDefinitionEntity {
    /*流程定义转换类*/
    private String id;
    private String name;
    private String key;
    private Integer version;
    private String deploymentId;
    private String resourceName;
    private String diagramResourceName;



}
