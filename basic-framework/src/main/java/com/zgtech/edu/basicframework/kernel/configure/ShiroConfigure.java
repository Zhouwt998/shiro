package com.zgtech.edu.basicframework.kernel.configure;

import com.zgtech.edu.basicframework.kernel.configure.shiro.*;
import com.zgtech.edu.basicframework.kernel.model.mapped.Permission;
import com.zgtech.edu.basicframework.kernel.service.permission.PermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ShiroConfigure {

    @Autowired
    private PermissionService permissionService;


    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /*
    * shiro拦截器
    * */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/view/user/loginUI");
        shiroFilterFactoryBean.setSuccessUrl("/view/user/welcome");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/view/user/loginUI");
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        /*查询所有权限*/
        List<Permission> permissions = permissionService.findAllPermission();
        for(Permission permission : permissions){
            /*添加需要权限才能访问得url*/
            filterChainDefinitionMap.put(permission.getPerUrl(),"perms["+permission.getPermission()+"]");
            System.out.println(permission.getPerUrl());
        }
        filterChainDefinitionMap.put("/view/user/login","anon");
        filterChainDefinitionMap.put("/druid/**","anon");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /*凭证匹配器*/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /*
    * 自定义的Realm
    * */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCachingEnabled(true);
        return shiroRealm;
    }

    /*Ehcache缓存*/
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    /*
    * sessionDao（session的crud和redis缓存）
    * */
    @Bean
    public ShiroSessionDao shiroSessionDao(){
        return new ShiroSessionDao();
    }

    /*
    * 创建会话工厂
    * */
    @Bean
    public ShiroSessionFactory shiroSessionFactory(){
        return new ShiroSessionFactory();
    }

    /*
    * cookie模板
    * */
    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sessionId");
        return simpleCookie;
    }

    /*
    * 会话管理
    * */
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(simpleCookie());// 设置cookie模板
        sessionManager.setSessionDAO(shiroSessionDao());// 设置SessionDao
        sessionManager.setSessionFactory(shiroSessionFactory());// 设置SessionFactory
        sessionManager.setGlobalSessionTimeout(shiroSessionDao().getExpireTime());// 设置全局session超时时间
        sessionManager.setSessionListeners(shiroSessionListener());/*设置监听器*/
        sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
        sessionManager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        sessionManager.setCacheManager(ehCacheManager());/*设置缓存*/
        return sessionManager;
    }

    @Bean
    public Collection<SessionListener> shiroSessionListener(){
        Collection<SessionListener> shiroSessionListeners = new ArrayList<SessionListener>();
        shiroSessionListeners.add(new ShiroSessionListener());
        return shiroSessionListeners;
    }
    /*
    * 安全管理，认证，授权等
    * */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());//设置自定义的认证类
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(ehCacheManager());/*设置缓存*/
        return securityManager;
    }



}
