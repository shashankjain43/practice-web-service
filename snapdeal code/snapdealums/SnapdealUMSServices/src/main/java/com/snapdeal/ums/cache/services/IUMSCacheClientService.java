/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services;

import com.aerospike.client.Key;
import com.snapdeal.ums.core.sro.user.UserSRO;

/**
 *  @version   1.0, Jan 15, 2015
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public interface IUMSCacheClientService {
	
	public void evictInConsistentKeys();

    public boolean isConnected();
    
//    public void connectToCache(String hostname, int port);
    
    public void connectToCacheCluster(String clusterInfoCsv);
    
    public UserSRO getUserSROByEmail(final String email);
    
    public boolean putUserSROByEmail(final String email, final UserSRO userSRO);
    
    public boolean deleteUserSROByEmail(final String email);
    
    public UserSRO getUserSROById(final int userId);
    
    public boolean putEmailByIdMapping(final int userId, final String email);
    
    public String getEmailByIdMapping(final int userId);
    
    public boolean putUserSROById(final int userId, final UserSRO userSRO);

	void scheduleCachedKeyEviction(String namespace, String set, String key);

	void scheduleCachedKeyEviction(Key key);
        
}
