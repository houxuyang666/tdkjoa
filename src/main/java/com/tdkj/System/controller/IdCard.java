package com.tdkj.System.controller;

import com.tdkj.System.utils.FileuploadUtils;
import com.tdkj.System.utils.RedisUtil;
import com.tdkj.System.utils.idUtils.Base64Util;
import com.tdkj.System.utils.idUtils.FileUtil;
import com.tdkj.System.utils.idUtils.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/8/28 14:51
 */
@Slf4j
@Controller
@RequestMapping("idcode")
@RequiredArgsConstructor(onConstructor =@_(@Autowired))
public class IdCard {

    private static String ak ="DTDebPhQY93ZPagMAHjGjP3r";

    private static String sk="OC3vhSwRfAdP9hm1svOcoEcdZnfqS2YO";

    private final RedisUtil redisUtil;

    @Value("${file.uploadTempImageFolder}")
    private String uploadTempImageFolder;




    @RequestMapping("goidcode")
    public String idcard(){
        return "page/idcode/idcode";
    }

        /**
         * 重要提示代码中所需工具类
         * FileUtil,Base64Util,HttpUtil,GsonUtils请从
         * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
         * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
         * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
         * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
         * 下载
         */
        @ResponseBody
        @RequestMapping("getidcode")
        public String idcard(@RequestParam(value ="tempimg",required = false) MultipartFile tempimg) {
            // 请求url
            String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
            try {
                /*获取本地文件后获取本地路径*/
                FileuploadUtils fileuploadUtils =new FileuploadUtils();
                String code= RandomStringUtils.random(10, false, true);
                String tempImgName = fileuploadUtils.FileuploadTempimage(tempimg, uploadTempImageFolder, code);
                // 本地文件路径
                String filePath = uploadTempImageFolder+tempImgName;
                byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                String imgParam = URLEncoder.encode(imgStr, "UTF-8");

                String param = "id_card_side=" + "front" + "&image=" + imgParam;

                // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                String accessToken = getAuth(ak,sk);

                String result = HttpUtil.post(url, accessToken, param);
                System.out.println(result);
                /*获取完成后根据 临时名称删除临时文件*/
                fileuploadUtils.Filedelete(uploadTempImageFolder,tempImgName);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth(String ak, String sk) {
        /*进来方法后获取redis 如果有的话直接返回  如果没有再去差百度库*/
        Object baidutoken = redisUtil.get("baidutoken");
        if (null!=baidutoken){
            return baidutoken.toString();
        }

        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");

            redisUtil.set("baidutoken",access_token,Long.valueOf(60*60*24*15));
            log.info("插入redis");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


}
