package com.tdkj.System.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Fileinfotemporary)实体类
 *
 * @author makejava
 * @since 2020-08-07 15:18:57
 */
public class Fileinfotemporary implements Serializable {
    private static final long serialVersionUID = -14082790534020223L;
    /**
     * 附件ID 参见数据字典：附件类型字典表
     */
    private Integer fileinfoid;
    /**
     * 附件路径 相对路径
     */
    private String url;
    /**
     * 创建时间
     */
    private Date createdate;


    public Integer getFileinfoid() {
        return fileinfoid;
    }

    public void setFileinfoid(Integer fileinfoid) {
        this.fileinfoid = fileinfoid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

}