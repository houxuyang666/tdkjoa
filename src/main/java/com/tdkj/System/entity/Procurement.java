package com.tdkj.System.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * (Procurement)实体类
 *
 * @author makejava
 * @since 2020-07-30 09:57:27
 */
@Data
public class Procurement implements Serializable {
    private static final long serialVersionUID = -62301755074903965L;
    /**
     * 采购单编号
     */
    private String proid;
    /**
     * 公司编号 公司ID
     */
    private Integer corpid;
    /**
     * 采购日期
     */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date prodate;
    /**
     * 采购类型 关联采购类型字典表
     */
    private Integer protype;
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
    private Integer number;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 总金额
     */
    private BigDecimal totalamount;
    /**
     * 申请人 申请人ID
     */
    private Integer applicantid;
    /**
     * 申请部门 申请部门ID
     */
    private Integer applicationdeptid;
    /**
     * 备注
     */
    private String prodesc;
    /**
     * 关联附件表 合同附件
     */
    private Integer fileinfoid;
    /**
     * 状态  关联采购字典表
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdate;
    /**
     * 编辑时间
     */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifydate;

}