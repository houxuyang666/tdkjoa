package com.tdkj.System.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/5/26 17:11
 */
public class FileuploadUtils {


    /**
     * @Author houxuyang
     * @Description //获取文件后缀名
     * @Date 15:37 2020/7/8
     * @Param [multipartFile]
     * @return java.lang.String
     **/
    public static String getfilesuffix(MultipartFile multipartFile){
        String suffix =multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
        return suffix;
    }

    /**
     * @Author houxuyang
     * @Description //上传普通文件
     * @Date 13:14 2020/8/6
     * @Param [multipartFile, uploadFile, desc, UniqueIdentification]
     * @return java.lang.String
     **/
    public String Fileupload(MultipartFile multipartFile,String uploadFile,String desc,String UniqueIdentification){
        String url=null;
        //创建附件文件夹 存放劳动合同
        File path = new File(uploadFile);
        if(!path.exists()){
            path.mkdirs();
        }
        //获取文件后缀名
        String filesuffix = getfilesuffix(multipartFile);
        try{
            multipartFile.transferTo(new File(path+"/"+UniqueIdentification+desc+"."+filesuffix));
            url =UniqueIdentification+desc+"."+filesuffix;
        }catch (IOException e){
            return "上传失败";
        }
        return url;
    }

    /**
     * @Author houxuyang
     * @Description //上传图片
     * @Date 13:15 2020/8/6
     * @Param [multipartFile, uploadImageFolder, desc, UniqueIdentification]
     * @return java.lang.String
     **/
    public String Fileuploadimage(MultipartFile multipartFile,String uploadImageFolder,String desc,String UniqueIdentification){
        String url=null;
        //创建图片文件夹 存放上传的图片
        File path = new File(uploadImageFolder);
        if(!path.exists()){
            path.mkdirs();
        }
        //获取文件后缀名
        String filesuffix = getfilesuffix(multipartFile);
        try{
            multipartFile.transferTo(new File(path+"/"+UniqueIdentification+desc+"."+filesuffix));
            url =UniqueIdentification+desc+"."+filesuffix;
        }catch (IOException e){
            return "上传失败";
        }
        return url;
    }

    /**
     * @Author houxuyang
     * @Description //上传合同模板
     * @Date 13:15 2020/8/6
     * @Param [multipartFile, uploadFile, desc, UniqueIdentification]
     * @return java.lang.String
     **/
    public String FileContractTemplateupload(MultipartFile multipartFile,String uploadFile,String desc,String UniqueIdentification){
        String url=null;
        //创建附件文件夹 存放合同模板
        File path = new File(uploadFile);
        if(!path.exists()){
            path.mkdirs();
        }
        //获取文件后缀名
        String filesuffix = getfilesuffix(multipartFile);
        try{
            multipartFile.transferTo(new File(path+"/"+UniqueIdentification+desc+"."+filesuffix));
            url =UniqueIdentification+desc+"."+filesuffix;
        }catch (IOException e){
            return "上传失败";
        }
        return url;
    }


    /**
     * @Author houxuyang
     * @Description //删除本地文件
     * @Date 13:15 2020/8/6
     * @Param [uploadUrl, fileUrl]
     * @return java.lang.String
     **/
    public String Filedelete(String uploadUrl,String fileUrl){
        String fileName = uploadUrl+fileUrl;
        File file = new File(fileName);// 根据指定的文件名创建File对象
        if (!file.exists()) { // 要删除的文件不存在
            return "文件" + fileName + "不存在，删除失败！";
        } else { // 要删除的文件存在
            if (file.isFile()) { // 如果目标文件是文件，判断是文件
                file.delete();//删除文件
                return "删除成功";
            }
            return "文件" + fileName + "存在，删除失败！";
        }

    }



    /**
     * @Author houxuyang
     * @Description //下载文件
     * @Date 14:13 2020/8/6
     * @Param [uploadUrl, fileUrl, response]
     * @return java.lang.String
     **/
    public String Filedownload(String uploadUrl,String fileUrl,HttpServletResponse response) throws Exception {
        if (null != fileUrl) {
            //文件所在目录路径
            String filePath = uploadUrl + fileUrl;
            File file = new File(filePath);
            if (!file.exists()) {
                return "文件不存在";
            } else {
                FileInputStream fileInputStream = new FileInputStream(file);
                //设置Http响应头告诉浏览器下载这个附件,下载的文件名也是在这里设置的
                response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileUrl, "UTF-8"));
                OutputStream outputStream = response.getOutputStream();
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = fileInputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, len);
                }
                fileInputStream.close();
                outputStream.close();
            }
        }
        return "未获取到文件名";

    }









}
