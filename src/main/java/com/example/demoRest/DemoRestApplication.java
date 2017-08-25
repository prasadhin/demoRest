package com.example.demoRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.controller")
@EntityScan(basePackages = {"com.example.model"})
@EnableJpaRepositories("com.example.repsoitory")

public class DemoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestApplication.class, args);
	}
}
