package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Vehicleinfo;
import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/7 17:07
 */
@Data
public class VehicleinfoVO extends Vehicleinfo {

    /**
     * 企业名称
     */
    private String corpname;

    /**
     * 员工姓名
     */
    private String name;
}
