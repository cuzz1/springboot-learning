package com.cuzz.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot07CacheApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;  // 操作字符串的

	@Autowired
	RedisTemplate redisTemplate;  // k-v 都是对象

	@Test
	public void contextLoads() {
	}

}
