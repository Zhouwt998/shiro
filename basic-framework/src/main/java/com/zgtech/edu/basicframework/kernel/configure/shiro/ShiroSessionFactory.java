package com.zgtech.edu.basicframework.kernel.configure.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

public class ShiroSessionFactory implements SessionFactory{
    @Override
    public Session createSession(SessionContext initData) {
        ShiroSession shiroSession = new ShiroSession();

        return shiroSession;
    }
}
