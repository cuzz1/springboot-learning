package com.cuzz.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot12TaskApplicationTests {

	@Autowired
	JavaMailSenderImpl mailSender;

	@Test
	public void contextLoads() {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setSubject("Hello World");
		message.setText("text");

		message.setTo("cuzz1234@163.com");
		message.setFrom("214769277@qq.com");

		mailSender.send(message);

	}

}
