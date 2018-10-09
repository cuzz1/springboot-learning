package com.cuzz.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Springboot14SpringcloudEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot14SpringcloudEurekaServerApplication.class, args);
	}
}
