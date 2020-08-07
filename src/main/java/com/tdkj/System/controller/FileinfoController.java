package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.FileTypeEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.entity.Fileinfotemporary;
import com.tdkj.System.service.CorpbasicinfoService;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.FileinfoService;
import com.tdkj.System.service.FileinfotemporaryService;
import com.tdkj.System.utils.FileuploadUtils;
import com.tdkj.System.utils.ShiroUtils;
import com.tdkj.System.utils.WordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tdkj.System.common.OAResultType.*;

/**
 * (Fileinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@Slf4j
@Controller
@RequestMapping("fileinfo")
public class FileinfoController {
    /**
     * 服务对象
     */
    @Resource
    private FileinfoService fileinfoService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CorpbasicinfoService corpbasicinfoService;
    @Autowired
    private FileinfotemporaryService fileinfotemporaryService;

    @Value("${file.uploadContractTemplateFile}")
    private String uploadContractTemplateFile;

    @Value("${file.uploadImageFolder}")
    private String uploadImageFolder;

    /*跳转查询合同页面*/
    @RequestMapping("/goselectfile")
    public String goselectfile() {
        return "page/file/fileinfolist";
    }

    /**
     * @Author houxuyang
     * @Description //查询本公司的所有合同模板
     * @Date 18:22 2020/8/5
     * @Param [page, limit]
     * @return com.tdkj.System.common.OAResponseList
     **/
    @ResponseBody
    @RequestMapping("/selectfileinfo")
    public OAResponseList selectfileinfo(Integer page, Integer limit) {
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());
        Fileinfo fileinfo =new Fileinfo();
        fileinfo.setCorpid(employee.getCorpid());
        fileinfo.setFileinfotype(FileTypeEnmu.Contract_template.getCode()); //查询类型为4的合同 即合同模板
        PageHelper.startPage(page,limit,true);
        //根据公司id查询公司所有合同
        List<Fileinfo> fileinfoList=fileinfoService.queryAll(fileinfo);
        PageInfo<Fileinfo> pageInfo=new PageInfo<>(fileinfoList);
        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }


    @RequestMapping("/goadd")
    public String goadd() {
        return "page/file/addfileinfo";
    }



   /* @RequestMapping("/goadd")
    public ModelAndView goadd(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page/file/addfileinfo");
        modelAndView.addObject("demopicture","D:/upload/demopicture/signdemo.png");
        return modelAndView;
    }*/


    /**
     * @Author houxuyang
     * @Description //上传合同模板
     * @Date 11:14 2020/8/6
     * @Param [name, url]
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String name, @RequestParam("file") MultipartFile url)throws Exception {
        //获取当前员工所在的公司ID
        Employee employee =employeeService.queryById(ShiroUtils.getPrincipal().getEmployeeid());

        Fileinfo fileinfo =new Fileinfo();
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
        if(null!=url&&url.getSize()>0){
            //生成4为随机数 第二个参数为是否要字母 第三个参数是否要数字
            String code= RandomStringUtils.random(4, true, true);
            //合同模板
            String fileurl = fileuploadUtils.Fileupload(url,uploadContractTemplateFile,"合同模板"+code,name);
            log.info("合同模板上传中");
            fileinfo.setCorpid(employee.getCorpid());
            fileinfo.setFileinfotype(FileTypeEnmu.Contract_template.getCode());
            fileinfo.setName(name+"合同模板");
            fileinfo.setUrl(fileurl);
            fileinfo.setCreatedate(new Date());
            fileinfo=this.fileinfoService.insert(fileinfo);
            //向临时数据表中插入该文件的原始名称
            Fileinfotemporary fileinfotemporary =new Fileinfotemporary();
            fileinfotemporary.setFileinfoid(fileinfo.getFileinfoid());
            fileinfotemporary.setUrl(fileinfo.getUrl());
            fileinfotemporary.setCreatedate(new Date());
            this.fileinfotemporaryService.insert(fileinfotemporary);
            log.info("合同模板上传成功");
        }
        Corpbasicinfo corpbasicinfo =corpbasicinfoService.queryById(employee.getCorpid());
        String fileUrl =setSign(fileinfo,corpbasicinfo.getSignurl(),uploadContractTemplateFile,uploadImageFolder);
        //更新表中路径信息
        Fileinfo newfileinfo =new Fileinfo();
        newfileinfo.setFileinfoid(fileinfo.getFileinfoid());
        newfileinfo.setUrl(fileUrl);
        newfileinfo.setModifydate(new Date());
        this.fileinfoService.update(newfileinfo);
        //TimeUnit.MILLISECONDS.sleep(10000);
        /*添加文件时删除处此文件之外的其他文件*/
