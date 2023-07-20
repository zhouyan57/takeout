package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: MyTask
 * Package: com.sky.task
 * Description: 自定义定时任务类
 *
 * @Author: Jane
 * @Create: 2023/7/21 - 6:48
 * @version: v1.0
 */
@Component
@Slf4j
public class MyTask {
    /**
     * 定时任务 每隔5秒触发一次
     */
    // 指定任务何时触发 秒 分 小时 日 月 周 年
//    @Scheduled(cron = "0/5 * * * * ?") // 每隔五秒触发一次
//    public void executeTask(){
//        log.info("定时任务开始执行：{}", new Date());
//    }
}
