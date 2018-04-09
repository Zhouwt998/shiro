package com.zgtech.edu.basicframework.kernel.configure.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

public class ShiroSessionListener implements SessionListener{

    private static final Log log = Logs.getLog(ShiroSessionListener.class);
    @Autowired
    private ShiroSessionDao shiroSessionDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void onStart(Session session) {
        log.info("session {"+session.getId()+"} onStart");
    }

    @Override
    public void onStop(Session session) {
        shiroSessionDao.delete(session);
        log.info("session {"+session.getId()+"} onStop");
    }

    @Override
    public void onExpiration(Session session) {
        //shiroSessionDao.delete(session);
        log.info("session {"+session.getId()+"} onExpiration");
    }
}
