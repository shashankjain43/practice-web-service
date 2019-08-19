package com.snapdeal.opspanel.promotion.aspect;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.dao.UsersDao;
import com.snapdeal.opspanel.promotion.entity.UsersEntity;
import com.snapdeal.opspanel.rms.service.TokenService;

@Aspect
@Component
public class SuperUserOnlyMethodAspect {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsersDao usersDao;

	@Pointcut("execution(public * com.snapdeal.opspanel.promotion.rp.controller.*.*(..)) && @annotation(com.snapdeal.opspanel.promotion.annotation.SuperUserOnly)")
	private void classLevelExtraction() {

	}

	@Before("classLevelExtraction()")
	public void IsSuperUser(JoinPoint joinPoint)
			throws OpsPanelException, IOException {

		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String token = httpServletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		UsersEntity usersEntity= usersDao.getEntity(emailId);
		Boolean status = Boolean.FALSE;
		
		if(usersEntity.getUser_role().toLowerCase().contains("superuser")) {
			status= Boolean.TRUE;
		}
		
		if (status == Boolean.FALSE) {
			throw new OpsPanelException("MT-5006",
					"Not authorized to access this api");
		}
	}
}