//      获取除了本次上传的文件外的其他文件url
        List<Fileinfotemporary> temporaryList =this.fileinfotemporaryService.queryAlltemporaryUrl(fileinfo.getFileinfoid());
        for (Fileinfotemporary fileinfotemporary : temporaryList) {
            fileuploadUtils.Filedelete(uploadContractTemplateFile,fileinfotemporary.getUrl());
            this.fileinfotemporaryService.deleteById(fileinfotemporary.getFileinfoid());
        }
        return OAResponse.setResult(0,ADD_SUCCESS);
    }

    /**
     * @Author houxuyang
     * @Description //替换电子签名
     * @Date 14:38 2020/8/7
     * @Param [fileinfo, signurl, uploadContractTemplateFile, uploadImageFolder]
     * @return java.lang.String
     **/
    public static String setSign(Fileinfo fileinfo,String signurl,String uploadContractTemplateFile,String uploadImageFolder) throws IOException {
        String url = uploadImageFolder+signurl; //图片路径
        String templateurl = uploadContractTemplateFile+fileinfo.getUrl(); //文件路径
        Map<String, Object> header = new HashMap<String, Object>(); //存图片
        header.put("width", 120);
        header.put("height", 60);
        header.put("type", "png");
        header.put("content", url);//图片路径
        Map<String, Object> param = new HashMap<String, Object>(); //图片或者文本的
        param.put("imgSign" , header);
        //StringBuilder a = new StringBuilder(url);
        XWPFDocument doc = WordUtil.generateWord(param, templateurl);
        String code= RandomStringUtils.random(4, true, true);
        String str=fileinfo.getUrl();
        String prefix=str.substring(0, str.indexOf("."));//截取.之前的字符串
        String suffix=str.substring(str.lastIndexOf(".")+1);//截取.之前的字符串

        String fileUrl=prefix+code+"."+suffix;
        FileOutputStream fopts = new FileOutputStream(uploadContractTemplateFile+fileUrl);
        doc.write(fopts);
        fopts.close();
        return fileUrl;
    }

    /**
     * @Author houxuyang
     * @Description //删除合同模板
     * @Date 11:48 2020/8/6
     * @Param [fileinfoid]
     * @return com.tdkj.System.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer fileinfoid){
        Fileinfo fileinfo = this.fileinfoService.queryById(fileinfoid);
        if (null!=fileinfo.getUrl()){
            FileuploadUtils fileuploadUtils =new FileuploadUtils();
            log.info(fileuploadUtils.Filedelete(uploadContractTemplateFile,fileinfo.getUrl()));
        }
        this.fileinfoService.deleteById(fileinfoid);
        log.info("删除数据库合同模板数据成功");
        return OAResponse.setResult(200,REMOVE_SUCCESS);
    }


    /**
     * @Author houxuyang
     * @Description //下载文件
     * @Date 13:35 2020/8/6
     * @Param [fileinfoid, request, response]
     * @return com.tdkj.System.common.OAResponse
     **/
    @RequestMapping("/download")
    public void downloadFile(Integer fileinfoid, HttpServletResponse response) throws Exception {
        //根据文件id在数据库中获取文件名
        Fileinfo fileinfo = this.fileinfoService.queryById(fileinfoid);
        FileuploadUtils fileuploadUtils =new FileuploadUtils();
        fileuploadUtils.Filedownload(uploadContractTemplateFile,fileinfo.getUrl(),response);
    }


    /**
     *
     * 2007版本word转换成html
     *
     * @param wordPath  word文件路径
     * @param wordName word文件名称无后缀
     * @param suffix   word文件后缀
     * @param htmlPath html存储地址
     * @return
     * @throws IOException
     */
    public static String Word2007ToHtml(String wordPath, String wordName, String suffix, String htmlPath)
            throws IOException {

        String htmlName = wordName + ".html";
        String imagePath = htmlPath + "image" + File.separator;
        // 判断html文件是否存在
        File htmlFile = new File(htmlPath + htmlName);
        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }
        // word文件
        File wordFile = new File(wordPath + File.separator + wordName + suffix);
        // 1) 加载word文档生成 XWPFDocument对象
        InputStream in = new FileInputStream(wordFile);
        XWPFDocument document = new XWPFDocument(in);
        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
        File imgFolder = new File(imagePath);
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(imgFolder));
        // html中图片的路径 相对路径
        options.URIResolver(new BasicURIResolver("image"));
        options.setIgnoreStylesIfUnused(false);
        options.setFragment(true);
        // 3) 将 XWPFDocument转换成XHTML
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream out = new FileOutputStream(htmlFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        return htmlFile.getAbsolutePath();
    }

  /*  public static void main(String[] args) {
        try {
            Word2007ToHtml("D:/upload/templatefile/", "唐都科技测试模板合同模板X8ZO", ".docx", "D:/upload/templatefile/");
           // Word2003ToHtml("D:/upload/templatefile/", "test合同模板jmd7", ".docx", "D:/upload/templatefile/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    public static String Word2003ToHtml(String wordPath, String wordName, String suffix, String htmlPath)
            throws IOException, TransformerException, ParserConfigurationException {
        String htmlName = wordName + ".html";
        final String imagePath = htmlPath + "image" + File.separator;
        // 判断html文件是否存在
        File htmlFile = new File(htmlPath + htmlName);


        if (htmlFile.exists()) {
            return htmlFile.getAbsolutePath();
        }
        // 原word文档
        final String file = wordPath + File.separator + wordName + suffix;
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        // 设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
                                      float heightInches) {
                File imgPath = new File(imagePath);
                if (!imgPath.exists()) {// 图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagePath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 图片在html文件上的路径 相对路径
                return "image/" + suggestedName;
            }
        });
        // 解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        // 生成html文件上级文件夹
        File folder = new File(htmlPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream outStream = new FileOutputStream(htmlFile);
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        return htmlFile.getAbsolutePath();
    }


}