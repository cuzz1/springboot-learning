package com.cuzz.elastic.bean;

import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @Author: cuzz
 * @Date: 2018/9/27 17:59
 * @Description:
 */
@Data
public class Article {
    @JestId
    private Integer id;
    private String autor;
    private String title;
    private String content;

    public Article() {
    }
}
