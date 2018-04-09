package com.zgtech.edu.basicframework.kernel.configure.shiro;

import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;
import com.zgtech.edu.basicframework.kernel.model.mapped.Role;
import com.zgtech.edu.basicframework.kernel.model.mapped.User;
import com.zgtech.edu.basicframework.kernel.service.role.RoleService;
import com.zgtech.edu.basicframework.kernel.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm{

    private static final Log log = Logs.getLog(ShiroRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ShiroSessionDao shiroSessionDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("——————开始添加权限——————");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<String> rolename = new ArrayList<String>();
        List<String> permission = new ArrayList<String>();

        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.fatchUser(username);

        /*获取用户角色列表*/
        List<Role> roles = user.getRoles();

        for(Role role : roles){
            rolename.add(role.getRolename());
            /*获取角色权限列表*/
            List<Permission> permissions = roleService.fetchRole(role.getRolename()).getPermissions();
            for(Permission permission1 : permissions){
                permission.add(permission1.getPermission());
            }
        }

        /*添加角色*/
        simpleAuthorizationInfo.addRoles(rolename);
        /*添加权限*/
        simpleAuthorizationInfo.addStringPermissions(permission);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        log.info("——————开始认证——————");

        String username = (String) authenticationToken.getPrincipal();

        User user = userService.fatchUser(username);
        if(user == null){
            throw new UnknownAccountException("用户不存在");
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("user",user);

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                //ByteSource.Util.bytes(user.getSalt()),//salt=username+salt,
                getName()
                );

        return simpleAuthenticationInfo;
    }
}
