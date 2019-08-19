package com.snapdeal.admin.app;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@EnableScheduling
@EnableAspectJAutoProxy
@ImportResource({"classpath:spring/application-context*.xml",
		"classpath:spring/security-applicationContext.xml"})
@ComponentScan({"com.snapdeal.admin", "com.snapdeal.ums",
		"com.snapdeal.base.transport"})
@SpringBootApplication
@Slf4j
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AdminApplication.class);
		application.setShowBanner(false);
		application.run(args);
	}

	/*
	 * @Bean public ServletContextInitializer initializer() { return new
	 * ServletContextInitializer() {
	 * 
	 * @Override public void onStartup(ServletContext servletContext) throws
	 * ServletException { servletContext .setAttribute("cache",
	 * CacheManager.getInstance()); servletContext.setAttribute("path", new
	 * PathResolver()); servletContext.setAttribute("dateUtils", new
	 * DateUtils());
	 * 
	 * } }; }
	 */
	@Bean
	public UrlRewriteFilter getUrlRewriteFilter() {
		UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();
		return urlRewriteFilter;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("/static/tiles/tiles.xml");
		configurer.setCheckRefresh(true);
		return configurer;
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

/*	@Bean
	public UrlBasedViewResolver getUrlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setViewClass(TilesView.class);
		return urlBasedViewResolver;
	}
*/
/*	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}
*/
	

}
