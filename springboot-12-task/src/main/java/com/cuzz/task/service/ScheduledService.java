package com.cuzz.task.service;

import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: cuzz
 * @Date: 2018/9/29 10:25
 * @Description:
 */
@Service
public class ScheduledService {

    // 表示周一到周六当秒为0时执行一次
    @Scheduled(cron = "0 * * * * MON-SAT")
    public void hello() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        System.out.println(date + "  hello...");
    }
}
