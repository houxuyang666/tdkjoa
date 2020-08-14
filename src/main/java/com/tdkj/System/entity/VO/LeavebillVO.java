package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Leavebill;
import lombok.Data;

/**
 * (Leavebill)实体类
 *
 * @author makejava
 * @since 2020-07-23 21:08:47
 */
@Data
public class LeavebillVO extends Leavebill {
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 员工名称
     */
    private String name;

}