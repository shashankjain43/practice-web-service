package com.snapdeal.ums.admin.startup;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.exception.InvalidConfigurationException;
import com.snapdeal.base.exception.InvalidFormatException;
import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.memcached.service.IMemcachedService;
import com.snapdeal.base.startup.service.IBaseStartupService;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.PaginationHelper;
import com.snapdeal.base.utils.SystemPropertyConfigurationUtils;
import com.snapdeal.catalog.base.model.GetAllCityListRequest;
import com.snapdeal.catalog.base.model.GetAllZoneListRequest;
import com.snapdeal.catalog.base.sro.CitySRO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.catalog.client.service.ICatalogClientService;
import com.snapdeal.ims.utils.ClientDetails;
import com.snapdeal.ipms.client.service.IIPMSClientService;
import com.snapdeal.locality.client.service.ILocalityClientService;
import com.snapdeal.mail.client.service.exceptions.InitializationException;
import com.snapdeal.mail.client.service.impl.EmailSender;
import com.snapdeal.oms.services.IOMSClientService;
import com.snapdeal.oms.services.IPaymentClientService;
import com.snapdeal.s4.client.service.S4ClientService;
import com.snapdeal.search.services.ISearchClientService;
import com.snapdeal.shipping.service.IShippingClientService;
import com.snapdeal.ums.admin.task.ITaskService;
import com.snapdeal.ums.cache.AccessControlCache;
import com.snapdeal.ums.cache.CitiesCache;
import com.snapdeal.ums.cache.EmailTemplateCache;
import com.snapdeal.ums.cache.LocalitiesCache;
import com.snapdeal.ums.cache.RoleCache;
import com.snapdeal.ums.cache.SmsTemplateCache;
import com.snapdeal.ums.cache.ZonesCache;
import com.snapdeal.ums.cache.services.IUMSCacheService;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.AccessControl;
import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.SmsTemplate;
import com.snapdeal.ums.core.entity.UMSProperty;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.dao.accesscontrol.IAccessControlDao;
import com.snapdeal.ums.dao.common.IStartupDao;
import com.snapdeal.ums.server.services.IHandlingUnavailableExtService;
import com.snapdeal.ums.server.services.impl.SubsidierySDCashBackService;
import com.snapdeal.ums.server.services.impl.HandlingUnavailableExtService.ExternalAppName;
import com.snapdeal.ums.cache.EmailTemplateCache;
import com.snapdeal.ums.cache.SmsTemplateCache;
import com.snapdeal.ums.cache.ZonesCache;
import com.snapdeal.ums.services.serverinfo.IServerInfoService;

@Service("umsAdminStartupService")
@Transactional
public class UMSAdminStartupServiceImpl implements IAdminStartupService, ApplicationContextAware  {

    @Autowired
    private IHandlingUnavailableExtService handleUnavailableExtServices;

    @Autowired
    private IMemcachedService memcachedService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private TaskScheduler reloadCacheScheduler;

    @Autowired
    private IStartupDao startupDao;

    @Autowired
    private IBaseStartupService baseStartupService;

    @Autowired
    private ILocalityClientService localityClientService;

    @Autowired
    private ICatalogClientService catalogClientService;

    @Autowired
    private IOMSClientService omsClientService;

    @Autowired
    private IShippingClientService shippingClientService;

    @Autowired
    private IServerInfoService serverInfoService;

    @Autowired
    private ISearchClientService searchClientService;

    @Autowired
    private IIPMSClientService ipmsClientService;

    @Autowired
    private IPaymentClientService paymentClientService;

    private ApplicationContext applicationContext;

    @Autowired
    private IAccessControlDao accessControldao;
    @Autowired
    private S4ClientService s4ClientService;

    @Autowired
    private IUMSCacheService umsCacheService;
    
    @Autowired
    private SubsidierySDCashBackService subsidieryCashBackService;

    private static final Logger    LOG = LoggerFactory.getLogger(UMSAdminStartupServiceImpl.class);
    
    private boolean isBusy = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
    
    public synchronized boolean isBusy() {
		return isBusy;
	}

