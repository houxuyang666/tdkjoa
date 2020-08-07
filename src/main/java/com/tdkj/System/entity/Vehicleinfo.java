package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Vehicleinfo)实体类
 *
 * @author makejava
 * @since 2020-08-07 17:49:43
 */
@Data
public class Vehicleinfo implements Serializable {
    private static final long serialVersionUID = 786936166220065621L;
    /**
     * 车辆ID
     */
    private Integer vehicleinfoid;
    /**
     * 车辆型号
     */
    private String vehicletype;
    /**
     * 车辆载人数量
     */
    private Integer vehicleseatsnumber;
    /**
     * 车牌号
     */
    private String vehiclenumber;
    /**
     * 车辆隶属公司 关联公司ID
     */
    private Integer vehicleaffiliationcorpbasicinfo;
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