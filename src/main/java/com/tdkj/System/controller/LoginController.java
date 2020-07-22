package com.tdkj.System.controller;

import com.tdkj.System.annotation.Limit;
import com.tdkj.System.common.OAJson;
import com.tdkj.System.common.OAResponse;
import com.tdkj.System.common.OAResultCode;
import com.tdkj.System.common.OAResultType;
import com.tdkj.System.entity.Log;
import com.tdkj.System.entity.Menu;
import com.tdkj.System.entity.MenuTree;
import com.tdkj.System.entity.User;
import com.tdkj.System.exception.OAException;
import com.tdkj.System.service.EmployeeService;
import com.tdkj.System.service.LogService;
import com.tdkj.System.service.MenuService;
import com.tdkj.System.service.UserService;
import com.tdkj.System.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/6/30 11:43
 */

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class LoginController implements OAResultType, OAResultCode {
    private final ValidateCodeUtil validateCodeUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MenuService menuService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/index")
    public String index() {
        return "page/index";
    }


    @RequestMapping("/welcome")
    public String welcome() {
        return "page/welcome";
    }


    /**
     * @Author houxuyang
     * @Description //查询目录
     * @Date 15:26 2020/6/18
     * @Param []
     * @return com.tdkj.RNS.common.OAResponse
     **/
    @ResponseBody
    @RequestMapping("/indexinit")
    public OAResponse indexinit() {
        MenuTree<Menu> menuList =menuService.findByUsernameGetMenu(ShiroUtils.getPrincipal().getUsername());
        return OAResponse.setResult(HTTP_RNS_CODE_200,FIND_SUCCESS, OAJson.toJson(menuList),employeeService.getName(ShiroUtils.getPrincipal().getEmployeeid()));
    }

    @ResponseBody
    @RequestMapping("/login")
    @PostMapping
    public OAResponse login(String username, String password, boolean rememberMe, String verifyCode,
                             HttpServletRequest request) throws Exception {
        log.info("-----login");
        /*使用Shiro编写认证操作
         *1.获取subjec
         * */
        Subject subject = SecurityUtils.getSubject();
        try {
            HttpSession session = request.getSession();
            validateCodeUtil.check(session.getId(), verifyCode);
            User user = userService.findByName(username);
            if (0 == user.getStatus()) {
                //model.addAttribute("msg", "账户已被冻结，请联系管理员");
                return OAResponse.setResult(HTTP_RNS_CODE_401,"账户已被冻结，请联系管理员");
            }
            password = Md5Util.Md5Password(user.getSalt(), password);
            /*2.封装用户数据*/ //记住我
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            /*3.执行登录操作*/
            //会将用户信息传给 UserRealm的doGetAuthenticationInfo方法的authenticationToken参数 用于与数据库验证

            subject.login(token);
            //token.setRememberMe(true);
            /*设置session*/
            Log log = LogUtils.setLog("登录"); //将操作传过去生成对象后 插入DB
            logService.insert(log);
            Session session1 = subject.getSession();
            session1.setAttribute("user", user);
            //session1.setAttribute("name", userinfoService.queryById(user.getUserinfoId()).getName());
            return OAResponse.setResult(HTTP_RNS_CODE_200,"/index");
        } catch(OAException e){
            return OAResponse.setResult(HTTP_RNS_CODE_401,"验证码错误！");
        }catch (UnknownAccountException e){
            //UnknownAccountException 指的是用户名不存在 但是为了防止恶意扫描账号 提示为用户名或密码不正确
            return OAResponse.setResult(HTTP_RNS_CODE_401,"用户名或密码错误！");
        }catch (IncorrectCredentialsException e){
            return OAResponse.setResult(HTTP_RNS_CODE_401,"用户名或密码错误");
        }catch (NullPointerException e){
            //由于在此需要获取用户的盐值及用户名等 会出现null错误 所以添加try
            return OAResponse.setResult(HTTP_RNS_CODE_401,"用户名或密码错误");
        }
    }

    @GetMapping("images/captcha")
    @Limit(key = "get_captcha", period = 60, count = 10, name = "获取验证码", prefix = "limit")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, OAException {
        validateCodeUtil.create(request, response);
        log.info("测试生成验证码");
    }

    @RequestMapping("/tologin")
    public String toLogin() {
        log.info("------------------------------tologin");
        return "login";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        System.out.println("noAuth");
        return "noAuth";
    }
}
