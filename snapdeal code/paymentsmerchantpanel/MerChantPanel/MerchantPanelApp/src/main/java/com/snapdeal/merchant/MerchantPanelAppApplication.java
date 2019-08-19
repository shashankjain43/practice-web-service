package com.snapdeal.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

@EnableAspectJAutoProxy
@SpringBootApplication
@ImportResource("classpath*:spring/applicationContext.xml")
@ComponentScan({ "com.snapdeal.*" })
public class MerchantPanelAppApplication {

   public static void main(String[] args) {
      SpringApplication app = new SpringApplication(MerchantPanelAppApplication.class);
      app.setShowBanner(true);
      app.run(args);
   }
}