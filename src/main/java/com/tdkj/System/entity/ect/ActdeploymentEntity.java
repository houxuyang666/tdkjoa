package com.tdkj.System.entity.ect;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/24 9:40
 */
@Data
public class ActdeploymentEntity {


    private String Id;

    private String Name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date DeploymentTime;

    private String Category;
}
