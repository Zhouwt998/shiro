package com.zgtech.edu.basicframework.kernel.configure.shiro;

import com.zgtech.edu.basicframework.kernel.utils.SerializeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.joda.time.DateTime;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/*
*   重构SessionDao
*
* */
@Service
public class ShiroSessionDao extends CachingSessionDAO{

    private static final Log log = Logs.getLog(ShiroSessionDao.class);

    @Autowired
    private JedisPool jedisPool;

    /*保存到redis缓存中key的前缀*/
    private String prefix = "";

    //设置会话的过期时间
    private int expireTime = 1800000;

    /*
    * 如果session中没有登录信息就调用doReadSession方法从redis中重读
    * session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null 代表没有登录，登录后Shiro会放入该值
    * */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException{
        Session session = super.getCachedSession(sessionId);
        if(session == null || session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null){
            session = this.doReadSession(sessionId);
            if(session == null){
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }else {
                cache(session,session.getId());
            }
        }
        return session;
    }

    /*
    * 更新会话
    *
    * */
    @Override
    protected void doUpdate(Session session) {

        try {
            if(session instanceof ValidatingSession && !((ValidatingSession) session).isValid()){
                return;
            }
        }catch (Exception e){
            log.error("ValidatingSession error");
        }

        try {
            if(session instanceof ShiroSession){
                ShiroSession shiroSession = (ShiroSession) session;
                /*如果没有字段（除lastAccessTime以外其他字段）发生改变*/
                if(!shiroSession.getIsChanged()){
                    return;
                }
                Jedis jedis = null;
                Transaction transaction = null;
                try{
                    jedis = jedisPool.getResource();
                    /*开启事务*/
                    transaction = jedis.multi();
                    shiroSession.setIsChanged(false);
                    shiroSession.setLastAccessTime(DateTime.now().toDate());
                    transaction.setex(prefix+session.getId(),expireTime, SerializeUtils.serializaToString(shiroSession));
                    log.info("sessionId {"+session.getId()+"} 被更新");
                    /*执行事务*/
                    transaction.exec();
                }catch (Exception e){
                    if(transaction != null){
                        transaction.discard();
                    }
                    log.error(e.getMessage());
                }finally {
                    jedis.close();
                }
            }else if(session instanceof Serializable){
                Jedis jedis = jedisPool.getResource();
                jedis.setex(prefix + session.getId(), expireTime, SerializeUtils.serializaToString((Serializable) session));
                log.info("sessionId {"+session.getId()+"} 作为非ShiroSession对象被更新");
            }else{
                log.info("sessionId {"+session.getId()+"} 不能被序列化 更新失败");
            }
        }catch (Exception e){
            log.warn("更新Session失败", e);
        }


    }

    /*
    *
    * 删除会话
    * 当会话过期/会话停止时会调用
    *
    * */
    @Override
    protected void doDelete(Session session) {

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.del(prefix + session.getId());
            /*删除cache中缓存的Session*/
            this.uncache(session.getId());
            log.info(session.getId()+" 被删除");
        }catch (Exception e){
            log.warn("删除session失败", e);
        }finally {
            jedis.close();
        }
    }

    /*
    * 删除cache中缓存的Session
    * */
    public void uncache(Serializable sessionId){
        try {
            Session session = super.getCachedSession(sessionId);
            super.uncache(session);
            log.info("shiro session id {"+sessionId+"} 的缓存失效");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*
    * SessionMannger创建完session会调用此方法
    * */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session,sessionId);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //session由Redis缓存失效决定，这里作简单标识
            session.setTimeout(expireTime);
            String value = SerializeUtils.serializaToString((ShiroSession) session);
            jedis.setex(prefix + sessionId, expireTime, value);
            log.info("sessionId {"+session.getId()+"} 被创建");

        }catch (Exception e){
            log.warn("创建session失败",e);
        }finally {
            jedis.close();
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) throws UnknownSessionException {
        Jedis jedis = null;
        Session session = null;

        try {
            jedis = jedisPool.getResource();
            String key = prefix+sessionId;
            String value = jedis.get(key);
            if(StringUtils.isNotBlank(value)){
                session = SerializeUtils.deserializeFromString(value);
                //重置Redis中过期的时间
                jedis.expire(key,expireTime);
                log.info("sessionId {"+session.getId()+"} 被读取,剩余时间 {"+jedis.ttl(key)+"}: ");
            }
        }catch (Exception e){
            log.warn("读取session失败",e);
        }finally {
            jedis.close();
        }
        return session;
    }

    /**
     * 从Redis中读取，但不重置Redis中缓存过期时间
     */
    public Session doReadSessionWithoutExpire(Serializable sessionId) {
        Session session = null;
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String key = prefix+sessionId;
            String value = jedis.get(key);
            if(StringUtils.isNotBlank(value)){
                session = SerializeUtils.deserializeFromString(value);
            }
        }catch (Exception e){
            log.warn("读取session失败",e);
        }finally {
            jedis.close();
        }

        return session;
    }

    /*
    * 获取当前所有活跃用户
    * */
    @Override
    public Collection<Session> getActiveSessions(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> keys = jedis.keys(prefix+"*");
            if(keys.isEmpty()){
                return null;
            }
            List<String> values = jedis.mget(keys.toArray(new String[keys.size()]));
            return SerializeUtils.deserializeFromStrings(values);

        }catch (Exception e){
            log.warn("统计Session信息失败", e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
