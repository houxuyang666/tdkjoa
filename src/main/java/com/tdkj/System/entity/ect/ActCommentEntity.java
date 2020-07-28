package com.tdkj.System.entity.ect;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/28 11:03
 */
@Data
public class ActCommentEntity {

    private String  userId;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private String  message;
    private String  fullMeassage;

}
