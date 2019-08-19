/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services;

/**
 *  @version   1.0, Jan 15, 2015
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public interface IUMSCacheService {
    
    public boolean isCacheEnabled();
    public void setEnabled(boolean isEnabled);
    
    public IUMSCacheClientService getCacheClient();
//    public void connectCacheClient(String hostname, int port);
    public void connectCacheCluster(String clusterInfoCsv);
    public boolean isCacheConnected();
    public void evictInConsistentKeys();
    
    
}
