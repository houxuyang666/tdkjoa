package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Vehicleorders)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:09
 */
@Data
public class Vehicleorders implements Serializable {
    private static final long serialVersionUID = -15367050128784890L;
    /**
     * 订单ID
     */
    private String orderid;
    /**
     * 部门名称 车辆id
     */
    private String vehicleid;
    /**
     * 用户id 关联用户ID
     */
    private Integer userid;
    /**
     * 车辆司机ID 关联司机ID
     */
    private Integer vehicledriverid;
    /**
     * 开始地址
     */
    private Integer beganaddress;
    /**
     * 目的地地址
     */
    private String destinationaddress;
    /**
     * 结束地址
     */
    private String endaddress;
    /**
     * 里程数
     */
    private String mileage;
    /**
     * 开始里程数照片 开始里程数照片
     */
    private String mileagebeganurl;
    /**
     * 结束里程数照片 结束里程数照片
     */
    private String mileageendurl;
    /**
     * 开始时间 预计开始时间
     */
    private Date begandate;
    /**
     * 结束时间 预计结束时间
     */
    private Date enddate;
    /**
     * 状态 关联车辆订单字典表
     */
    private Integer status;
    /**
     * 订单描述 描述
     */
    private String orderdesc;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}