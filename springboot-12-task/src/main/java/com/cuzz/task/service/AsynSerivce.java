package com.cuzz.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: cuzz
 * @Date: 2018/9/28 17:49
 * @Description:
 */
@Service
public class AsynSerivce {

    @Async
    public void hello() {
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中...");
    }
}
