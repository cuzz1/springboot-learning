package com.cuzz.amqp.bean;

import lombok.Data;

/**
 * @Author: cuzz
 * @Date: 2018/9/27 14:22
 * @Description:
 */
@Data
public class Book {
    private String  bookName;
    private String author;

    public Book(){

    }

    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }
}
