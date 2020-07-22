package com.tdkj.System.shiro;

import com.tdkj.System.entity.Menu;
import com.tdkj.System.entity.User;
import com.tdkj.System.service.MenuService;
import com.tdkj.System.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    /*注入查询业务*/
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    /*执行授权逻辑*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权逻辑");
        //创建该类给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /*一定要与perms[user:add]中的字符串一致*/
        //添加资源的授权字符串 以下为硬编码 实际项目中使用数据库进行授权
        //info.addStringPermission("user:add");

        //到数据库中查询当前用户的授权字符串
        //获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        //也就是获取doGetAuthenticationInfo return 中参数带的user
        User user =(User)subject.getPrincipal();
        log.info("ShiroUsername:"+user.getUsername());
        //获取数据库中用户的
        User dbUser =userService.queryById(user.getUserid());
        log.info("DBusername:"+dbUser.getUsername());

        List<Menu> menuList = menuService.findByIdGetPerms(dbUser.getUserid());

        for (Menu list:menuList){
            info.addStringPermission(list.getPerms());
            log.info("用户拥有的权限:"+list.getPerms());
        }
        /*添加该用户的授权  现在只是一个 实际项目中应该是一个map  或者 查询返回list 循环遍历 add方法 加入到info中*/
        //info.addStringPermissions(); //dbUser.getPerms()
        return info;
    }
    /*执行认证逻辑*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证逻辑");

        UsernamePasswordToken tokens = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByName(tokens.getUsername());

        if (user == null) {
            return null;
        }

        // 这样通过配置中的 HashedCredentialsMatcher 进行自动校验  判断密码是否正确  其他两个参数是shiro需要的  password 是必要的 该user 需要传到doGetAuthorizationInfo 为授权做准备
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());// 参数分别为:
    }


}
