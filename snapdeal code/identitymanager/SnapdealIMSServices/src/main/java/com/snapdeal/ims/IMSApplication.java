package com.snapdeal.ims;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.ims.filter.ExceptionTimestampFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy
@ImportResource("classpath*:spring/application-context.xml")
@ComponentScan({ "com.snapdeal.ims", "com.snapdeal.ums.client",
		"com.snapdeal.ums.services", "com.snapdeal.ums.admin",
		"com.snapdeal.base.transport","com.snapdeal.payments", 
		"com.snapdeal.notifier"})
@SpringBootApplication
public class IMSApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(
				IMSApplication.class);
		application.setShowBanner(false);
		application.run(args);
	}
	
	@Bean
	public NotifierServiceClient getNotifierServiceClient(){
	   NotifierServiceClient fcNotifier  = new NotifierServiceClient();
	   return fcNotifier;
	}
	
	@Bean
   public ExceptionTimestampFilter exceptionTimestampFilter() {
       return new ExceptionTimestampFilter();
   }
}
