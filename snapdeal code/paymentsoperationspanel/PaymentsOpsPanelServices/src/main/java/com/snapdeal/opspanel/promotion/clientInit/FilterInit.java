package com.snapdeal.opspanel.promotion.clientInit;


import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.snapdeal.opspanel.promotion.filter.SimpleCORSFilter;


public class FilterInit  implements WebApplicationInitializer {



	@Override 
	public void onStartup(ServletContext servletContext)
			throws ServletException {

        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", SimpleCORSFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
		
	}
}
