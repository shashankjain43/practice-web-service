package com.snapdeal.ims.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.cluster.Node;
import com.snapdeal.ims.cache.configuration.AerospikeConfiguration;
import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.errorcodes.IMSAerospikeExceptionCodes;
import com.snapdeal.ims.exception.IMSAerospikeException;
import com.snapdeal.payments.healthcheck.Pingable;

@Service("IMSCacheServiceProviderImpl")
@Slf4j
public class IMSCacheServiceProviderImpl implements IIMSCacheServiceProvider, Pingable {

   private AerospikeConfiguration config;

   private String hostname;
   private int port;

   private String clusterInfo;

   private IMSAerospikeClient imsAerospikeClient;

   public String getHostname() {
      return hostname;
   }

   public int getPort() {
      return port;
   }

   @Override
   public boolean isConnected() {
      if (imsAerospikeClient == null) {
         log.warn("umsAerospikeClient is NULL.");
         return false;
      }
      return imsAerospikeClient.isConnected();
   }

   @Override
   public void connectToCacheCluster(String clusterInfo) {
      try {
         if (this.imsAerospikeClient == null || this.clusterInfo == null
                  || !this.clusterInfo.equals(clusterInfo) || !isConnected()) {
            String[] nodes = clusterInfo.split(",");
            Host[] hosts = new Host[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
               hosts[i] = new Host(nodes[i].split(":")[0], Integer.parseInt(nodes[i].split(":")[1]));
            }
            
            // Load Configurations
            this.config = new AerospikeConfiguration();
            this.config.loadConfiguration();

            log.info("Going to initialize IMSAerospikeClient. Hosts are: " + hosts);
            try {
               Class.forName("com.aerospike.client.cluster.Node");
            } catch (ClassNotFoundException e) {
               throw new RuntimeException(e);
            }
            this.imsAerospikeClient = new IMSAerospikeClient(this.config.getClientPolicy(), hosts);
            if (isConnected()) {
               log.info("Connected to Aerospike cluster");
               this.clusterInfo = clusterInfo;
            } else {
               log.error("Unable to connect to Aerospike cluster");
               throw new RuntimeException("Unable to connect to Aerospike cluster");
            }
         } else {
            log.info("No change in Aerospike connection properties");
         }

         // Prints hostname and port of all the cluster nodes to log stream
         for (Node node : this.imsAerospikeClient.getNodes()) {
            log.info(node.getHost().toString());
         }

      } catch (AerospikeException aex) {
         handleAerospikeException(clusterInfo, "Exception when connecting to Aerospike: ", aex);

      }
   }

   @Override
   public IMSAerospikeClient getClient() {
      if (imsAerospikeClient == null) {
         throw new IMSAerospikeException(
                  IMSAerospikeExceptionCodes.AEROSPIKE_CLIENT_NOT_INITIALIZED.errCode(),
                  IMSAerospikeExceptionCodes.AEROSPIKE_CLIENT_NOT_INITIALIZED.errMsg());
      }
      return imsAerospikeClient;
   }

   @Override
   public AerospikeConfiguration getClientConfig() {
      if (config == null) {
         throw new IMSAerospikeException(
                  IMSAerospikeExceptionCodes.AEROSPIKE_CLIENT_CONFIG_NOT_PRESENT.errCode(),
                  IMSAerospikeExceptionCodes.AEROSPIKE_CLIENT_CONFIG_NOT_PRESENT.errMsg());
      }
      return config;
   }

   private void handleAerospikeException(Key key, String string, AerospikeException aex) {
      log.error(string, aex);
   }

   private void handleAerospikeException(String clusterInfo2, String string, AerospikeException aex) {
      log.error(string + clusterInfo2, aex);
      // since aerosipke is our primary source of truth, if not able to
      // connect due to any situation runtime exception should be thrown
      throw new RuntimeException(aex);

   }

   private void handleAerospikeExceptionEvictKey(Key emailKey, String string, AerospikeException aex) {
      log.error(string, aex);

   }

	@Override
	public boolean isHealthy() {
		return isConnected();
	}
}
