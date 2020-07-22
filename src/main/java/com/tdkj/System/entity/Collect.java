package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Collect)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:02
 */
@Data
public class Collect implements Serializable {
    private static final long serialVersionUID = -34172941035932296L;
    /**
     * 编号ID 仓库ID
     */
    private Integer collectid;
    /**
     * 公司编号 公司ID
     */
    private Integer corpid;
    /**
     * 领用部门 部门ID
     */
    private Integer deptid;
    /**
     * 领用人 用户ID
     */
    private Integer userid;
    /**
     * 货物名称
     */
    private String goodsname;
    /**
     * 单位
     */
    private String unit;
    /**
     * 型号
     */
    private String type;
    /**
     * 数量
     */
    private Integer numbe;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;


}