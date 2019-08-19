package com.snapdeal.ums.aspect;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.metrics.Metrics;

/**
 * Aspect to enable the capturing of metrics for various ums services.
 * 
 * @author lovey
 */
@Configurable
@Aspect
public class MetricsAspectHandler {

//	private static final Logger LOG = LoggerFactory
//			.getLogger(MetricsAspectHandler.class);

	/**
	 * Around advice and pointCut to pick out all the service execution join
	 * points that matches with the below signature
	 * 
	 * @param joinPoint
	 */

	// @Around("execution(@com.snapdeal.ums.aspect.annotation.EnableMonitoring * com.snapdeal.ums..*.*(..))")
	// public Object captureMetricsForServices(ProceedingJoinPoint joinPoint)
	// throws Throwable {
	//
	// return capturingMetrics(joinPoint);
	//
	// }

	@Pointcut("execution(* com.snapdeal.ums.*.*.*.*.*.*.*(..))")
	public void executeSDWalletServiceMethods() {

	}

	@Pointcut("execution(* com.snapdeal.ums.*.*.*.*.*.*(..))")
	public void executeSDWalletDaoMethods() {

	}

	@Pointcut("execution(* com.snapdeal.ums.*.*.*.*.*(..))")
	public void executeServiceAndDaoMethods() {

	}

	@Pointcut("@annotation(com.snapdeal.ums.aspect.annotation.EnableMonitoring)")
	public void isEnableMonitoring() {

	}

	@Around("(executeSDWalletServiceMethods()|| executeServiceAndDaoMethods()|| executeSDWalletDaoMethods()) && isEnableMonitoring()")
	public Object captureMetricsForServices(ProceedingJoinPoint joinPoint)
			throws Throwable {
		return capturingMetrics(joinPoint);
	}

	/**
	 * Method to monitor the service by creating and registering the different
	 * suitable metrics.
	 * 
	 * @param joinPoint
	 */

	private Object capturingMetrics(ProceedingJoinPoint joinPoint)
			throws Throwable {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getName();

		String className = signature.getDeclaringType().getSimpleName();
//		LOG.info("DeclaringType:" + className);
//
//		LOG.info("methodName:" + methodName);
		// final com.yammer.metrics.core.Counter count = (
		// Metrics.getRegistry())
		// .newCounter(getClass(), metricName + "Count");
		// final com.yammer.metrics.core.Meter rate = Metrics.getRegistry()
		// .newMeter(getClass(), metricName + "Meter", "Call",
		// TimeUnit.SECONDS);
		//
		// final com.yammer.metrics.core.Timer timer = Metrics.getRegistry()
		// .newTimer(getClass(), metricName + "Timer",
		// TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
		//
		// // Using different registered metrics to sample the event.
		//
		// com.yammer.metrics.core.TimerContext context = timer.time();
		// count.inc();
		// rate.mark();
		// Object response = joinPoint.proceed();
		// context.stop();
		// LOG.info("Inside metrics aspect handler");

		// Counter count = Metrics.getRegistry().counter(
		// className +"#"+ methodName + ".counter");
		// count.inc();
		// Meter rate = Metrics.getRegistry().meter(
		// className+"#" + methodName + ".rate");
		// rate.getMeanRate();
		// rate.getFifteenMinuteRate();
		// rate.getFiveMinuteRate();
		// rate.getOneMinuteRate();
		// rate.mark();

		final Timer timer = Metrics.getRegistry().timer(
				className + "#" + methodName + ".timer");
		final Timer.Context context = timer.time();
		// final Snapshot snapshot = timer.getSnapshot();
		// snapshot.getMin();
		// snapshot.getMax();
		// snapshot.getMedian();
		// snapshot.get75thPercentile();
		// snapshot.get95thPercentile();
		// snapshot.get99thPercentile();
		try {

			Object actual = joinPoint.proceed();
			// if (actual instanceof ServiceResponse) {
			// if (!((ServiceResponse) actual).isSuccessful()) {
			// count.inc();
			// rate.mark();
			// }
			// }
			return actual;
		} finally {
			context.stop();
		}

	}
}
