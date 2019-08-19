package com.snapdeal.merchant.rest.interceptor;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MDCRequestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Random random = new Random();
		Long requestId = random.nextLong();
		requestId = requestId < 0l ? (requestId * -1) : requestId ;

		try {
			MDC.put("requestId", requestId.toString());
		} catch(Exception e) {
			log.error("could not put request id in mdc");
		}
		return true;

	}

}
