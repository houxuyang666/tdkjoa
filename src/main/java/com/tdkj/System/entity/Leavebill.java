package com.tdkj.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Integer userid;

    public Leavebill() {

    }

    public Leavebill(String title, String content, Double days, Date leavetime, Integer status, Integer userid) {
        this.title = title;
        this.content = content;
        this.days = days;
        this.leavetime = leavetime;
        this.status = status;
        this.userid = userid;
    }
}
