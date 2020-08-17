package com.hills.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JwtSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSecurityDemoApplication.class, args);
	}
	

}
