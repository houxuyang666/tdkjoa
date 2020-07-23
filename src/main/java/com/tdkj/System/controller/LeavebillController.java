package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponse;
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

import static com.tdkj.System.common.OAResultType.ADD_SUCCESS;
import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

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


    @ResponseBody
    @RequestMapping("/selectleavebill")
    public OAResponse selectleavebill(Integer page, Integer limit) {
        log.info("selectleavebill");
        /*如果是超级管理员那么可以查询所有 ，但是公司需要区分*/
        //获取当前用户的id
        Leavebill leavebill =new Leavebill();
        leavebill.setUserid(ShiroUtils.getPrincipal().getUserid());
        List<Leavebill> leavebillList=leavebillService.queryAllLeavebill(leavebill);
        if (null != leavebillList && leavebillList.size() > 0) {
            for (Leavebill Leavebill : leavebillList) {
                log.info(Leavebill.toString());
            }
        }
        return OAResponse.setResult(0,FIND_SUCCESS);
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
        String state="0";
        Leavebill leavebill =new Leavebill(title,content,days, DateUtil.getformatDate(leavetime),state,ShiroUtils.getPrincipal().getUserid());
        leavebillService.insert(leavebill);
        return OAResponse.setResult(0,ADD_SUCCESS);
    }

}