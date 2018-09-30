package com.cuzz.user;

import com.cuzz.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot14ConsumerUserApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
		userService.hello();
	}
}
