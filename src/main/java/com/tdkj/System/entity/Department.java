package com.tdkj.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Department)实体类
 *
 * @author makejava
 * @since 2020-07-28 13:00:27
 */
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 242287330482270261L;
    /**
     * 部门编号 自增ID
     */
    private Integer deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 部门负责人
     */
    private Integer deptheadid;
    /**
     * 部门职责
     */
    private String deptdesc;
    /**
     * 所属公司 关联公司表
     */
    private Integer corpid;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdate;
    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifydate;

}