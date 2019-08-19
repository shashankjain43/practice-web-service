package com.snapdeal.ums.dao.serverinfo;

import java.util.List;

import com.snapdeal.ums.core.entity.ServerInfo;

public interface IServerInfoDao {
	ServerInfo getServersByNameAndAddress(String name,String address);
	void updateServer(ServerInfo server);
	List<ServerInfo> getAllServers();
	void updateReloadRequired(List<String> nodes,String caches);
	void updateReloadRequired(String node,String caches);
	
}
