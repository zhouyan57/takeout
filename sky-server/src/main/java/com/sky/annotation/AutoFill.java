package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: AutoFill
 * Package: com.sky.annotation
 * Description:自定义注解，用于标识某个方法需要进行公共字段自动填充处理
 *
 * @Author: Jane
 * @Create: 2023/7/15 - 11:34
 * @version: v1.0
 */
// 指定注解只能加在方法上面
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 指定数据库操作类型：UPDATE INSERT
    OperationType value();
}
