/*
 * Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @version 1.0, 24-Sept-2010
 * 
 * @author Ghanshyam
 */
package com.snapdeal.ums.core.cache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.base.utils.DateUtils;

@Cache(name = "properties")
public class UMSPropertiesCache
{
	
	private static final String IMS_USER_ID_BENCHMARK = "ims.userId.benchmark";

    private static final String OMS_WEB_SERVICE_URL = "oms.web.service.url";

    private static final String USE_OMS_WEB_SERVICE = "use.oms.web.service";

    private static final String CATALOG_WEB_SERVICE_URL = "catalog.web.service.url";

    private static final String IPMS_WEB_SERVICE_URL = "ipms.web.service.url";
    
    private static final String IMS_WEB_SERVICE_IP = "ims.web.service.ip";
    
    private static final String IMS_WEB_SERVICE_PORT = "ims.web.service.port";
    
    private static final String IMS_WEB_SERVICE_CLIENT_KEY = "ims.web.service.client.key";
    
    private static final String IMS_WEB_SERVICE_CLIENT_ID = "ims.web.service.client.id";

    private static final String SHIPPING_WEB_SERVICE_URL = "shipping.web.service.url";

    private static final String SHIPPING_SOA_ENABLED = "use.shipping.web.service";

    private static final String S4_WEB_SERVICE_URL = "s4.web.service.url";

    private static final String LOCALITY_WEB_SERVICE_URL = "locality.web.service.url";

    private static final String RELOAD_CACHE_REFERENCE_TIME = "reload.cache.reference.time";

    private static final String RELOAD_CACHE_REFERENCE_TIME_FORMAT = "reload.cache.reference.time.format";

    private static final String RELOAD_CACHE_SELF_ENABLED = "reload.cache.self.enabled";

    private static final String RELOAD_CACHE_CHECK_ENABLED = "reload.cache.check.enabled";

    private static final String RELOAD_CACHE_CHECK_INTERVAL = "reload.cache.check.interval";

    private static final String RELOAD_CACHE_INTERVAL = "reload.cache.interval";

    private static final String SYSTEM_CONFIGURATION_FILE_PATH = "system.configuration.file.path";

    private static final String SYSTEM_CONFIGURATION_DIR_PATH = "system.configuration.dir.path";

    private static final String STATIC_RESOURCES_PATH = "static.resources.path";

    private static final String CONTENT_PATH = "content.path";

    private static final String CONTEXT_PATH = "context.path";

    private static final String MEMCACHED_SERVER_LIST = "memcached.server.list";

    private static final String STATIC_ROOT_DIRECTORY_PATH = "static.root.directory.path";

    private static final String RT_TRACING_THRESHOLD = "responsetime.tracing.threshold";

    private static final String DEFAULT_SHIPPING_TIME = "default.shipping.time";

    private static final String DEFAULT_ESP_ID = "default.esp.id";

    private static final String USER_ADDRESS_COUNT_LIMIT = "user.address.count.limit";

    private static final String USE_OMS_ADDRESS_FLAG = "use.oms.addresses";

    private static final String DEFAULT_SERVER_BEHAVIOUR_CONTEXT="defaultserver.behaviour.context";

    // ACTIVEMQ
    private static final String ACTIVEMQ_URL = "activemq.url";

    private static final String ACTIVEMQ_PORT = "activemq.port";

    private static final String ACTIVEMQ_USERNAME = "activemq.username";

    private static final String ACTIVEMQ_PASSWORD = "activemq.password";

    private static final String MAIL_CLIENT_URL = "mailClient.url";

    private static final String MAX_SDCASH_CREDIT = "max.sdcash.credit";
    private static final String SDWALLET_HISTORY_FETCH_LIMIT = "sdwallet.record.fetch.limit";
    private static final String SDCASH_PROACTIVE_LIMIT = "sdcash.proactive.limit";


    private static final String UMS_CACHE_ENABLED = "ums.cache.enabled";

    // AEROSPIKE
    private static final String AEROSPIKE_IP = "aerospike.ip";
    private static final String AEROSPIKE_PORT = "aerospike.port";
    private static final String AEROSPIKE_CLUSTER = "aerospike.cluster";
    private static final String AEROSPIKE_INCONSISTENT_KEY_EVICTION_ENABLED = "aerospike.inconsistent.key.eviction.enabled";
    private static final String AEROSPIKE_KEY_EVICTION_TRANSACTION_SIZE= "aerospike.eviction.transaction.size";
    //Number of eviction transactions allowed.
    private static final String AEROSPIKE_KEY_EVICTION_TRANSACTION_ITERATION_LIMIT= "aerospike.eviction.transaction.iteration.limit";

