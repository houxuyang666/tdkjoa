package com.tdkj.System.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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




}
