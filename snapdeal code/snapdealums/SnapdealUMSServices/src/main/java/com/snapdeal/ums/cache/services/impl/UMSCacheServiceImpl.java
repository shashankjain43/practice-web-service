/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.cache.services.IUMSCacheClientService;
import com.snapdeal.ums.cache.services.IUMSCacheService;

/**
 *  @version   1.0, Jan 15, 2015
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service
public class UMSCacheServiceImpl implements IUMSCacheService {

    private boolean isEnabled;
    
    @Autowired
    private IUMSCacheClientService umsCacheClient;

    @Override
    public boolean isCacheEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

//    public void connectCacheClient(String hostname, int port) {
//        umsCacheClient.connectToCache(hostname, port);
//    }
    
    public void connectCacheCluster(String clusterInfoCsv){
        umsCacheClient.connectToCacheCluster(clusterInfoCsv);
    }

    @Override
    public boolean isCacheConnected() {
        return umsCacheClient.isConnected();
    }

    @Override
    public IUMSCacheClientService getCacheClient() {
        return this.umsCacheClient;
    }

	@Override
	public void evictInConsistentKeys() {
		umsCacheClient.evictInConsistentKeys();
	}
}
