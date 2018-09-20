package com.cuzz.springboot.config;

import com.cuzz.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: cuzz
 * @Date: 2018/9/20 20:49
 * @Description: 指明当前类是一个配置类，来代替以前的Spring配置文件
 *               在配置文件中用<bean></bean>来添加组件的
 */
@Configuration
public class MyAppConfig {

    // 将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
    @Bean
    public HelloService helloService() {
        System.out.println("配置类@Bean给容器中添加组件了...");
        return new HelloService();
    }
}
