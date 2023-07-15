package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * ClassName: AutoFillAspect
 * Package: com.sky.aspect
 * Description: 自定义切面，实现公共字段自动填充处理逻辑
 *
 * @Author: Jane
 * @Create: 2023/7/15 - 11:40
 * @version: v1.0
 */
@Aspect // 该类声明为切面类
@Component // 把该类实例化放入到spring容器中
@Slf4j // log日志
public class AutoFillAspect {
    // 切面就是 通知 + 切入点(对哪些类的哪些方法进行拦截)
    /**
     * 切入点
     */
    // .*所有的类 .*所有的方法 (..)匹配所有的参数类型 &&后面：满足加入了AutoFill注解的方法
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知，在通知中进行公共字段的赋值
     */
    // 通知分为 前置通知、后置通知、环绕通知、异常通知等 此处用前置通知
    // 当匹配上切点表达式后 会执行通知的方法
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinpoint){
        log.info("开始进行公共字段的自动填充...");
        // 传进来的参数是连接点 通过连接点知道 哪个方法被拦截到了 以及方法的参数是样的(参数值、参数类型)

        // 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinpoint.getSignature(); // 向下转型 方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 获得方法上的注解对象
        OperationType operationType = autoFill.value(); // 获得数据库操作类型

        // 获取到当前被拦截的方法上的参数--实体对象
        Object[] args = joinpoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object entity = args[0]; // 约定实体放在第一位

        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now(); // 当前时间
        Long currentId = BaseContext.getCurrentId(); // 当前登录用户id

        // 根据当前不同的操作类型，为对应的属性通过反射赋值
        if(operationType == OperationType.INSERT){
            // 为4个公共字段赋值
            try {
                // 获得set方法
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                // 通过反射来为对象属性赋值
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else if(operationType == OperationType.UPDATE){
            // 为2个公共字段赋值
            try {
                // 获得set方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                // 通过反射来为对象属性赋值
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
