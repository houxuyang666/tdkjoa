package com.tdkj.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Leavebill)实体类
 *
 * @author makejava
 * @since 2020-08-14 15:50:22
 */
@Data
public class Leavebill implements Serializable {
    private static final long serialVersionUID = -74839630567516111L;

    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 天数
     */
    private Double days;
    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date leavetime;
    /**
     * 0 未提交 1审批中 2审批完成 3 已放弃
     */
    private Integer status;
    /**
     * 用户ID
     */
    private Integer userid;
    /**
     * 公司id
     */
    private Integer corpid;

    public Leavebill() {

    }

    public Leavebill(String title, String content, Double days, Date leavetime, Integer status, Integer userid,Integer corpid) {
        this.title = title;
        this.content = content;
        this.days = days;
        this.leavetime = leavetime;
        this.status = status;
        this.userid = userid;
        this.corpid = corpid;
    }

}