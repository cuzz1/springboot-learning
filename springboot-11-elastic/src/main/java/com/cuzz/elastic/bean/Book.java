package com.cuzz.elastic.bean;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author: cuzz
 * @Date: 2018/9/27 18:32
 * @Description:
 */
@Document(indexName = "cuzz",type="book")
@Data
public class Book {
    private Integer id;
    private String bookName;
    private String auto;


    public Book() {
        super();
    }

    public Book(Integer id, String bookName, String auto) {
        super();
        this.id = id;
        this.bookName = bookName;
        this.auto = auto;
    }
}