	public synchronized void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

    @Override
    public void loadAll() throws Exception {
    	
    	try {
            loadUMSProperties();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadUMSProperties()", e);
        }
    	
    	try {
    		loadRoles();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadRoles()", e);
        }
    	
    	try {
    		loadAccessControls();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadAccessControls()", e);
        }
    	
    	try {
    		loadMemcachedService();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadMemcachedService()", e);
        }
    	
    	try {
    		loadUMSCacheService();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadUMSCacheService();", e);
        }
    	
    	try {
    		loadEmailTemplates();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadEmailTemplates();", e);
        }
    	
    	try {
    		loadMailClient();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadMailClient()", e);
        }
    	
    	try {
    		loadEmailChannels();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadMailClient()", e);
        }
    	
    	try {
    		loadEmailDomains();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadEmailDomains()", e);
        }
    	
    	try {
    		loadSmsChannels();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadSmsChannels();", e);
        }
    	
    	try {
    		loadSmsTemplates();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadSmsTemplates();", e);
        }
    	
    	try {
    		loadS4ClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadS4ClientConfig();", e);
        }
    	
    	try {
    		loadCatalogClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadCatalogClientConfig()", e);
        }
    	
    	try {
    		loadOMSClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadOMSClientConfig();", e);
        }
    	
    	try {
    		loadShippingClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadShippingClientConfig();;", e);
        }
    	
    	try {
    		loadLocalityClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadLocalityClientConfig()", e);
        }
    	
    	try {
    		loadZonesAndCities();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadZonesAndCities()", e);
        }
    	
    	try {
    		loadLocalities();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadLocalities();", e);
        }
    	
    	try {
    		loadIPMSClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadIPMSClientConfig();", e);
        }
    	
    	// ---------------IMS---------------------
        LOG.info("---- Going to intialize IMS client ----");
        try {
            LOG.info("Will start with loadIMSClientConfig() -");
            loadIMSClientConfig();
        } catch (Exception e) {
            LOG.error("Something went wrong in initializing IMS config()", e);
        }
    	
    	try {
    		initializeVelocityLogger();
        } catch (Exception e) {
            LOG.error("Something went wrong in initializeVelocityLogger()", e);
        }
    	
    	try {
    		loadAdminTasks();
        } catch (Exception e) {
            LOG.error("Something went wrong in loadAdminTasks()", e);
        }
        
        LOG.info("===== Initiate processing the promotion file from SD subsideries =====");
        try {
        	processPromotionFilesFromSubsideries();
        } catch (Exception e) {
            LOG.error("Something went wrong in processPromotionFilesFromSubsideries()", e);
        }
        LOG.info("===== Finished processing of promotion file from SD subsideries =====");
    }
    
    /**
     * This will process the promotion file uploaded to S3 by SD subsideries(like Freecharge)
     */
	private void processPromotionFilesFromSubsideries() throws Exception {
		try{
			if(isBusy){
				LOG.info("Alredy processing the promotion file from SD subsideries, "
						+ "skipping this iteration");
				return;
			}
			setBusy(true);
			subsidieryCashBackService.processFiles();
		}catch(Exception e){
			LOG.error("Something went wrong in processPromotionFilesFromSubsideries");
			throw e;
		}finally{
			setBusy(false);
		}
		
	}

    @Override
    public void loadAccessControls() {
        LOG.info("Loading Access Controls...");
        AccessControlCache cache = new AccessControlCache();
        for (AccessControl accessControl : accessControldao
                .getAllAccessControls()) {
            cache.addAccessControls(accessControl);
        }
        CacheManager.getInstance().setCache(cache);
        LOG.info("Access Controls Loaded Successfully...");
    }

    private void loadAdminTasks() throws Exception {
        LOG.info("Loading tasks...");
        taskService.loadTasks();
        taskService.startTasks();
        LOG.info("Loaded tasks SUCCESSFULLY!");

    }

