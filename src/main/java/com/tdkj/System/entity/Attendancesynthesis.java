package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Attendancesynthesis)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Data
public class Attendancesynthesis implements Serializable {
    private static final long serialVersionUID = -65987683958651118L;
    /**
     * 考勤综合ID 自增ID
     */
    private Integer attensynid;
    /**
     * 员工ID
     */
    private Integer userid;
    /**
     * 部门ID
     */
    private Integer deptid;
    /**
     * 申请日期
     */
    private Date applydate;
    /**
     * 地点 外出/出差填写即可
     */
    private String address;
    /**
     * 开始时间
     */
    private Date begandate;
    /**
     * 结束时间
     */
    private Date enddate;
    /**
     * 类型 关联考勤类型字典
     */
    private Integer type;
    /**
     * 备注
     */
    private String desc;
    /**
     * 状态 关联状态表
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}