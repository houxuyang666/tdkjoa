package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Vehicleorders;
import lombok.Data;

import java.io.Serializable;

/**
 * (Vehicleorders)实体类
 *
 * @author makejava
 * @since 2020-08-10 15:17:14
 */
@Data
public class VehicleordersVO extends Vehicleorders implements Serializable  {
    private static final long serialVersionUID = -83781885491543558L;

    /**
     * 申请人名称
     */
    private String employeename;

    /**
     * 司机名称
     */
    private String vehicledrivername;

    /**
     * 车辆名称
     */
    private String vehicletype;

}