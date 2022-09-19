package com.example.redistest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages="com.example.redistest")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.fantasy.clash")
@ComponentScan(basePackages= "com.example")
@EnableMongoRepositories("com.example")
public class RedistestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedistestApplication.class, args);
	}

}
