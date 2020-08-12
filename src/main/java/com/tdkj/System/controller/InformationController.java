package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.entity.ImgInfo;
import com.tdkj.System.entity.Information;
import com.tdkj.System.service.CorpbasicinfoService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.InformationService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.FileuploadUtils;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.utils.TextUtil.getImgStr;
import static com.tdkj.System.utils.TextUtil.getfirstImg;

/**
 * @author chang
 * @version 1.0
 * @date 2020/8/11 16:34
 */
@Controller
@RequestMapping("information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @Autowired
    private CorpbasicinfoService corpbasicinfoService;


    @Value("${file.uploadinformationImage}")
    private String uploadContractTemplateFile;


    /*跳转通知公告页面*/
    @RequestMapping("/goinformation")
    public String goinformation() {
        return "page/information/informationlist";
    }


    /*跳转信息发布页面*/
    @RequestMapping("/goadd")
    public String goadd() {
        return "page/information/addinformation";
    }

    /*跳转信息预览页面*/
    @RequestMapping("/look")
    public String golook() {
        return "page/information/look";
    }


    /*跳转信息修改页面*/
    @RequestMapping("/modifyinfo")
    public String gomodify() {
        return "page/information/modifyinfo";
    }

    FileuploadUtils fileuploadUtils = new FileuploadUtils();

    /**
     * @param file:
     * @param response:
     * @param request:
     * @return ImgInfo
     * @author Chang
     * @description 富文本框图片上传
     * @date 2020/8/11 10:00
     */
    @RequestMapping("imgUpload")
    @ResponseBody
    public ImgInfo setImgUrl(@RequestParam("img") MultipartFile file, HttpServletResponse response,
                             HttpServletRequest request)
            throws Exception {
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        String path = uploadContractTemplateFile;
        File imgFile = new File(path);
        if (!imgFile.exists()) {
            imgFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();// 文件名称
        String trueFileName = System.currentTimeMillis() + fileName;

        try (FileOutputStream fos = new FileOutputStream(new File(path + trueFileName))) {
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String value = "/upload/informationimgs/" + trueFileName;
        String[] values = {value};

        ImgInfo imgInfo = new ImgInfo();

        imgInfo.setError(0);
        imgInfo.setUrl(values);

        System.out.println(imgInfo.toString());
        return imgInfo;
    }


    @RequestMapping("addinfo")
    @ResponseBody
    public OAResponse addNews(Integer infoid, String infotitle, String infocontent, String infodate, Integer corpid) {
        Corpbasicinfo corpbasicinfo = corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        Information information = new Information();
        information.setInfoid(infoid);
        information.setCorpid(corpbasicinfo.getCorpid());
        information.setInfotitle(infotitle);
        information.setInfocontent(infocontent);
        information.setInfoimg(getfirstImg(infocontent));
        information.setCreatedate(new Date());

        try {
            Date date = DateUtil.formatDate(infodate);
            information.setInfodate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Information insert = informationService.insert(information);
        return OAResponse.setResult(200, "添加成功");
    }


    @RequestMapping("getInfoList")
    @ResponseBody
    public PageInfo<Information> queryNews(Integer page, Integer limit, Integer infoid) {
        if (page == null && limit == null) {
            page = 1;
            limit = 5;
        }
        PageHelper.startPage(page, limit);
        Information information = new Information();
        Corpbasicinfo corpbasicinfo = corpbasicinfoService.queryByemployeeId(ShiroUtils.getPrincipal().getEmployeeid());
        information.setCorpid(corpbasicinfo.getCorpid());
        information.setInfoid(infoid);
        List<Information> InfoList = informationService.queryAll(information);
        PageInfo<Information> pageInfo = new PageInfo<>(InfoList);
        return pageInfo;
    }

    @GetMapping("selectone")
    @ResponseBody
    public Information getone(Integer infoid){
        Information information = informationService.queryById(infoid);
        return information;
    }

    @GetMapping("delinfo")
    @ResponseBody
    public OAResponse delinfo(Integer infoid) {
        String infocontent = informationService.queryById(infoid).getInfocontent();
        List<String> imgUrls = getImgStr(infocontent);
        if (imgUrls.size() > 0) {
            imgUrls.forEach(item -> {
                        Integer end = item.lastIndexOf("/") + 1;
                        String img = item.substring(end);
                        fileuploadUtils.Filedelete(uploadContractTemplateFile, img);
                    }
            );
        }
        boolean b = informationService.deleteById(infoid);
        if (b){
            return OAResponse.setResult(200, "删除成功");
        }
        return OAResponse.setResult(500, "删除失败");
    }

    @RequestMapping("modifyif")
    @ResponseBody
    public OAResponse modifyif(Integer infoid, String infotitle, String infocontent, String infodate){
        Information information = new Information();
        information.setInfoid(infoid);
        information.setInfotitle(infotitle);
        information.setInfocontent(infocontent);
        try {
            Date date = DateUtil.formatDate(infodate);
            information.setInfodate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        information.setModifydate(new Date());
        information.setInfoimg(getfirstImg(infocontent));


        informationService.update(information);
        return OAResponse.setResult(200, "修改成功");
    }
}