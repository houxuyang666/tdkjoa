package com.tdkj.System.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Information)实体类
 *
 * @author makejava
 * @since 2020-08-11 09:53:40
 */
public class Information implements Serializable {
    private static final long serialVersionUID = -23019221290519723L;
    
    private Integer infoid;
    /**
    * 标题
    */
    private String infotitle;
    /**
    * 通知图片
    */
    private String infoimg;
    /**
    * 内容
    */
    private String infocontent;
    /**
    * 通知时间
    */
    private Date infodate;
    /**
    * 公司id
    */
    private Integer corpid;
    /**
    * 创建时间
    */
    private Date createdate;
    /**
    * 修改时间
    */
    private Date modifydate;


    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public String getInfotitle() {
        return infotitle;
    }

    public void setInfotitle(String infotitle) {
        this.infotitle = infotitle;
    }

    public String getInfoimg() {
        return infoimg;
    }

    public void setInfoimg(String infoimg) {
        this.infoimg = infoimg;
    }

    public String getInfocontent() {
        return infocontent;
    }

    public void setInfocontent(String infocontent) {
        this.infocontent = infocontent;
    }

    public Date getInfodate() {
        return infodate;
    }

    public void setInfodate(Date infodate) {
        this.infodate = infodate;
    }

    public Integer getCorpid() {
        return corpid;
    }

    public void setCorpid(Integer corpid) {
        this.corpid = corpid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

}