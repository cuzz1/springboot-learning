package com.cuzz.elastic.repository;

import com.cuzz.elastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @Author: cuzz
 * @Date: 2018/9/27 18:33
 * @Description:
 */
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {
    //自定义查询方法
    public List<Book> findByBookNameLike(String bookName);
}
