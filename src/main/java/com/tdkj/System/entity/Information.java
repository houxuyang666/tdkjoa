package com.tdkj.System.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Information)实体类
 *
 * @author makejava
 * @since 2020-08-11 09:53:40
 */
@Data
public class Information implements Serializable {
    private static final long serialVersionUID = -23019221290519723L;
    
    private Integer infoid;
    /**
    * 标题
    */
    private String infotitle;
    /**
    * 通知图片
    */
    private String infoimg;
    /**
    * 内容
    */
    private String infocontent;
    /**
    * 通知时间
    */
    private Date infodate;
    /**
    * 公司id
    */
    private Integer corpid;
    /**
    * 创建时间
    */
    private Date createdate;
    /**
    * 修改时间
    */
    private Date modifydate;

}