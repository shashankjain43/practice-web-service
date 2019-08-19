package com.snapdeal.opspanel.init;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/application-context*.xml")
@ComponentScan({ "com.snapdeal.opspanel.bulk,com.snapdeal.opspanel.audit,com.snapdeal.opspanel.clientkeymanagement,com.snapdeal.ims,com.snapdeal.vanila,com.snapdeal.payments,com.snapdeal.ims,com.snapdeal.opspanel,com.snapdeal.onecheck, com.snapdeal.opspanel.AbstractComponentabstract.Exception, com.snapdeal.bulkprocess,  com.snapdeal.opspanel.promotion" })

public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication application = new SpringApplication(Application.class);
		application.setShowBanner(false);
		application.run(args);
	}
	
}