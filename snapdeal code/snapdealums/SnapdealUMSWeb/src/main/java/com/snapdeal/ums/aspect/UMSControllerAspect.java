package com.snapdeal.ums.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.base.services.request.context.RequestContextProcessorFactory;
import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.base.transport.service.ITransportService.Protocol;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.core.sro.user.UserSRO;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.server.services.IServerBehaviourContextService;

/**
 * Aspect to help track the caller of UMS SRVs
 * 
 * @author ashish.saxena@snapdeal.com
 * 
 */
@Aspect
public class UMSControllerAspect implements Ordered {

	private static final Logger LOG = LoggerFactory
			.getLogger(UMSControllerAspect.class);

	@Autowired
	private RequestContextProcessorFactory requestContextProcessorFactory;

	@Autowired
	private IServerBehaviourContextService serverBehaviourContextService;

	// Scoping designator
	@Pointcut("execution(* com.snapdeal.ums.*.*.*.*(..))")
	public void methodPointcutWebControllers() {
	}

	// Scoping designator
	@Pointcut("execution(* com.snapdeal.ums.*.*.*.*(..))")
	public void methodPointcutAdminControllers() {
	}

	// Scoping designator
	@Pointcut("execution(* com.snapdeal.ums.admin.web.controller.*.*(..))")
	public void methodPointcutAdminWebControllers() {
	}

	// Contextual designator
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void isRequestMapping() {
	}

	// Contextual designator
	@Pointcut("@annotation(javax.ws.rs.Path)")
	public void isPath() {
	}

	@Around("(methodPointcutWebControllers() || methodPointcutAdminControllers() || methodPointcutAdminWebControllers()) && (isRequestMapping() || isPath())")
	public Object forwardRequestContext(ProceedingJoinPoint pjp)
			throws Throwable {
		ServiceResponse response = null;
		String uri = null;

		try {

			ServiceRequest serviceRequest = null;
			boolean isServiceURLDisabled = false;
			Protocol protocol = Protocol.PROTOCOL_PROTOSTUFF;
			for (Object arg : pjp.getArgs()) {

				if (arg instanceof HttpServletRequest) {

					HttpServletRequest httpServletRequest = (HttpServletRequest) arg;
					uri = httpServletRequest.getRequestURI();
					isServiceURLDisabled = serverBehaviourContextService
							.isServiceURLDisabled(uri);
				}

				else if (arg instanceof ServiceRequest) {
					serviceRequest = (ServiceRequest) arg;
					protocol = serviceRequest.getResponseProtocol();
					if (protocol == null) {
						protocol = serviceRequest.getRequestProtocol();

					}
					String context = "unknownCaller";
					if (serviceRequest != null) {
						RequestContextSRO contextSRO = serviceRequest
								.getContextSRO();
						if (null != contextSRO) {
							context = contextSRO.toString();
						}
						if (context != null) {
							MDC.put("RqCntxt", context);
						}

					}

				}
			}

			if (isServiceURLDisabled) {
				LOG.info(uri + " is disabled! returning 503!");
				MethodSignature signature = (MethodSignature) pjp
						.getSignature();
				Method method = signature.getMethod();
				Class returnType = method.getReturnType();
				response = (ServiceResponse) returnType.getConstructor(null)
						.newInstance(null);
				response.setSuccessful(false);
				response.addValidationError(new ValidationError(
						ErrorConstants.SERVICE_DISABLED.getCode(),
						ErrorConstants.SERVICE_DISABLED.getMsg()+uri));
				response.setMessage(ErrorConstants.SERVICE_DISABLED.getMsg()+uri);

				/*
				 * 503 Service Unavailable
				 * 
				 * The service is currently unavailable (because it is
				 * overloaded or down for maintenance or disabled). Generally,
				 * this is a temporary state.
				 */
				response.setCode("503");

				response.setProtocol(protocol);
				return response;

			} else {
				return pjp.proceed();

			}

		} catch (Exception e) {
			LOG.info("Error@aspect", e);

		}

		finally {
			MDC.remove("RqCntxt");
		}
		return response;
	}

	/**
	 * This method will return a value such that this aspect has a lower
	 * precedence than other aspects like trace-interceptor
	 */
	@Override
	public int getOrder() {
		return Integer.MAX_VALUE - 1;
	}

	public static void main(String[] args) {
		UserSRO sro = new UserSRO();

		if (sro instanceof Object) {
			System.out.println("It is an object");
		}
	}

}
