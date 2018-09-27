package com.cuzz.amqp.service;

import com.cuzz.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Author: cuzz
 * @Date: 2018/9/27 14:34
 * @Description:
 */
@Service
public class BookService {
    @RabbitListener(queues = "cuzz.news")
    public void receive(Book book){
        System.out.println(book);
    }

    @RabbitListener(queues = "cuzz")
    public void receive02(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
