package com.foreignCurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"com.foreignCurrency.dao","com.foreignCurrency.service","com.foreignCurrency.controller"})
@EnableJpaRepositories
public class AppConfig {
	
	
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}


}
