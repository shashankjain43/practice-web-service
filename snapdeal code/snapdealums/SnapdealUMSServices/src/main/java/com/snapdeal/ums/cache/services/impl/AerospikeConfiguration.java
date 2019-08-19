/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services.impl;

import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.constants.AerospikeProperties;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;

/**
 *  @version   1.0, Jan 22, 2015
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class AerospikeConfiguration {

    private ClientPolicy clientPolicy;
    private Policy readPolicy;
    private WritePolicy writePolicy;
    
    public void loadConfiguration() {
        UMSPropertiesCache properties = CacheManager.getInstance().getCache(UMSPropertiesCache.class);
        
        // initialize client policy
        this.clientPolicy = new ClientPolicy();
        
        String maxSocketIdle = properties.getProperty(AerospikeProperties.ClientPolicy.SOCKET_IDLE.getValue());
        if(maxSocketIdle!=null && !maxSocketIdle.isEmpty()){
            this.clientPolicy.maxSocketIdle = Integer.parseInt(maxSocketIdle);
        }
        
        String maxThreads = properties.getProperty(AerospikeProperties.ClientPolicy.MAX_THREADS.getValue());
        if(maxThreads!=null && !maxThreads.isEmpty()){
            this.clientPolicy.maxThreads = Integer.parseInt(maxThreads);
        }
        
        String isThreadPoolShared = properties.getProperty(AerospikeProperties.ClientPolicy.SHARED_THREADS.getValue());
        if(isThreadPoolShared!=null && !isThreadPoolShared.isEmpty()){
            this.clientPolicy.sharedThreadPool = Boolean.parseBoolean(isThreadPoolShared);
        }
        
        String connTimeout = properties.getProperty(AerospikeProperties.ClientPolicy.CONNECTION_TIMEOUT.getValue());
        if(connTimeout!=null && !connTimeout.isEmpty()){
            this.clientPolicy.timeout = Integer.parseInt(connTimeout);
        }
        
        // initialize default read policy
        this.readPolicy = new Policy();
        
        String maxRetries = properties.getProperty(AerospikeProperties.ReadPolicy.DEFAULT_MAX_READ_RETRIES.getValue());
        if(maxRetries!=null && !maxRetries.isEmpty()){
            this.readPolicy.maxRetries = Integer.parseInt(maxRetries);
        }
        
        String sleepInterval = properties.getProperty(AerospikeProperties.ReadPolicy.DEFAULT_SLEEP_BETWEEN_READ_RETRIES.getValue());
        if(sleepInterval!=null && !sleepInterval.isEmpty()){
            this.readPolicy.sleepBetweenRetries = Integer.parseInt(sleepInterval);
        }
        
        String readTimeout = properties.getProperty(AerospikeProperties.ReadPolicy.DEFAULT_READ_TIMEOUT.getValue());
        if(readTimeout!=null && !readTimeout.isEmpty()){
            this.readPolicy.timeout = Integer.parseInt(readTimeout);
        }
        
        this.writePolicy = new WritePolicy();
        
    }

    public ClientPolicy getClientPolicy() {
        return clientPolicy;
    }

    public Policy getReadPolicy() {
        return readPolicy;
    }

    public WritePolicy getWritePolicy() {
        return writePolicy;
    }
    
    

}