    // GRAPHITE
    private static final String GRAPHITE_IS_ENABLED="graphite.enabled";
    private static final String GRAPHITE_IP="graphite.ip";
    private static final String GRAPHITE_PORT="graphite.port";
    private static final String GRAPHITE_REPORTING_INTERVAL="graphite.reporting.interval";
    private static final String MONITORING_ENV="monitoring.env";
    private static final String MONITORING_ENV_SERVER="monitoring.env.server";
    private static final String MONITORING_REPO="monitoring.repo";

    //To uniquely identify each server    
    private static final String SERVER_ID="server.id";
    
    //Amazon S3 properties
    private static final String S3_ACCESS_ID="amazon.s3.accessId";
    private static final String S3_SECRET_KEY="amazon.s3.secretKey";
    private static final String S3_BUCKET_NAME="amazon.s3.bucketName";
    private static final String S3_DIRECTORY_NAME="amazon.s3.bucket.dirName";
    
    public String getS3AccessId()
    {
        if (properties.containsKey(S3_ACCESS_ID)) {
            return properties.getProperty(S3_ACCESS_ID);
        }
        return null;
    }
    
    public String getS3SecretKey()
    {
        if (properties.containsKey(S3_SECRET_KEY)) {
            return properties.getProperty(S3_SECRET_KEY);
        }
        return null;
    }
    
    public String getS3BucketName()
    {
        if (properties.containsKey(S3_BUCKET_NAME)) {
            return properties.getProperty(S3_BUCKET_NAME);
        }
        return null;
    }
    
    public String getS3DirectoryName()
    {
        if (properties.containsKey(S3_DIRECTORY_NAME)) {
            return properties.getProperty(S3_DIRECTORY_NAME);
        }
        return null;
    }
    
	public Integer getAerospikeEvictionTransactionSize() {
		Integer size = 1000;

		if (properties.containsKey(AEROSPIKE_KEY_EVICTION_TRANSACTION_SIZE)) {
			size = Integer.parseInt(properties
					.getProperty(AEROSPIKE_KEY_EVICTION_TRANSACTION_SIZE));
			return size;
		}
		return size;
	}
	
	public Integer getAerospikeEvictionTransactionIterationLimit() {
		Integer size = 1000;

		if (properties.containsKey(AEROSPIKE_KEY_EVICTION_TRANSACTION_ITERATION_LIMIT)) {
			size = Integer.parseInt(properties
					.getProperty(AEROSPIKE_KEY_EVICTION_TRANSACTION_ITERATION_LIMIT));
			return size;
		}
		return size;
	}
	
    
	public boolean isAerospikeInconsistentKeyEvictionEnabled() {
		return "true".equals(properties
				.getProperty(AEROSPIKE_INCONSISTENT_KEY_EVICTION_ENABLED));
	}
	public boolean isGraphiteEnabled() {
		return "true".equals(properties.getProperty(GRAPHITE_IS_ENABLED));
	}
    
	public String getServerId() {
		if (properties.containsKey(SERVER_ID)) {
			return properties.getProperty(SERVER_ID);
		}
		return SERVER_ID;
	}

	public int getSDWalletHIstoryFetchLimit() {

        return Integer.parseInt(properties.getProperty(SDWALLET_HISTORY_FETCH_LIMIT));

    }

    public int getSDCashProactiveLimit() {

        return Integer.parseInt(properties.getProperty(SDCASH_PROACTIVE_LIMIT));

    }

    public Integer getGraphitePort() {
        Integer port=2004;

        if (properties.containsKey(GRAPHITE_PORT)) {
            port=Integer.parseInt(properties.getProperty(GRAPHITE_PORT));
            return port;
        }
        return port;
    }

    public String getGraphiteUrl() {
        if (properties.containsKey(GRAPHITE_IP)) {
            return properties.getProperty(GRAPHITE_IP);
        }
        return GRAPHITE_IP;
    }

    public Integer getGraphiteReportingInterval() {

        Integer graphiteReportingInterval=60;
        if (properties.containsKey(GRAPHITE_REPORTING_INTERVAL)) {

            graphiteReportingInterval=Integer.parseInt(properties.getProperty(GRAPHITE_REPORTING_INTERVAL));
            return graphiteReportingInterval;
        }
        return graphiteReportingInterval;
    }

    public String getMaxSdcashCredit() {
        if (properties.containsKey(MAX_SDCASH_CREDIT)) {
            return properties.getProperty(MAX_SDCASH_CREDIT);
        }
        return MAX_SDCASH_CREDIT;
    }

