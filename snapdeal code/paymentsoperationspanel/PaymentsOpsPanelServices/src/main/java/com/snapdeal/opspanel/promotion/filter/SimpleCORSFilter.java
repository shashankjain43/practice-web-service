package com.snapdeal.opspanel.promotion.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

	private final String REQUEST_ID = "requestId";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		String clientOrigin=(((HttpServletRequest)req).getHeader("origin"));
		response.setHeader("Access-Control-Allow-Origin", clientOrigin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, appName, token, merchantId, emailId, Accept-Encoding");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Expires", "0"); 
		
		// Set request id in session and logs
		HttpServletRequest request = (HttpServletRequest) req;
		String requestId = UUID.randomUUID().toString();
		request.setAttribute(REQUEST_ID, requestId);
		MDC.put(REQUEST_ID, requestId );

/*
 		// Invalidate session after RMS session expiration time.
		HttpSession session = request.getSession();
		long sessionCreationTime = session.getCreationTime();
		if( System.currentTimeMillis() - sessionCreationTime > RMSConstants.sessionExpirationTime ) {
			session.invalidate();
		}
*/
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}


