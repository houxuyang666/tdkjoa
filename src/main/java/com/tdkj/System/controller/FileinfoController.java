package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.FileTypeEnmu;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Employee;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.FileinfoService;
import com.tdkj.System.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * (Fileinfo)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@RestController
@RequestMapping("fileinfo")
public class FileinfoController {
    /**
     * 服务对象
     */
    @Resource
    private FileinfoService fileinfoService;
    @Autowired
    private EmployeeService employeeService;

    @Value("${file.uploadContractTemplateFile}")
    private String uploadContractTemplateFile;

    /*跳转查询合同页面*/
    @RequestMapping("/goselectfile")
    public String goselectfile() {
        return "page/file/filelist";
    }

    @ResponseBody
    @RequestMapping("/selectfile")
    public OAResponseList selectfile(Integer page, Integer limit) {
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
        return "page/file/addfile";
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Fileinfo selectOne(Integer id) {
        return this.fileinfoService.queryById(id);
    }





}