    public String getActiveMQURL()
    {

        if (properties.containsKey(ACTIVEMQ_URL)) {
            return properties.getProperty(ACTIVEMQ_URL);
        }
        return null;
    }

    public String getActiveMQPort()
    {

        if (properties.containsKey(ACTIVEMQ_PORT)) {
            return properties.getProperty(ACTIVEMQ_PORT);
        }
        return null;
    }

    public String getActiveMQUsername()
    {

        if (properties.containsKey(ACTIVEMQ_USERNAME)) {
            return properties.getProperty(ACTIVEMQ_USERNAME);
        }
        return null;
    }

    public String getActiveMQpassword()
    {

        if (properties.containsKey(ACTIVEMQ_PASSWORD)) {
            return properties.getProperty(ACTIVEMQ_PASSWORD);
        }
        return null;
    }

    private final java.util.Properties properties = new java.util.Properties();

    public String getProperty(String name)
    {
        if(properties.containsKey(name)){
            return properties.getProperty(name);
        }
        return null;
    }

    public void addProperty(String name, String value)
    {

        properties.put(name, value);
    }

    public String getProperty(String name, String defaultValue)
    {

        String value = properties.getProperty(name);
        return value != null ? value : defaultValue;
    }

    public Date getReloadCacheReferenceTime()
    {

        Date date;
        if (properties.containsKey(RELOAD_CACHE_REFERENCE_TIME)) {
            DateFormat df;
            if (properties.containsKey(RELOAD_CACHE_REFERENCE_TIME_FORMAT)) {
                df = new SimpleDateFormat(properties.getProperty(RELOAD_CACHE_REFERENCE_TIME_FORMAT));
            }
            else {
                df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            }
            try {
                date = df.parse(properties.getProperty(RELOAD_CACHE_REFERENCE_TIME));
            }
            catch (ParseException e) {
                date = DateUtils.getCurrentDate();
            }
        }
        else {
            date = DateUtils.getCurrentDate();
        }
        return date;

    }

    public boolean isReloadCacheSelfEnabled()
    {

        return "true".equals(properties.getProperty(RELOAD_CACHE_SELF_ENABLED));
    }

    public boolean isReloadCacheCheckEnabled()
    {

        return "true".equals(properties.getProperty(RELOAD_CACHE_CHECK_ENABLED));
    }

    public long getReloadCacheCheckInterval()
    {

        return Long.parseLong(properties.getProperty(RELOAD_CACHE_CHECK_INTERVAL, "-1"));
    }

    public boolean isUseOMSWebService()
    {

        if (properties.containsKey(USE_OMS_WEB_SERVICE)) {
            return "true".equals(properties.getProperty(USE_OMS_WEB_SERVICE));
        }
        return false;
    }

    public boolean isUseOMSAddresses()
    {

        if (properties.containsKey(USE_OMS_ADDRESS_FLAG)) {
            return "true".equals(properties.getProperty(USE_OMS_ADDRESS_FLAG));
        }
        return false;
    }

    public String getOMSWebServiceURL()
    {

        return properties.getProperty(OMS_WEB_SERVICE_URL);
    }

    public String getCatalogWebServiceURL()
    {

        return properties.getProperty(CATALOG_WEB_SERVICE_URL);
    }

    public String getIPMSWebServiceURL()
    {

        return properties.getProperty(IPMS_WEB_SERVICE_URL);
    }

    public String getShippingWebServiceURL()
    {

        return properties.getProperty(SHIPPING_WEB_SERVICE_URL);
    }

    public boolean isShippingSOAEnabled()
    {

        if (properties.containsKey(SHIPPING_SOA_ENABLED)) {
            return "true".equalsIgnoreCase(properties.getProperty(SHIPPING_SOA_ENABLED));
        }
        return false;
    }

    public long getReloadCacheInterval()
    {

        return Long.parseLong(properties.getProperty(RELOAD_CACHE_INTERVAL, "-1"));
    }

    public String getSystemConfigurationFilePath()
    {

        return properties.getProperty(SYSTEM_CONFIGURATION_FILE_PATH);
    }

    public String getLocalityWebServiceUrl()
    {

        return properties.getProperty(LOCALITY_WEB_SERVICE_URL);
    }

    public String getSystemConfigurationDirectoryPath()
    {

        return properties.getProperty(SYSTEM_CONFIGURATION_DIR_PATH);
    }

    public String getStaticResourcesPath()
    {

        return properties.getProperty(STATIC_RESOURCES_PATH);
    }

