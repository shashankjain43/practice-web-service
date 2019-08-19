package com.snapdeal.vanila.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan({ "com.snapdeal.vanila" })
@ImportResource("classpath*:spring/application-context.xml")

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
