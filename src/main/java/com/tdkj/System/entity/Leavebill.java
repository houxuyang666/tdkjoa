package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Leavebill)实体类
 *
 * @author makejava
 * @since 2020-07-23 21:08:47
 */
@Data
public class Leavebill implements Serializable {
    private static final long serialVersionUID = 845263274707335443L;

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
    private String days;
    /**
     * 申请时间
     */
    private Date leavetime;
    /**
     * 0 未提交 1审批中 2审批完成 3 已放弃
     */
    private String state;

    private Integer userid;

    public Leavebill() {

    }

    public Leavebill(String title, String content, String days, Date leavetime, String state, Integer userid) {
        this.title = title;
        this.content = content;
        this.days = days;
        this.leavetime = leavetime;
        this.state = state;
        this.userid = userid;
    }


}