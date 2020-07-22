package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Menu)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:50:59
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -23729806983471157L;
    /**
     * 菜单ID
     */
    private Integer menuid;
    /**
     * 父类ID
     */
    private Integer parentid;
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 请求路径 url
     */
    private String href;
    /**
     * 权限
     */
    private String perms;
    /**
     * 图标样式
     */
    private String icon;
    /**
     * 跳转方式
     */
    private String target;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 修改时间
     */
    private Date modifydate;

}