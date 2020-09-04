package com.tdkj.System.controller;

import com.tdkj.System.common.OAResponseList;
import com.tdkj.System.entity.Role;
import com.tdkj.System.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.List;

import static com.tdkj.System.common.OAResultType.FIND_SUCCESS;

/**
 * 角色表(Role)表控制层
 *
 * @author makejava
 * @since 2020-07-17 14:51:04
 */
@Slf4j
@Controller
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/selectrole")
    public OAResponseList selectrole() {
        log.info("selectrole");
        //获取除超级管理员以外的权限
        List<Role> role = roleService.queryAll(new Role());
        return OAResponseList.setResult(0, FIND_SUCCESS, role);
    }

}