package com.snapdeal.ums.services.serverinfo;

import java.util.List;

import com.snapdeal.ums.core.entity.ServerInfo;


public interface IServerInfoService {
    public void updateServer(String type);
    public void updateServerAtStartup();
    public ServerInfo getServerInfo(); 
    public List<ServerInfo> getAllServerInfo();
    public void updateReloadRequired(String nodes,String caches);
    public void updateReloadRequired(String caches);
}
