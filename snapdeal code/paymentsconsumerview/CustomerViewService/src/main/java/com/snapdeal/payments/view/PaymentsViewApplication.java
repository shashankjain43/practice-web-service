package com.snapdeal.payments.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource({ "file:${config.path}/api.properties", "file:${config.path}/app.properties",
         "file:${config.path}/db.properties", "file:${config.path}/log.properties", 
         "file:${config.path}/vault.properties"})
@EnableScheduling
@EnableTransactionManagement
@ImportResource("classpath*:spring/application-context.xml")
@ComponentScan({ "com.snapdeal.ims.*", "com.snapdeal.payments",
         "com.snapdeal.payments.view" })
@EnableAutoConfiguration(exclude = { DataSourceTransactionManagerAutoConfiguration.class,
         DataSourceAutoConfiguration.class })
@SpringBootApplication
public class PaymentsViewApplication {

   public static void main(String args[]) {
      SpringApplication application = new SpringApplication(PaymentsViewApplication.class);
      application.setShowBanner(false);

      application.run(args);
   }
}