package com.cuzz.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Springboot12TaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot12TaskApplication.class, args);
	}
}
