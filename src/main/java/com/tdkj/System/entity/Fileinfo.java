package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Fileinfo)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@Data
public class Fileinfo implements Serializable {
    private static final long serialVersionUID = 414926737330037678L;
    /**
     * 附件ID 参见数据字典：附件类型字典表
     */
    private Integer fileinfoid;
    /**
     * 公司ID
     */
    private Integer corpid;
    /**
     * 附件类型 参见数据字典：附件类型字典表
     */
    private Integer fileinfotype;
    /**
     * 业务编号
     */
    private Integer businesssysno;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 附件路径 相对路径
     */
    private String url;
    /**
     * 创建时间
     */
    private Date creatdate;
    /**
     * 编辑时间
     */
    private Date modifydate;
}