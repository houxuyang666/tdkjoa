package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/27 10:04
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
