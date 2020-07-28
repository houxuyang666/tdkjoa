package com.tdkj.System.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdkj.System.Enum.AuditStatusEnmu;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.service.LeavebillService;
import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.tdkj.System.common.OAResultType.*;

/**
 * (Leavebill)表控制层
 *
 * @author makejava
 * @since 2020-07-23 21:08:49
 */
@Slf4j
@Controller
@RequestMapping("leavebill")
public class LeavebillController {
    /**
     * 服务对象
     */
    @Resource
    private LeavebillService leavebillService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Leavebill selectOne(Integer id) {
        return this.leavebillService.queryById(id);
    }



    @RequestMapping("goselectleavebill")
    public String goselectleavebill() {
        return "page/leavebilllist";
    }

    @ResponseBody
    @RequestMapping("/selectleavebill")
    public OAResponseList selectleavebill(Integer page, Integer limit) {
        log.info("selectleavebill");
        /*如果是超级管理员那么可以查询所有 ，但是公司需要区分*/
        //获取当前用户的id
        Leavebill leavebill =new Leavebill();
        leavebill.setUserid(ShiroUtils.getPrincipal().getUserid());
        PageHelper.startPage(0,10,true);
        List<Leavebill> leavebillList=leavebillService.queryAllLeavebill(leavebill);
        PageInfo<Leavebill> pageInfo=new PageInfo<>(leavebillList);

        if (null != leavebillList && leavebillList.size() > 0) {
            for (Leavebill Leavebill : leavebillList) {
                log.info(Leavebill.toString());
            }
        }
        return OAResponseList.setResult(0,FIND_SUCCESS,pageInfo);
    }


    @RequestMapping("goadd")
    public String goadd() {
        return "page/Test/addleavebill";
    }



    @ResponseBody
    @RequestMapping("/add")
    public OAResponse add(String title, String content,String days,String leavetime)throws Exception {
        log.info("addleavebill");
        /*请假流程单 生成时状态为未申请*/

        Leavebill leavebill =new Leavebill(title,content,days, DateUtil.formatDate(leavetime), AuditStatusEnmu.To_audit.getCode(),ShiroUtils.getPrincipal().getUserid());
        leavebillService.insert(leavebill);
        return OAResponse.setResult(0,ADD_SUCCESS);
    }

    @RequestMapping("goupdate")
    public String goupdate() {
        return "page/Test/updateleavebill";
    }

    @ResponseBody
    @RequestMapping("/update")
    public OAResponse update(Integer id,String title, String content,String days,String leavetime)throws Exception {
        log.info("updateleavebill");
        /*修改请假单内容*/
        Leavebill leavebill =new Leavebill(title,content,days, DateUtil.formatDate(leavetime),AuditStatusEnmu.To_audit.getCode(),ShiroUtils.getPrincipal().getUserid());
        leavebill.setId(id);
        leavebillService.update(leavebill);
        return OAResponse.setResult(0,UPDATE_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public OAResponse delete(Integer id){
        log.info("deleteleavebill");
        /*删除请假单*/
        leavebillService.deleteById(id);
        return OAResponse.setResult(0,REMOVE_SUCCESS);
    }

}