package com.cuzz.springboot.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: cuzz
 * @Date: 2018/9/20 19:34
 * @Description:
 */
@Data
@Component
public class Dog {
    private String name;
    private Integer age;
}
