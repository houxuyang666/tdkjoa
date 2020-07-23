package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Leavebill;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Leavebill)实体类
 *
 * @author makejava
 * @since 2020-07-23 21:08:47
 */
@Data
public class LeavebillVO extends Leavebill {
    //批量删除使用
    private Integer[] ids;

    private Integer page;
    private Integer limit;
    private Date startTime;
    private Date endTime;


}