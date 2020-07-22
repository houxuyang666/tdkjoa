package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Procurement)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:50:53
 */
@Data
public class Procurement implements Serializable {
    private static final long serialVersionUID = 154293786465229061L;
    /**
     * 采购单编号
     */
    private String proid;
    /**
     * 公司编号 公司ID
     */
    private Integer corpid;
    /**
     * 采购日期 部门名称
     */
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
    private Integer numbe;
    /**
     * 单价
     */
    private Double price;
    /**
     * 总金额
     */
    private Double totalamount;
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
     * 审核人
     */
    private Integer reviewer;
    /**
     * 审核意见
     */
    private String reviewerdesc;
    /**
     * 验收人 验收人ID
     */
    private Integer acceptorid;
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
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}