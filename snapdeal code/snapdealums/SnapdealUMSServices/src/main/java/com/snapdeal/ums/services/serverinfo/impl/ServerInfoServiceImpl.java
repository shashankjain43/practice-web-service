package com.snapdeal.ums.services.serverinfo.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.ServerInfo;
import com.snapdeal.core.utils.ServerInfoUtil;
import com.snapdeal.ums.dao.serverinfo.IServerInfoDao;
import com.snapdeal.ums.services.serverinfo.IServerInfoService;

@Service("serverInfoService")
@Transactional
public class ServerInfoServiceImpl implements IServerInfoService	 {
	
	private static final Logger     LOG = LoggerFactory.getLogger(ServerInfoServiceImpl.class);
	
	@Value("#{ systemProperties['servername']}")
    private String serverName;
	
    @Autowired
    private IServerInfoDao serverInfoDao;

    
    private ServerInfo prepareServer(){
		ServerInfo server = serverInfoDao.getServersByNameAndAddress(serverName,ServerInfoUtil.getHostAddress());
		if(server == null){
			server = new ServerInfo();
			server.setName(serverName);
   			server.setCreated(DateUtils.getCurrentTime());
   			server.setAddress(ServerInfoUtil.getHostAddress());
   		}
   		server.setReloadRequired(false);
   		server.setUpdated(DateUtils.getCurrentTime());
   		server.setLastReloaded(DateUtils.getCurrentTime());
    	
    	return server;
		
    }
    
	
	@Override
	public void updateServer(String type) {
		LOG.info("Updating Server Info with Type - START");
		if(serverName != null){
			ServerInfo server = prepareServer();
			server.setLastReloadCache(type);
			server.setReloadRequiredFor("all");
			serverInfoDao.updateServer(server);
			LOG.info("Updating Server Info with Type - Successful");
   		}else{
   			LOG.info("Updating Server Info with Type - Failed - No Server name defined");
   		}
		
	}
	
	@Override
	public void updateServerAtStartup() {
		LOG.info("Updating Server Info with Type - START");
		if(serverName != null){
			ServerInfo server = prepareServer();
			server.setLastReloadCache("all");
			server.setReloadRequiredFor("all");
			server.setStarted(DateUtils.getCurrentTime());
			serverInfoDao.updateServer(server);
			LOG.info("Updating Server Info with Type - Successful");
   		}else{
   			LOG.info("Updating Server Info with Type - Failed - No Server name defined");
   		}
		
	}



	@Override
	public ServerInfo getServerInfo() {
		if(serverName != null){
			ServerInfo server = serverInfoDao.getServersByNameAndAddress(serverName,ServerInfoUtil.getHostAddress());
			return server;
		}
		return null;
	}

	@Override
	public List<ServerInfo> getAllServerInfo() {
		return serverInfoDao.getAllServers();
	}


	@Override
	public void updateReloadRequired(String nodes, String caches) {
		String [] strings = nodes.split(",");
		List<String> list = Arrays.asList(strings);
		serverInfoDao.updateReloadRequired(list,caches);
		
	}
	
	@Override
	public void updateReloadRequired(String caches) {
		serverInfoDao.updateReloadRequired(serverName,caches);
		
	}

}
