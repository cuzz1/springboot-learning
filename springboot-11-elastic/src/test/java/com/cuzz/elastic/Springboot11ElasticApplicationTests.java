package com.cuzz.elastic;

import com.cuzz.elastic.bean.Article;
import com.cuzz.elastic.bean.Book;
import com.cuzz.elastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot11ElasticApplicationTests {

	@Autowired
	JestClient jestClient;

	@Test
	public void contextLoads() {
        // 给Es中索引（保存）一个文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("Effect Java");
        article.setAutor("Joshua Bloch");
        article.setContent("Hello World");
        // 构建一个索引功能
        Index index = new Index.Builder(article).index("cuzz").type("article").build();

        try {
            //执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    @Test
    public void search(){
        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"Hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //构建搜索操作
        Search search = new Search.Builder(json).addIndex("cuzz").addType("article").build();

        //执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    BookRepository bookRepository;

	@Test
    public void testBook() {
	    Book book = new Book();
	    bookRepository.index(book);
    }

    @Test
    public void testSearch(){
        for (Book book : bookRepository.findByBookNameLike("Effect")) {
            System.out.println(book);
        }

    }
}
