package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表(Role)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -30925675121568241L;
    /**
     * 主键
     */
    private Integer roleid;
    /**
     * 角色名称
     */
    private String rolename;

}