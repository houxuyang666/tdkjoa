package com.tdkj.System.utils;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @ClassName TextUtil
 * @Author Chang
 * @date 2020.08.11 11:16
 */
public class TextUtil {

    public static List<String> getImgStr(String htmlStr) {
        List<String> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }

    public static String getfirstImg(String content){
        List<String> imgUrls = getImgStr(content);
        if (imgUrls.isEmpty()) {
          return  "default.png";
        } else {
            String s = imgUrls.get(0);
            Integer end = s.lastIndexOf("/") + 1;
            String firsturl = s.substring(end);
            return firsturl;
        }
    }
}