    public String getContentPath(String defaultContentPath)
    {

        return properties.containsKey(CONTENT_PATH) ? properties.getProperty(CONTENT_PATH) : defaultContentPath;
    }

    public String getContextPath(String defaultContextPath)
    {

        return properties.containsKey(CONTEXT_PATH) ? properties.getProperty(CONTEXT_PATH) : defaultContextPath;
    }

    public String getMemcacheServerList()
    {

        return properties.getProperty(MEMCACHED_SERVER_LIST);
    }

    public String getStaticContentRootDirectoryPath()
    {

        String staticPath = getProperty(STATIC_ROOT_DIRECTORY_PATH);
        if (staticPath.endsWith("/")) {
            return staticPath;
        }
        else {
            return staticPath + "/";
        }
    }

    public long getResponseTimeTracingThreshold()
    {

        return properties.containsKey(RT_TRACING_THRESHOLD) ? Long.parseLong(properties
                .getProperty(RT_TRACING_THRESHOLD)) : 10;
    }

    public String getS4WebServiceURL()
    {

        return properties.getProperty(S4_WEB_SERVICE_URL);
    }

    public Integer getUserAddressCountLimit()
    {

        return Integer.parseInt(properties.getProperty(USER_ADDRESS_COUNT_LIMIT));
    }

    public Integer getDefaultShippingTime()
    {

        try {
            return properties.containsKey(DEFAULT_SHIPPING_TIME) ? Integer.parseInt(properties
                    .getProperty(DEFAULT_SHIPPING_TIME)) : 1;
        }
        catch (NumberFormatException e) {
            return 1;
        }
    }

    public int getDefaultESP(Integer deaultEspId)
    {

        if (properties.containsKey(DEFAULT_ESP_ID)) {
            try {
                return Integer.parseInt(properties.getProperty(DEFAULT_ESP_ID));
            }
            catch (NumberFormatException e) {
                return deaultEspId;
            }
        }
        else {
            return deaultEspId;
        }
    }

    public String getMailClientUrl() {
        if (properties.containsKey(MAIL_CLIENT_URL)) {
            return properties.getProperty(MAIL_CLIENT_URL);
        }

        return null;
    }


    public String getAerospikeIp() {
        if(properties.containsKey(AEROSPIKE_IP)){
            return properties.getProperty(AEROSPIKE_IP);
        }
        return null;
    }

    public String getAerospikePort() {
        if(properties.containsKey(AEROSPIKE_PORT)){
            return properties.getProperty(AEROSPIKE_PORT);
        }
        return null;
    }

    public String getAerospikeClusterInfo() {
        if(properties.containsKey(AEROSPIKE_CLUSTER)){
            return properties.getProperty(AEROSPIKE_CLUSTER);
        }
        return null;
    }

    public String isUmsCacheEnabled(){
        if(properties.containsKey(UMS_CACHE_ENABLED)){
            return properties.getProperty(UMS_CACHE_ENABLED);
        }
        return null;
    }

    public String getDisabledServicesProfile() {
        if(properties.containsKey(DEFAULT_SERVER_BEHAVIOUR_CONTEXT)){
            return properties.getProperty(DEFAULT_SERVER_BEHAVIOUR_CONTEXT);
        }
        return null;
    }

    public String getMonitoringEnv() {
        if(properties.containsKey(MONITORING_ENV)){
            return properties.getProperty(MONITORING_ENV);
        }

        return null;
    }

    public  String getMonitoringEnvServer() {
        if(properties.containsKey(MONITORING_ENV_SERVER)){
            return properties.getProperty(MONITORING_ENV_SERVER);
        }

        return null;
    }

    public String getMonitoringRepo() {
        if(properties.containsKey(MONITORING_REPO)){
            return properties.getProperty(MONITORING_REPO);
        }
        return null;
    }

	public Integer getImsUserIdBenchmark() {
		
		try{
			return Integer.parseInt(properties.getProperty(IMS_USER_ID_BENCHMARK));
		}
        catch (NumberFormatException e) {
            return null;
        }
		
	}

	public String getIMSWebServiceIP() {
		return properties.getProperty(IMS_WEB_SERVICE_IP);
	}
	
	public String getIMSWebServicePort() {
		return properties.getProperty(IMS_WEB_SERVICE_PORT);
	}
	
	public String getIMSWebServiceClientKey() {
		return properties.getProperty(IMS_WEB_SERVICE_CLIENT_KEY);
	}
	
	public String getIMSWebServiceClientId() {
		return properties.getProperty(IMS_WEB_SERVICE_CLIENT_ID);
	}

}