    @Override
    public void loadRoles() {
        LOG.info("Loading Roles...");
        List<Role> roles = startupDao.getAllRoles();
        RoleCache roleCache = new RoleCache();
        for (Role role : roles) {
            roleCache.addRole(role);
        }
        CacheManager.getInstance().setCache(roleCache);
        LOG.info("Loaded Roles SUCCESSFULLY!");

    }

    @Override
    public void loadAllAtStartup() throws Exception {
        serverInfoService.updateReloadRequired("all");
        loadCache(this, serverInfoService, "all");
        serverInfoService.updateServerAtStartup();
        LOG.info("after load all at startup completed");
    }

    @Override
    public void loadOMSClientConfig() {
        LOG.info("Loading OMS Client Config..");
        String baseURL = CacheManager.getInstance()
                .getCache(UMSPropertiesCache.class).getOMSWebServiceURL();
        omsClientService.setWebServiceBaseURL(baseURL);
        LOG.info("OMS Client Config loaded SUCCESSFULLY...");
    }

    public void loadCatalogClientConfig() {
        LOG.info("Loading Catalog Client Config..");
        String baseURL = CacheManager.getInstance()
                .getCache(UMSPropertiesCache.class).getCatalogWebServiceURL();
        catalogClientService.setWebServiceBaseURL(baseURL);
        LOG.info("Catalog Client Config loaded SUCCESSFULLY");

    }

    @Override
    public void loadShippingClientConfig() {
        LOG.info("Loading Shipping Client Config...");
        String baseURL = CacheManager.getInstance()
                .getCache(UMSPropertiesCache.class).getShippingWebServiceURL();
        shippingClientService.setShippingWebServiceURL(baseURL);
        LOG.info("Shipping Client Config loaded SUCCESSFULLY...");
    }

    @Override
    public void loadIPMSClientConfig() {
        LOG.info("Loading IPMS Client Config..");
        String baseURL = CacheManager.getInstance()
                .getCache(UMSPropertiesCache.class).getIPMSWebServiceURL();
        ipmsClientService.setWebServiceBaseURL(baseURL);
        LOG.info("IPMS Client Config loaded SUCCESSFULLY");
    }
    
    private void loadIMSClientConfig() {
		
    	LOG.info("Loading IMS Client Config..");
        String ip = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getIMSWebServiceIP();
        String port= CacheManager.getInstance().getCache(UMSPropertiesCache.class).getIMSWebServicePort();
        String clientKey = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getIMSWebServiceClientKey();
        String clientId = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getIMSWebServiceClientId();
        try {
			ClientDetails.init(ip, port, clientKey, clientId);
			LOG.info("IMS Client Config loaded SUCCESSFULLY");
		} catch (Exception e) {
			LOG.error("Something went wrong in IMS ClientDetails.init()", e);
		}
	}

    @Override
    public void loadLocalityClientConfig() {
        LOG.info("Loading Locality Client Config..");
        String baseURL = CacheManager.getInstance()
                .getCache(UMSPropertiesCache.class).getLocalityWebServiceUrl();
        localityClientService.setWebServiceBaseURL(baseURL);
        LOG.info("Locality Client Config loaded SUCCESSFULLY");
    }

