package com.cuzz.springboot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: cuzz
 * @Date: 2018/9/20 20:41
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHelloService {


    @Autowired
    ApplicationContext ioc;


    @Test
    public void testHelloService() {
        boolean b = ioc.containsBean("helloService");
        System.out.println(b); // true
    }

}
