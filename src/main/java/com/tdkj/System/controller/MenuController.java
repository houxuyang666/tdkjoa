package com.tdkj.System.controller;

import com.tdkj.System.common.OAJson;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.entity.Menu;
import com.tdkj.System.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.tdkj.System.common.OAResultCode.HTTP_RNS_CODE_200;
import static com.tdkj.System.common.OAResultType.*;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:01
 */
@Slf4j
@Controller
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    @RequestMapping("/gomenu")
    public String gomenu() {
        log.info("menu");
        return "page/menu";
    }

    @ResponseBody
    @RequestMapping("/getmenu")
    public OAResponse getmenu() {
        List<Menu> menuList =menuService.findMenusAllMenu();
        return OAResponse.setResult(HTTP_RNS_CODE_200,FIND_SUCCESS, OAJson.toJson(menuList));
    }



    @RequestMapping("/goaddmenu")
    public String goaddmenu() {
        log.info("menu");
        return "page/menu/addmenu";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/addmenu")
    public OAResponse addmenu(Integer parentId,String title, String href, String perms,String icon,String target) {
        Menu menu =new Menu();
        menu.setParentid(parentId);
        menu.setTitle(title);
        menu.setHref(href);
        menu.setPerms(perms);
        menu.setIcon(icon);
        menu.setTarget(target);
        menu.setCreatedate(new Date());
        menu =menuService.insert(menu);
        log.info(menu.getMenuid().toString());
        /*添加时直接设置在权限管理表中插入关联*/
        menuService.insertroleAndmenu(1,menu.getMenuid());
        return OAResponse.setResult(HTTP_RNS_CODE_200,ADD_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/updatemenu")
    public OAResponse updatemenu(Integer menuId,Integer parentId,String title, String href, String perms,String icon,String target) {
        Menu menu =new Menu();
        menu.setMenuid(menuId);
        menu.setParentid(parentId);
        menu.setTitle(title);
        menu.setHref(href);
        menu.setPerms(perms);
        menu.setIcon(icon);
        menu.setTarget(target);
        menu.setModifydate(new Date());
        menuService.update(menu);
        return OAResponse.setResult(HTTP_RNS_CODE_200,UPDATE_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/delemenu")
    public OAResponse delemenu(Integer menuId) {
        menuService.deleteById(menuId);
        return OAResponse.setResult(HTTP_RNS_CODE_200,REMOVE_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/addroleAndmenu")
    public OAResponse addroleAndmenu(Integer roleId,Integer menuId) {
        menuService.insertroleAndmenu(roleId,menuId);
        return OAResponse.setResult(HTTP_RNS_CODE_200,ADD_SUCCESS);
    }

}