    @Override
    public void loadZonesAndCities() {
        LOG.info("Loading Cities...");
        final CitiesCache citiesCache = new CitiesCache();
        PaginationHelper<CitySRO> cityPaginator = new PaginationHelper<CitySRO>(100) {
            protected List<CitySRO> moreResults(int start, int pageSize) {
                try {
                    return localityClientService.getAllCityList(new GetAllCityListRequest(start, pageSize)).getCitySRO();
                } catch (Exception e) {
                    LOG.error("Unable to load cities due to: ", e);
                    //throw new RuntimeException("Unable to load cities", e);
                    return null;
                }
            }

            protected void process(List<CitySRO> cities, int pageIndex) {
                int count = 0;
                if(cities!=null){
                    for (CitySRO city : cities) {
                        city.setPageUrl(city.getPageUrl().toLowerCase());
                        citiesCache.addCity(city);
                        count++;
                    }
                }
                LOG.info("Page: {}, Number of cities loaded: {}", pageIndex, count);
            }
        };

        cityPaginator.paginate();

        LOG.info("Loading Zones...");
        final ZonesCache zonesCache = new ZonesCache();
        PaginationHelper<ZoneSRO> zonePaginator = new PaginationHelper<ZoneSRO>(100) {
            protected List<ZoneSRO> moreResults(int start, int pageSize) {
                try {
                    return localityClientService.getAllZoneList(new GetAllZoneListRequest(start, pageSize)).getZoneSROs();
                } catch (Exception e) {
                    LOG.error("Unable to load Zones due to: ", e);
                    return null;
                }
            }

            protected void process(List<ZoneSRO> zones, int pageIndex) {
                int count = 0;
                if(zones!=null){
                    for (ZoneSRO zone : zones) {
                        zone.getCity().setPageUrl(zone.getCity().getPageUrl().toLowerCase());
                        zonesCache.addZone(zone);
                        count++;
                    }
                }
                LOG.info("Page: {}, Number of zones loaded: {}", pageIndex, count);
            }
        };
        zonePaginator.paginate();

        boolean isCitiesCacheNotNullNotEmpty=false;
        if(citiesCache != null && !citiesCache.getCities().isEmpty()){
            isCitiesCacheNotNullNotEmpty=true;
            CacheManager.getInstance().setCache(citiesCache);
            LOG.info("Loaded Cities... SUCCESSFULLY");
        }

        boolean isZonesCacheNotEmptyNotNull=false;
        if(zonesCache!= null && !zonesCache.getZones().isEmpty()){
            CacheManager.getInstance().setCache(zonesCache);
            LOG.info("Loaded Zones... SUCCESSFULLY");
            isZonesCacheNotEmptyNotNull = true;
        }

        if(isCitiesCacheNotNullNotEmpty && isZonesCacheNotEmptyNotNull){
            //Enable ext services temorarily
            LOG.info("Enabling CaMS dependent services..");
            handleUnavailableExtServices.enableTemporarilyDisabledServices(ExternalAppName.CAMS);
        }else{
            LOG.info("Disabling CaMS dependent services..");
            handleUnavailableExtServices.temporarilyDisableServices(ExternalAppName.CAMS);
        }
    }


