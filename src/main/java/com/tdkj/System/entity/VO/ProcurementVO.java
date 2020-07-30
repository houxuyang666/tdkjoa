package com.tdkj.System.entity.VO;

import com.tdkj.System.entity.Procurement;
import lombok.Data;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/30 15:16
 */
@Data
public class ProcurementVO extends Procurement {

    /*公司名称*/
    private String corpname;
    /**
     * 申请人名称
     */
    private String applicantname;
    /**
     * 申请部门名称
     */
    private String applicationdeptname;

    /**
     * 附件名称
     */
    private String filename;
    /**
     * 附件路径 相对路径
     */
    private String fileurl;



}
