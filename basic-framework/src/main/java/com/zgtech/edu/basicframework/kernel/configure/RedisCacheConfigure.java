package com.zgtech.edu.basicframework.kernel.configure;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
public class RedisCacheConfigure extends CachingConfigurerSupport{

    private static final Log log = Logs.getLog(RedisCacheConfigure.class);

    /*redis主机地址*/
    @Value("${spring.redis.host}")
    private String host;

    /*redis端口号*/
    @Value("${spring.redis.port}")
    private int port;

    /*redis密码*/
    @Value("${spring.redis.password}")
    private String password;

    /*redis连接超时时间*/
    @Value("${spring.redis.timeout}")
    private int timeout;

    /*@Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;*/

    @Bean
    public JedisPool redisPoolFactory(){
        //log.info("连接redis信息："+host+"--"+port+"--"+timeout+"--"+password);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        JedisPool jedisPool = new JedisPool(host,port);
        return jedisPool;
    }
}
