package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.ImgInfo;
import com.tdkj.System.entity.Information;
import com.tdkj.System.service.InformationService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.utils.TextUtil.getImgStr;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/7 16:34
 */
@Controller
@RequestMapping("information")
public class InformationController {
    @Autowired
    InformationService informationService;

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


    /**
     *
     * @author Chang
     * @description 富文本框图片上传
     * @date 2020/8/11 10:00
     * @param file:
     * @param response:
     * @param request:
     * @return ImgInfo
     */
    @RequestMapping("imgUpload")
    @ResponseBody
    public ImgInfo setImgUrl(@RequestParam("img") MultipartFile file, HttpServletResponse response,
                             HttpServletRequest request)
            throws Exception {
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        String path=uploadContractTemplateFile;
        File imgFile = new File(path);
        if (!imgFile.exists()) {
            imgFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();// 文件名称
        String trueFileName=System.currentTimeMillis()+fileName;

        try (FileOutputStream fos = new FileOutputStream(new File(path + trueFileName))) {
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String value = "/image/informationimgs/" + trueFileName;
        String[] values = { value };

        ImgInfo imgInfo = new ImgInfo();

        imgInfo.setError(0);
        imgInfo.setUrl(values);

        System.out.println(imgInfo.toString());
        return imgInfo;
    }


    @RequestMapping("addinfo")
    @ResponseBody
    public OAResponse addNews(Integer infoid,String infotitle,String infocontent,String infodate,Integer corpid) {
        Information information=new Information();
        information.setInfoid(infoid);
        ShiroUtils.getPrincipal().getEmployeeid();
        information.setCorpid(corpid);
        information.setInfotitle(infotitle);
        information.setInfocontent(infocontent);
        //获取文本中图片路径
        List<String> imgUrls = getImgStr(infocontent);
        if (imgUrls.isEmpty()) {
            information.setInfoimg("default.png");
        } else {
            String s = imgUrls.get(0);
            Integer end = s.lastIndexOf("/")+1;
            String suoluetu = s.substring(end);
            information.setInfoimg(suoluetu);
        }
        System.out.println("----------------------------------------");
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


}