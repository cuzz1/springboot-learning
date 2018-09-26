package com.cuzz.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.cuzz.cache.mapper")
@SpringBootApplication
@EnableCaching
public class Springboot07CacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot07CacheApplication.class, args);
	}
}
