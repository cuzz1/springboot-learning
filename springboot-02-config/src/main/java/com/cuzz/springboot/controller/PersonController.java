package com.cuzz.springboot.controller;

import com.cuzz.springboot.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cuzz
 * @Date: 2018/9/20 19:46
 * @Description:
 */
@RestController
public class PersonController {

    @Autowired
    Person person;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String person() {
        return person.toString();
    }
}
