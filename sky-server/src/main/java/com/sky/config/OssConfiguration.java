package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: OssConfiguration
 * Package: com.sky.config
 * Description: 配置类，用于创建AliOssUtil对象
 *
 * @Author: Jane
 * @Create: 2023/7/15 - 19:54
 * @version: v1.0
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean  // 当项目启动时，就会调用这个方法把对象创建出来，交给string容器去管理
    @ConditionalOnMissingBean // 当没有这个Bean的时候再去创建（保证整个spring容器里面只有一个Util对象）
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传工具类对象：{}",aliOssProperties);

        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
