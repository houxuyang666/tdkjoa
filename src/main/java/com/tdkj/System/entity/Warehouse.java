package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Warehouse)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
@Data
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 288270505528730696L;
    /**
     * 编号ID 仓库ID
     */
    private Integer warehouseid;
    /**
     * 公司编号 公司ID
     */
    private Integer corpid;
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
    private Integer totalnumbe;
    /**
     * 单价
     */
    private Double price;
    /**
     * 总金额
     */
    private Double totalamount;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}