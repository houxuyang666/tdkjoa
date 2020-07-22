package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Attendance)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
@Data
public class Attendance implements Serializable {
    private static final long serialVersionUID = 772433905503578053L;
    /**
     * 考勤ID 自增ID
     */
    private Integer attendanceid;
    /**
     * 员工ID
     */
    private Integer userid;
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
    private String attendanceDesc;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}