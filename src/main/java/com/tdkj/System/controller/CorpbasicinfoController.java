package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.service.CorpbasicinfoService;
import com.tdkj.System.service.LogService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.FileuploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.*;

/**
 * (Corpbasicinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Slf4j
@Controller
@RequestMapping("corpbasicinfo")
@PropertySource("classpath:application-dev.properties")//此处路径需要按需修改
public class CorpbasicinfoController {
    /**
     * 服务对象
     */
    @Autowired
    private CorpbasicinfoService corpbasicinfoService;
    @Autowired
    private LogService logService;

    @Value("${file.uploadImageFolder}")
    private String uploadImageFolder;


    @RequestMapping("goadd")
    public String goadd() {
        return "page/corpbasicinfo/addcorpbasicinfo";
    }

    @RequestMapping("goselectcorpbasicinfo")
    public String goselectcorpbasicinfo() {
        return "page/corpbasicinfo/corpbasicinfolist";
    }


    /**
     * @return com.github.pagehelper.PageInfo<com.tdkj.System.entity.Corpbasicinfo>
     * @Author houxuyang
     * @Description //查询公司列表
     * @Date 14:40 2020/8/3
     * @Param [page, limit, corpname, corpcode]
     **/
    @ResponseBody
    @RequestMapping("/selectcorpbasicinfo")
    public OAResponseList selectemployee(Integer page, Integer limit, String corpname, String corpcode) {
        PageHelper.startPage(page, limit, true);
        log.info(corpname);
        log.info(corpcode);
        Corpbasicinfo corpbasicinfo = new Corpbasicinfo();
        if (null != corpname) {
            corpbasicinfo.setCorpname(corpname);
        }
        if (null != corpcode) {
            corpbasicinfo.setCorpcode(corpcode);
        }
        List<Corpbasicinfo> corpbasicinfoList = corpbasicinfoService.queryAll(corpbasicinfo);
        PageInfo<Corpbasicinfo> pageInfo = new PageInfo<>(corpbasicinfoList);
        return OAResponseList.setResult(0, FIND_SUCCESS, pageInfo);
    }

    /**
     * @return com.tdkj.System.common.OAResponse
     * @Author houxuyang
     * @Description //添加公司
     * @Date 15:08 2020/8/3
     * @Param [corpcode, corpname, corptype, licensenum, address, zipcode, legalman, legalmanduty, legalmanidcardtype, legalmanidcardnumber, registerdate, establishdate, officphone, faxnumber, linkman, linkphone, signname, signurl, email, website]
     **/
    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String corpcode, String corpname, Integer corptype, String licensenum, String address, String zipcode,
                          String legalman, String legalmanduty, Integer legalmanidcardtype, String legalmanidcardnumber,
                          String registerdate, String establishdate, String officphone, String faxnumber, String linkman,
                          String linkphone, String signname, @RequestParam("signurl") MultipartFile signurl, String email, String website) throws Exception {
        Corpbasicinfo oldcorpbasicinfo = corpbasicinfoService.queryByCode(corpcode);
        if (null != oldcorpbasicinfo) {
            return OAResponse.setResult(HTTP_RNS_CODE_500, "公司已存在");
        }
        Corpbasicinfo corpbasicinfo = new Corpbasicinfo();
        corpbasicinfo.setCorpcode(corpcode);
        corpbasicinfo.setCorpname(corpname);
        corpbasicinfo.setCorptype(corptype);
        corpbasicinfo.setLicensenum(licensenum);
        corpbasicinfo.setAddress(address);
        corpbasicinfo.setZipcode(zipcode);
        corpbasicinfo.setLegalman(legalman);
        corpbasicinfo.setLegalmanduty(legalmanduty);
        corpbasicinfo.setLegalmanidcardtype(legalmanidcardtype);
        corpbasicinfo.setLegalmanidcardnumber(legalmanidcardnumber);
        corpbasicinfo.setRegisterdate(DateUtil.getformatDate(registerdate));
        corpbasicinfo.setEstablishdate(DateUtil.getformatDate(establishdate));
        corpbasicinfo.setOfficphone(officphone);
        corpbasicinfo.setFaxnumber(faxnumber);
        corpbasicinfo.setLinkman(linkman);
        corpbasicinfo.setLinkphone(linkphone);
        corpbasicinfo.setSignname(signname);
        FileuploadUtils fileuploadUtils = new FileuploadUtils();
        if (null != signurl && signurl.getSize() > 0) {
            //上传头像照 并返回url
            String sign = fileuploadUtils.Fileuploadimage(signurl, uploadImageFolder, "signurl", legalman);
            corpbasicinfo.setSignurl(sign);
            log.info("电子签名");
        }
        corpbasicinfo.setEmail(email);
        corpbasicinfo.setWebsite(website);
        corpbasicinfo.setCreatedate(new Date());
        corpbasicinfoService.insert(corpbasicinfo);
        //Log log = LogUtils.setLog("添加公司"+corpname);
        //logService.insert(log);
        return OAResponse.setResult(HTTP_RNS_CODE_200, ADD_SUCCESS);
    }


    /**
     * @return com.tdkj.System.common.OAResponse
     * @Author houxuyang
     * @Description //修改公司
     * @Date 15:08 2020/8/3
     * @Param [corpcode, corpname, corptype, licensenum, address, zipcode, legalman, legalmanduty, legalmanidcardtype, legalmanidcardnumber, registerdate, establishdate, officphone, faxnumber, linkman, linkphone, signname, signurl, email, website]
     **/
    @Transactional
    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(Integer corpid, String corpcode, String corpname, Integer corptype, String licensenum, String address, String zipcode,
                             String legalman, String legalmanduty, Integer legalmanidcardtype, String legalmanidcardnumber,
                             String registerdate, String establishdate, String officphone, String faxnumber, String linkman,
                             String linkphone, String signname, @RequestParam("signurl") MultipartFile signurl, String email, String website) throws Exception {

        Corpbasicinfo corpbasicinfo = new Corpbasicinfo();
        corpbasicinfo.setCorpid(corpid);
        corpbasicinfo.setCorpcode(corpcode);
        corpbasicinfo.setCorpname(corpname);
        corpbasicinfo.setCorptype(corptype);
        corpbasicinfo.setLicensenum(licensenum);
        corpbasicinfo.setAddress(address);
        corpbasicinfo.setZipcode(zipcode);
        corpbasicinfo.setLegalman(legalman);
        corpbasicinfo.setLegalmanduty(legalmanduty);
        corpbasicinfo.setLegalmanidcardtype(legalmanidcardtype);
        corpbasicinfo.setLegalmanidcardnumber(legalmanidcardnumber);
        corpbasicinfo.setRegisterdate(DateUtil.getformatDate(registerdate));
        corpbasicinfo.setEstablishdate(DateUtil.getformatDate(establishdate));
        corpbasicinfo.setOfficphone(officphone);
        corpbasicinfo.setFaxnumber(faxnumber);
        corpbasicinfo.setLinkman(linkman);
        corpbasicinfo.setLinkphone(linkphone);
        corpbasicinfo.setSignname(signname);
        FileuploadUtils fileuploadUtils = new FileuploadUtils();
        if (null != signurl && signurl.getSize() > 0) {
            //上传头像照 并返回url
            String sign = fileuploadUtils.Fileuploadimage(signurl, uploadImageFolder, "signurl", legalman);
            corpbasicinfo.setSignurl(sign);
            log.info("电子签名");
        }
        corpbasicinfo.setEmail(email);
        corpbasicinfo.setWebsite(website);
        corpbasicinfo.setModifydate(new Date());
        corpbasicinfoService.update(corpbasicinfo);
        //Log log = LogUtils.setLog("添加公司"+corpname);
        //logService.insert(log);
        return OAResponse.setResult(HTTP_RNS_CODE_200, UPDATE_SUCCESS);
    }


    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer corpid) {
        this.corpbasicinfoService.deleteById(corpid);
        return OAResponse.setResult(HTTP_RNS_CODE_200, REMOVE_SUCCESS);
    }


}