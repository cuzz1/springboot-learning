package com.cuzz.springboot.controller;

import com.cuzz.springboot.entity.User;
import com.cuzz.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        // 用2.0这快会报错 换1.5就好了
        //  Error:(19, 35) java: 无法将接口 org.springframework.data.repository.query.QueryByExampleExecutor<T>中的方法 findOne应用到给定类型;
        //  需要: org.springframework.data.domain.Example<S>
        //  找到: java.lang.Integer
        //  原因: 无法推断类型变量 S
        //    (参数不匹配; java.lang.Integer无法转换为org.springframework.data.domain.Example<S>)
        User user = userRepository.findOne(id);
        return null;
    }

    @GetMapping("/user")
    public User insertUser(User user) {
        User save = userRepository.save(user);
        return save;
    }
}
