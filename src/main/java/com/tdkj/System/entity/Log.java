package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志表(Log)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = -46812672206541465L;
    /**
     * 主键
     */
    private Integer logid;
    /**
     * 当前用户
     */
    private String operateor;
    /**
     * 操作
     */
    private String operatetype;
    /**
     * 时间
     */
    private Date operatedate;
    /**
     * 状态
     */
    private String operateresult;
    /**
     * ip
     */
    private String ip;


}