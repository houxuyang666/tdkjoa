package com.tdkj.System.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Corpbasicinfo)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Data
public class Corpbasicinfo implements Serializable {
    private static final long serialVersionUID = 733189212531616235L;
    /**
     * 企业ID
     */
    private Integer corpid;
    /**
     * 统一社会信用代码
     */
    private String corpcode;
    /**
     * 企业名称
     */
    private String corpname;
    /**
     * 企业登记注册类型
     */
    private Integer corptype;
    /**
     * 工商营业执照注册号
     */
    private String licensenum;
    /**
     * 企业营业地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private String zipcode;
    /**
     * 法定代表人姓名
     */
    private String legalman;
    /**
     * 法定代表人职务
     */
    private String legalmanduty;
    /**
     * 法定代表人证件类型
     */
    private Integer legalmanidcardtype;
    /**
     * 法定代表人证件号码
     */
    private String legalmanidcardnumber;
    /**
     * 注册日期
     */
    private Date registerdate;
    /**
     * 成立日期
     */
    private Date establishdate;
    /**
     * 办公电话
     */
    private String officphone;
    /**
     * 传真号码
     */
    private String faxnumber;
    /**
     * 联系人姓名
     */
    private String linkman;
    /**
     * 联系人电话
     */
    private String linkphone;
    /**
     * 电子签名名称
     */
    private String signname;
    /**
     * 电子签名URL
     */
    private String signurl;
    /**
     * 企业邮箱
     */
    private String email;
    /**
     * 企业网址
     */
    private String website;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}