package com.tdkj.System.entity.ect;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/27 10:24
 */
@Data
public class ActTaskEntity {

    private String id;
    private String name;
    private String assigness;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