    public void loadUMSCacheService(){
        UMSPropertiesCache properties = CacheManager.getInstance().getCache(UMSPropertiesCache.class);
        LOG.info("UMSCache enabled ? : " + properties.isUmsCacheEnabled());
        if(Boolean.parseBoolean(properties.isUmsCacheEnabled())){
            umsCacheService.evictInConsistentKeys();

            umsCacheService.setEnabled(true);
            //umsCacheService.connectCacheClient(properties.getAerospikeIp(), Integer.parseInt(properties.getAerospikePort()));
            umsCacheService.connectCacheCluster(properties.getAerospikeClusterInfo());
            umsCacheService.evictInConsistentKeys();

        }else{
            umsCacheService.setEnabled(false);
            LOG.warn("UMS Cache is disabled");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadLocalities() {
        LOG.info("Loading Localities...");
        LocalitiesCache cache = new LocalitiesCache();
        List<Locality> localities = startupDao.getAllLocalities();
        for (Locality locality : localities) {
            cache.addLocality(locality);
        }
        CacheManager.getInstance().setCache(cache);
        LOG.info("Localities loaded SUCCESSFULLY...");
    }

    @Override
    public void loadMemcachedService() throws Exception {

        UMSPropertiesCache properties = CacheManager.getInstance().getCache(
                UMSPropertiesCache.class);
        memcachedService.initialize(properties.getMemcacheServerList());
        LOG.info("Loaded memcached clients... SUCCESSFULLY");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadUMSProperties() {
        LOG.info("Loading System Properties...");
        List<UMSProperty> properties = startupDao.getUMSProperties();
        UMSPropertiesCache propertiesCache = new UMSPropertiesCache();
        for (UMSProperty property : properties) {
            propertiesCache
            .addProperty(property.getName(), property.getValue());
        }

        CacheManager.getInstance().setCache(propertiesCache);
        loadSystemFileProperties();
        LOG.info("Loaded System Properties... SUCCESSFULLY");
    }

    @Override
    public void loadSystemFileProperties() {
        if (CacheManager.getInstance().getCache(UMSPropertiesCache.class) != null) {
            String fileName = CacheManager.getInstance()
                    .getCache(UMSPropertiesCache.class)
                    .getSystemConfigurationFilePath();
            String configurationPath = CacheManager.getInstance()
                    .getCache(UMSPropertiesCache.class)
                    .getSystemConfigurationDirectoryPath();
            try {
                loadSystemFilePropertiesCache(configurationPath, fileName);
            } catch (InvalidFormatException e) {
                LOG.error("System configuration file is in wrong format...", e);
            } catch (InvalidConfigurationException e) {
                LOG.error(
                        "System configuration file is not correctly configured...",
                        e);
            } catch (IllegalArgumentException e) {
                LOG.error("System configuration file not present... Properties will only be loaded from DB...");
            }
        } else {
            LOG.warn("System Properties not loaded...");
        }
    }

    @Override
    public void loadSystemFilePropertiesCache(String configPath, String fileName)
            throws InvalidFormatException, InvalidConfigurationException,
            IllegalArgumentException {
        String file = configPath + fileName;
        LOG.info("Loading System Properties Cache from file {}....", file);
        Map<String, Object> map = SystemPropertyConfigurationUtils
                .getMaps(file);

        // Update all the scalar values to SystemPropertiesCache
        UMSPropertiesCache spc = CacheManager.getInstance().getCache(
                UMSPropertiesCache.class);
        if (spc != null) {
            String value = null;
            for (String key : map.keySet()) {
                if (map.containsKey(key.toLowerCase())) {
                    if (!(map.get(key.toLowerCase()) instanceof Collection<?>)) {
                        value = (String) map.get(key.toLowerCase());
                    } else {
                        throw new InvalidConfigurationException(
                                "The property passed is a collection");
                    }
                }
                if (value != null) {
                    spc.addProperty(key, value);
                }
            }
        }
        LOG.info("System Properties Cache from file {} loaded succesfully ...",
                file);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadEmailTemplates() {
        LOG.info("Loading email templates...");
        List<EmailTemplate> templates = startupDao.getEmailTemplates();
        EmailTemplateCache cache = new EmailTemplateCache();
        for (EmailTemplate template : templates) {
            cache.addEmailTemplate(template);
        }
        CacheManager.getInstance().setCache(cache);
        LOG.info("Loaded email templates... SUCCESSFULLY");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadEmailChannels() throws InstantiationException,
    IllegalAccessException, ClassNotFoundException {
        LOG.info("Loading email channels...");
        baseStartupService.loadEmailChannels();
        LOG.info("Loaded email channels... SUCCESSFULLY");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadEmailDomains() {
        LOG.info("Loading email domains...");
        baseStartupService.loadEmailDomains();
        LOG.info("Loaded email domains... SUCCESSFULLY");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadSmsChannels() {
        LOG.info("Loading sms channels...");
        baseStartupService.loadSmsChannels();
        LOG.info("Loaded sms channels... SUCCESSFULLY");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadSmsTemplates() {
        LOG.info("Loading sms templates...");
        List<SmsTemplate> templates = startupDao.getSmsTemplates();
        SmsTemplateCache cache = new SmsTemplateCache();
        for (SmsTemplate template : templates) {
            cache.addSmsTemplate(template);
        }
        CacheManager.getInstance().setCache(cache);
        LOG.info("Loaded sms templates... SUCCESSFULLY");
    }

    @Override
    public void initReloadCache() {
        UMSPropertiesCache cache = CacheManager.getInstance().getCache(
                UMSPropertiesCache.class);
        if (cache.isReloadCacheSelfEnabled()) {
            long interval = cache.getReloadCacheInterval();
            Date refTime = cache.getReloadCacheReferenceTime();
            Date currTime = new Date();
            long kickoff;

            if (interval == -1) {
                interval = Constants.DEFAULT_RELOAD_CACHE_INTERVAL;
            }

            if (currTime.getTime() <= refTime.getTime()) {
                kickoff = refTime.getTime();
            } else {
                kickoff = DateUtils
                        .getNextInterval(currTime, refTime, interval);
            }

            reloadCacheScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    reloadCachePeriodic();
                }
            }, new Date(kickoff), interval);

            if (cache.isReloadCacheCheckEnabled()) {
                long checkInterval = cache.getReloadCacheCheckInterval();
                if (checkInterval == -1) {
                    checkInterval = Constants.DEFAULT_RELOAD_CACHE_CHECK_INTERVAL;
                }

                reloadCacheScheduler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        checkReloadRequired();
                    }
                }, checkInterval);

            }
        }
    }

    private void reloadCachePeriodic() {
        try {
            IServerInfoService server = (IServerInfoService) applicationContext
                    .getBean("serverInfoService");
            server.updateReloadRequired("all");
            reloadCache("all");
        } catch (Exception e) {
            LOG.info("Self Reloading the Cache - Failed");
            e.printStackTrace();
        }

    }

    private void checkReloadRequired() {
        IServerInfoService serverInfo = (IServerInfoService) applicationContext
                .getBean("serverInfoService");
        if (serverInfo.getServerInfo() != null) {
            if (serverInfo.getServerInfo().isReloadRequired()) {
                LOG.info("Cache Reload is required for: "
                        + serverInfo.getServerInfo().getReloadRequiredFor());
                try {
                    reloadCache(serverInfo.getServerInfo()
                            .getReloadRequiredFor());
                } catch (Exception e) {
                    LOG.info("Self Reloading the Cache - Failed");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void reloadCache(String type) throws Exception {
        IAdminStartupService startup = (IAdminStartupService) applicationContext
                .getBean("umsAdminStartupService");
        IServerInfoService server = (IServerInfoService) applicationContext
                .getBean("serverInfoService");
        loadCache(startup, server, type);
    }

    private void loadCache(IAdminStartupService startup,
            IServerInfoService server, String type) throws Exception {
        LOG.info("Self Reloading the Cache - START");

        startup.loadAll();
        LOG.info("Self Reloading the Cache - Successful");
        server.updateServer(type);
    }

    @Override
    public void initializeVelocityLogger() {
        LOG.info("Initializing Velocity Logger...");
        Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.Log4JLogChute");
        Velocity.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        LOG.info("Velocity Logger Initialized...");
    }

    @Override
    public void loadS4ClientConfig() {
        UMSPropertiesCache cache = CacheManager.getInstance().getCache(
                UMSPropertiesCache.class);
        String baseURL = cache.getS4WebServiceURL();
        LOG.info("Loading S4 WebService URL: {}", baseURL);
        s4ClientService.setWebServiceBaseURL(baseURL);
        LOG.info("Loaded S4 WebService URL Successfully ...");
    }

    /**
     * Loading the mail client from cache and initializing to emailSender
     */
    private void loadMailClient() {
        LOG.info("initializing mail client ....");
        try {
            UMSPropertiesCache cache = CacheManager.getInstance().getCache(
                    UMSPropertiesCache.class);
            String mailClientURL = cache.getMailClientUrl();
            EmailSender.init("ums", mailClientURL);
        } catch (InitializationException e) {
            if (e.getCause() instanceof InstanceAlreadyExistsException) {
                LOG.info("Mail client instance already exists!");
            } else{
                LOG.error("error while initializing mail client", e);
            }
        }
        // When email client is already initialized and is again requested to,
        // it responds with IllegalStateException stating "EmailSender is
        // already
        // initialized..."
        // For this particular scenario, instead of printing out stack trace,
        // let us display the crux of it - in a graceful statement.
        catch (java.lang.IllegalStateException illegalStateException) {
            if (illegalStateException.getMessage().contains(
                    "EmailSender already initialized")) {
                LOG.info("EmailSender is already initialized...");
            } else {
                throw illegalStateException;
            }
        } catch (Exception e) {
            LOG.error("Something went wrong in loadclientmail()", e);
        }
        LOG.info("mail client successfully initialized");

    }

}
