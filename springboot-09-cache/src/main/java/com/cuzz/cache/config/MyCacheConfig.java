package com.cuzz.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @Author: cuzz
 * @Date: 2018/9/26 18:39
 * @Description:
 */
@Configuration
public class MyCacheConfig {

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return method.getName() + "[" + Arrays.asList(objects) + "]";
            }
        };
    }
}
