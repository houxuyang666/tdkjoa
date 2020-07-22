package com.tdkj.System.configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.github.pagehelper.PageHelper;
import com.tdkj.System.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
public class ShiroConfig{
    /*
     * @Author houxuyang
     * @Description //创建ShiroFilterFactoryBean 该类用于设置资源拦截器 和资源授权
     * @Date 10:50 2020/5/22
     * @Param [securityManager]
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*shiro内置过滤器，可以实现权限相关的拦截器
        *   常用的过滤器：
        *           anon：无需认证可以访问
        *           authc：必须认证才能访问
        *           user：如果使用rememberMe的功能可以直接访问
        *           perms：该资源必须得到资源权限才能访问
        *           role：该资源必须得到角色权限才能访问
        * */
        /*创建一个map存储功能和他它的权限 第一个参数为controller的 RequestMapping*/
        Map<String ,String> filterMap =new LinkedHashMap<String,String>();
        /*也可以配合使用 让某个请求可以被访问 ，但是必须在通配符上面*/
        filterMap.put("/login","anon");

        /* 第二个参数配置为logout  不用配置cotroller就能直接退出 并清除session*/
        filterMap.put("/logout","logout");
        //放行用户注册
        filterMap.put("/user/register/**","anon");



        /*Swagger 开放白名单*/
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        /*授权拦截器  访问add 需要perms[user:add] 该授权*/
        //方法的授权
        /*filterMap.put("/adduser","perms[user:add]");
        filterMap.put("/updatepassword","perms[user:updatepassword]");
        filterMap.put("/select","perms[user:select]");
        filterMap.put("/delete","perms[user:delete]");*/
        /*授权拦截器 结束*/

        /*放行静态资源-开始*/
        filterMap.put("/statics/**","anon");
        filterMap.put("/static/**","anon");
        filterMap.put("/api/**","anon");
        filterMap.put("/css/**","anon");
        filterMap.put("/images/**","anon");
        filterMap.put("/js/**","anon");

        /*放行静态资源-结束*/
        /*用通配符  */
        filterMap.put("/**","user"); //user表示配置记住我或认证通过可以访问的地址
        /*修改被拦截之后跳转的页面  参数为controller的 RequestMapping*/
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        /*登录成功后跳转的连接*/
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        /*设置未授权跳转的页面*/
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        /*将存好的权限交给shiroFilterFactoryBean 处理*/
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /*
     * @Author houxuyang
     * @Description //创建DefaultWebSecurityManager 默认的web安全管理器 获取Realm进行管理
     * @Date 10:50 2020/5/22
     * @Param [userRealm]
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     **/
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        /*创建一个默认的web安全管理器 ，将自定义Realm 交给shiro管理*/
        DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(rememberMeManager());//（增加改行）配置记住我
        return securityManager;
    }
    //会话管理器
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setGlobalSessionTimeout(1 * 60 * 60 * 1000);//session过期时间
        sessionManager.setDeleteInvalidSessions(true);//是否删除过期session
        sessionManager.setSessionIdCookie(rememberMeCookie());
        return sessionManager;
    }

    /**
     * （新增方法）
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        log.info("rememberMeCookie");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        //simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间 ,单位秒;-->
        simpleCookie.setMaxAge(86400);
        return simpleCookie;
    }

    /**
     * （新增方法）
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        log.info("rememberMeManager");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /*
     * @Author houxuyang
     * @Description //创建Realm  new了一个自定义Realm
     * @Date 10:50 2020/5/22
     * @Param []
     * @return UserRealm
     **/
    @Bean
    public UserRealm getUserRealm(){
        /*UserRealm用于验证用户 以及授权用户 等逻辑*/
        return new UserRealm();
    }

    /*
     * @Author houxuyang
     * @Description //配置ShiroDialect ，用于thymeleaf和shiro标签配合使用
     * @Date 10:49 2020/5/22
     * @Param []
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     **/
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     *后端分页插件
     */
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
