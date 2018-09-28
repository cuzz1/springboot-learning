package com.cuzz.task.controller;

import com.cuzz.task.service.AsynSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cuzz
 * @Date: 2018/9/28 17:51
 * @Description:
 */
@RestController
public class AsynController {

    @Autowired
    AsynSerivce asynSerivce;

    @GetMapping("/hello")
    public String hello() {
        asynSerivce.hello();
        return "success";
    }
}
