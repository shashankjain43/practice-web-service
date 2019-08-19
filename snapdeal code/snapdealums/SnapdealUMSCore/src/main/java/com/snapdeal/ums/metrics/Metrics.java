package com.snapdeal.ums.metrics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
//import com.yammer.metrics.core.MetricsRegistry;

/**
 * Class to create a singleton instance of Metric Registry used for registering
 * different metrics throughout the application
 * 
 * @author lovey
 */

public class Metrics {

	private static final Logger LOG = LoggerFactory.getLogger(Metrics.class);
	public static final String REGISTRY_ATTRIBUTE = "com.codahale.metrics.servlet.InstrumentedFilter.registry";
	private final static MetricRegistry metricsRegistry = new MetricRegistry();

	// @Autowired
	// private static MetricsRegistry metricsRegistry;
	//
	public static MetricRegistry getRegistry() {
		return metricsRegistry;
	}

//	   static {
//		       metricsRegistry.register(name("jvm", "gc"), new GarbageCollectorMetricSet());
//		      metricsRegistry.register(name("jvm", "memory"), new MemoryUsageGaugeSet());
//		     metricsRegistry.register(name("jvm", "thread-states"), new ThreadStatesGaugeSet());
//		      metricsRegistry.register(name("jvm", "fd", "usage"), new FileDescriptorRatioGauge());
//		   }

	// metricsRegistry.newCounter(Metrics.class,"GetUserByEmailCount");
	// (metricsRegistry).register(name("jvm", "gc"), new
	// GarbageCollectorMetricSet());
	// metricsRegistry.register(name("jvm", "memory"), new
	// MemoryUsageGaugeSet());
	// metricsRegistry.register(name("jvm", "thread-states"), new
	// ThreadStatesGaugeSet());
	// metricsRegistry.register(name("jvm", "fd", "usage"), new
	// FileDescriptorRatioGauge());
}
