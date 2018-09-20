package com.cuzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: cuzz
 * @Date: 2018/9/20 18:06
 * @Description: @SpringBootApplication 来标注一个主程序，说明这是一个SpringBoot应用
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        // Spring应用启动起来
        SpringApplication.run(Application.class, args);
    }
}
