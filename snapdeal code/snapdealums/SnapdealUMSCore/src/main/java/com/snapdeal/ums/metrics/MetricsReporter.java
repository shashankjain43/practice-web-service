package com.snapdeal.ums.metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
//import com.brightcove.metrics.reporting.GraphitePickleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.metrics.Metrics;

/**
 * Class to enable reporting the metrics to graphite server.
 * 
 * @author lovey
 */

public class MetricsReporter {

	private UMSPropertiesCache umsPropertyCache;

	private static final Logger LOG = LoggerFactory
			.getLogger(MetricsReporter.class);

	private GraphiteReporter reporter;

	public void reportToGraphite() {

		umsPropertyCache = CacheManager.getInstance().getCache(
				UMSPropertiesCache.class);

		String reportingPrefix = umsPropertyCache.getMonitoringEnv()+"."
				+ umsPropertyCache.getMonitoringEnvServer()+"."
				+ umsPropertyCache.getMonitoringRepo()+".";

		LOG.info("Reporting Prefix : {}", reportingPrefix);
		
		// GraphitePickleReporter.enable(Metrics.getRegistry(),
		// upc.getGraphiteReportingInterval(), TimeUnit.MILLISECONDS,
		// upc.getGraphiteUrl(), upc.getGraphitePort(), reportingPrefix,
		// 5000);

		final Graphite graphite = new Graphite(new InetSocketAddress(
				umsPropertyCache.getGraphiteUrl(),
				umsPropertyCache.getGraphitePort()));

		reporter = GraphiteReporter.forRegistry(Metrics.getRegistry())
				.prefixedWith(reportingPrefix).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.filter(MetricFilter.ALL).build(graphite);
		
		LOG.info("writing to graphite");
		// GraphitePickleReporter.enable(Metrics.getRegistry(),
		// upc.getGraphiteReportingInterval(), TimeUnit.MILLISECONDS,
		// upc.getGraphiteUrl(),upc.getGraphitePort() , reportingPrefix, 5000);

		reporter.start(umsPropertyCache.getGraphiteReportingInterval(),
				TimeUnit.SECONDS);
		LOG.info("Reporting to graphite finished");
		// reporter.start(upc.getGraphiteReportingInterval(), TimeUnit.SECONDS);
	}

	public void stopReporting() {
		reporter.stop();
	}

}
