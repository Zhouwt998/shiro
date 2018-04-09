package com.zgtech.edu.basicframework.kernel.controller.action;

import com.zgtech.edu.basicframework.kernel.configure.shiro.ShiroSessionDao;
import com.zgtech.edu.basicframework.kernel.controller.api.UserRest;
import com.zgtech.edu.basicframework.kernel.model.mapped.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/user")
public class UserAction {

    @Autowired
    private ShiroSessionDao shiroSessionDao;

    private static final Log log = Logs.getLog(UserRest.class);

    @GetMapping("/loginUI")
    public String loginUI() {
        if(SecurityUtils.getSubject().isAuthenticated()){
            log.info("已登录");
            return "redirect:/view/user/welcome";
        }
        return "loginUI.html";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute(SecurityUtils.getSubject().getSession().getAttribute("user"));
        return "welcome.html";
    }

    @PostMapping("/login")
    public String login(User user, Model model){
        log.info("用户："+user.getUsername()+"-----验证开始");
        String msg = "";
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        Subject currentUser = SecurityUtils.getSubject();

        try{
            currentUser.login(token);
        }catch (UnknownAccountException uae){
            msg = uae.getMessage();
        }catch (IncorrectCredentialsException ice) {
            msg = "密码不匹配";
        }catch (AuthenticationException auth){
            log.error(auth);
        }

        if(currentUser.isAuthenticated()){
            log.info("-------认证通过-------");
            model.addAttribute(SecurityUtils.getSubject().getSession().getAttribute("user"));
            return "welcome.html";
        }else{
            model.addAttribute("msg",msg);
            return "loginUI.html";
        }

    }

}