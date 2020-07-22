package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 764711325741397412L;
    /**
     * 主键自增ID
     */
    private Integer userid;
    /**
     * 用户信息ID 职员数据表
     */
    private Integer employeeid;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 状态 0为注销 1为正常
     */
    private Integer status;
    /**
     * 权限ID 关联权限表
     */
    private Integer roleid;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}