package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.entity.Log;
import com.tdkj.System.service.CorpbasicinfoService;
import com.tdkj.System.service.LogService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_500;
import static com.tdkj.System.common.OAResultType.ADD_SUCCESS;

/**
 * (Corpbasicinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Controller
@RequestMapping("corpbasicinfo")
public class CorpbasicinfoController {
    /**
     * 服务对象
     */
    @Resource
    private CorpbasicinfoService corpbasicinfoService;
    @Autowired
    private LogService logService;



    @RequestMapping("goadd")
    public String goadd() {
        return "page/table/addcorpbasicinfo";
    }

    @RequestMapping("showcompany")
    public String showcompany() {
        return "page/companylist";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String corpcode, String corpname, Integer corptype, String licensenum, String address, String zipcode,
                          String legalman, String legalmanduty, Integer legalmanidcardtype, String legalmanidcardnumber,
                          String registerdate, String establishdate, String officphone, String faxnumber, String linkman,
                          String linkphone, String signname, String signurl, String email, String website) throws Exception{
            Corpbasicinfo oldcorpbasicinfo =corpbasicinfoService.queryByCode(corpcode);
            if (oldcorpbasicinfo != null) {
                return OAResponse.setResult(HTTP_RNS_CODE_500,"公司已存在");
            }
            Corpbasicinfo corpbasicinfo=new Corpbasicinfo();
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
            corpbasicinfo.setSignurl(signurl);
            corpbasicinfo.setEmail(email);
            corpbasicinfo.setWebsite(website);
            corpbasicinfo.setCreatedate(new Date());

            corpbasicinfoService.insert(corpbasicinfo);
            Log log = LogUtils.setLog("添加公司"+corpname);
            logService.insert(log);
        return OAResponse.setResult(HTTP_RNS_CODE_200,ADD_SUCCESS);
    }


    @RequestMapping("getcompanylist")
    @ResponseBody
    public PageInfo<Corpbasicinfo> getcompanylist(Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        PageInfo<Corpbasicinfo> pageInfo = new PageInfo<>(corpbasicinfoService.queryAlls());
        return pageInfo;
    }

}