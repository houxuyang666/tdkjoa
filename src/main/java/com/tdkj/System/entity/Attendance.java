package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Attendance)实体类
 *
 * @author makejava
 * @since 2020-08-13 08:11:25
 */
@Data
public class Attendance implements Serializable {
    private static final long serialVersionUID = -73127869279638035L;
    /**
     * 考勤ID 自增ID
     */
    private Integer attendanceid;
    /**
     * 员工ID
     */
    private Integer userid;
    /**
     * 公司ID
     */
    private Integer corpid;
    /**
     * 部门ID
     */
    private Integer deptid;
    /**
     * 上班时间
     */
    private Date workdate;
    /**
     * 下班时间
     */
    private Date closedate;
    /**
     * 备注
     */
    private String attendancedesc;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;


}