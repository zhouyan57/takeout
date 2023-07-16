package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ClassName: RedisConfiguration
 * Package: com.sky.config
 * Description:
 *
 * @Author: Jane
 * @Create: 2023/7/16 - 20:05
 * @version: v1.0
 */
@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        // RedisConnectionFactory redis的连接工厂对象，并不需要自己去创建，因为引入了starter，会把这个连接工厂
        // 对象创建好，并且放到spring容器当中， 所以只需要声明一下就可以注进来了
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置redis key的序列化器 // 字符串类型的redis序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;


    }
}
