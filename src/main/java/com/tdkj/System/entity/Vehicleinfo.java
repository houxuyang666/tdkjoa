package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Vehicleinfo)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
@Data
public class Vehicleinfo implements Serializable {
    private static final long serialVersionUID = -54427046028895854L;
    /**
     * 车辆ID 采购单号
     */
    private Integer vehicleinfoid;
    /**
     * 车辆型号 部门名称
     */
    private String vehicletype;
    /**
     * 车辆载人数量
     */
    private Integer vehicleseatsnumber;
    /**
     * 车辆隶属公司 关联公司ID
     */
    private Integer vehicleaffiliationcompany;
    /**
     * 车辆隶属个人  关联个人ID
     */
    private Integer vehicleaffiliationpersonal;
    /**
     * 状态  关联车辆状态表
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