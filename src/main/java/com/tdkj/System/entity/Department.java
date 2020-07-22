package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Department)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:06
 */
@Data
public class Department implements Serializable {
    private static final long serialVersionUID = 326760780405617341L;
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
    private String depthead;
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
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}