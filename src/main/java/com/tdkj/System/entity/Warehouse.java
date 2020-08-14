package com.tdkj.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (Warehouse)实体类
 *
 * @author makejava
 * @since 2020-08-13 15:11:02
 */
@Data
public class Warehouse implements Serializable {
    private static final long serialVersionUID = -99993612922729802L;
    /**
     * 编号ID 仓库ID
     */
    private String warehouseid;
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
    private BigDecimal price;
    /**
     * 总金额
     */
    private BigDecimal totalamount;
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