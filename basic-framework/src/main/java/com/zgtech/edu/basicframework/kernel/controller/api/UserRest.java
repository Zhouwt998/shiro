package com.zgtech.edu.basicframework.kernel.controller.api;

import com.zgtech.edu.basicframework.kernel.model.dto.AjaxResult;
import com.zgtech.edu.basicframework.kernel.model.mapped.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserRest{




}
