package com.tdkj.System.entity.VO;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Employee)实体类
 *
 * @author makejava
 * @since 2020-07-17 14:51:07
 */
@Data
public class EmployeeVO implements Serializable {
    private static final long serialVersionUID = -71731910722822411L;
    /**
     * 员工ID 主键自增ID
     */
    private Integer employeeid;
    /**
     * 企业ID 关联企业表
     */
    private Integer corpid;
    /**
     * 企业名称
     */
    private String corpname;
    /**
     * 部门ID 关联部门表
     */
    private Integer departmentid;


    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 上级领导ID
     */
    private Integer superid;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 账号状态
     */
    private Integer userstatus;
    /**
     * 岗位名称
     */
    private String jobtitle;
    /**
     * 证件类型 参考数据字典：人员证件类型字典表
     */
    private Integer idcardtype;
    /**
     * 证件号码
     */
    private String idcardnumber;
    /**
     * 状态 参考数据字典：职员状态字典表
     */
    private Integer status;
    /**
     * 性别 参考数据字典：性别字典表
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 民族 身份证上民族信息
     */
    private String nation;
    /**
     * 学历
     */
    private Integer edulevel;
    /**
     * 出生日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 住址
     */
    private String address;
    /**
     * 入职时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date entrydate;
    /**
     * 转正时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date regulardate;
    /**
     * 离职时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date departuredate;
    /**
     * 头像 二代身份证上面的头像，尺寸为358px*441px
     */
    private String headimageurl;
    /**
     * 政治面貌
     */
    private Integer politicstype;
    /**
     * 手机号码
     */
    private String cellphone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 紧急联系人姓名
     */
    private String urgentlinkman;
    /**
     * 紧急联系电话
     */
    private String urgentlinkmanphone;
    /**
     * 正面照 URL
     */
    private String positiveidcardimageurl;
    /**
     * 反面照 URL
     */
    private String negativeidcardimageurl;
    /**
     * 附件URL 劳动合同
     */
    private Integer fileinfoid;
    /**
     * 附件URL 劳动合同url
     */
    private String fileUrl;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 编辑时间
     */
    private Date modifydate;

}