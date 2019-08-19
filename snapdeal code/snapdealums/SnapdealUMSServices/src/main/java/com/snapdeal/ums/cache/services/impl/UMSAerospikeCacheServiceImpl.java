/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.cache.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.cluster.Node;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.cache.services.IUMSCacheClientService;
import com.snapdeal.ums.constants.AerospikeProperties;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.CacheKeyToBeEvictedDO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.email.intrnl.email.SendUMSNotificationRequest;
import com.snapdeal.ums.server.services.impl.UMSNotificationService;
import com.snapdeal.ums.services.cache.impl.CacheKeyToBeEvictedServiceImpl;

/**
 * @version 1.0, Jan 15, 2015
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service
public class UMSAerospikeCacheServiceImpl implements IUMSCacheClientService {

	@Autowired
	private CacheKeyToBeEvictedServiceImpl cacheKeyToBeEvictedServiceImpl;
	@Autowired
	private UMSNotificationService umsNotificationService;

	private static final Logger LOG = LoggerFactory
			.getLogger(UMSAerospikeCacheServiceImpl.class);

	private static final String AEROSPIKE_ISSUE_NOTIFICATION_SUBJECT = "Issue in Aerospike @UMS production";

	private AerospikeConfiguration config;

	private String hostname;
	private int port;

	private String clusterInfo;

	private UMSAerospikeClient umsAerospikeClient;

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	@EnableMonitoring
	@Override
	public boolean isConnected() {
		if (umsAerospikeClient == null) {
			LOG.warn("umsAerospikeClient is NULL.");
			return false;
		}
		return umsAerospikeClient.isConnected();
	}

	// @Override
	// public void connectToCache(String hostname, int port) {
	// try {
	// if ((this.hostname == null)
	// || (!this.hostname.equals(hostname) || this.port != port)) {
	//
	// // Load Configurations
	// this.config = new AerospikeConfiguration();
	// this.config.loadConfiguration();
	//
	// this.umsAerospikeClient = new UMSAerospikeClient(
	// this.config.getClientPolicy(), hostname, port);
	// if (isConnected()) {
	// LOG.info("Connected to Aerospike cluster");
	// this.hostname = hostname;
	// this.port = port;
	// } else {
	// LOG.error("Unable to connect to Aerospike cluster");
	// }
	// } else {
	// LOG.info("No change in Aerospike connection properties");
	// }
	// } catch (AerospikeException aex) {
	// handleAerospikeException(String.valueOf(hostname+":"+port),"Exception when connecting to Aerospike: ",
	// aex);
	//
	// }
	// }

	@EnableMonitoring
	@Override
	public void connectToCacheCluster(String clusterInfo) {
		try {
			if (this.umsAerospikeClient == null || this.clusterInfo == null
					|| !this.clusterInfo.equals(clusterInfo) || !isConnected()) {
				String[] nodes = clusterInfo.split(",");
				Host[] hosts = new Host[nodes.length];
				for (int i = 0; i < nodes.length; i++) {
					hosts[i] = new Host(nodes[i].split(":")[0],
							Integer.parseInt(nodes[i].split(":")[1]));
				}

				// Load Configurations
				this.config = new AerospikeConfiguration();
				this.config.loadConfiguration();
				LOG.info("Going to initialize umsAerospikeClient. Hosts are: "
						+ hosts);

				this.umsAerospikeClient = new UMSAerospikeClient(
						this.config.getClientPolicy(), hosts);
				if (isConnected()) {
					LOG.info("Connected to Aerospike cluster");
					this.clusterInfo = clusterInfo;
				} else {
					LOG.error("Unable to connect to Aerospike cluster");
				}
			} else {
				LOG.info("No change in Aerospike connection properties");
			}

			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : this.umsAerospikeClient.getNodes()) {
				LOG.info(node.getHost().toString());
			}

		} catch (AerospikeException aex) {
			handleAerospikeException(clusterInfo,
					"Exception when connecting to Aerospike: ", aex);

		}
	}

	@EnableMonitoring
	@Override
	public UserSRO getUserSROByEmail(String email) {
		UserSRO userSRO = null;
		Key key = null;

		try {
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_SET.getValue(), email);
			Record record = umsAerospikeClient.get(this.config.getReadPolicy(),
					key);
			if (record != null) {
				userSRO = (UserSRO) record
						.getValue(AerospikeProperties.Bin.BASIC_USER_DATA_BIN
								.getValue());
			}
			logCacheOperation(OPERATION.GET,
					AerospikeProperties.Bin.BASIC_USER_DATA_BIN, email, userSRO);

		} catch (AerospikeException aex) {
			handleAerospikeException(key,
					"Unable to fetch record from aerospike", aex);

		}
		return userSRO;
	}

	@EnableMonitoring
	@Override
	public boolean putUserSROByEmail(String email, UserSRO userSRO) {
		boolean writeSuccessful = true;
		Key key = null;

		try {
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_SET.getValue(), email);
			Bin bin = new Bin(
					AerospikeProperties.Bin.BASIC_USER_DATA_BIN.getValue(),
					userSRO);
			umsAerospikeClient.put(this.config.getWritePolicy(), key, bin);
			// Let us add userID Vs email mapping right now to save another db
			// fetch because of getUserbyID call.
			if (userSRO != null) {
				putEmailByIdMapping(userSRO.getId(), userSRO.getEmail());
			}
			logCacheOperation(OPERATION.PUT,
					AerospikeProperties.Bin.BASIC_USER_DATA_BIN, email, userSRO);
		} catch (AerospikeException aex) {
			handleAerospikeExceptionEvictKey(key,
					"Unable to write data in aerospike: ", aex);

			writeSuccessful = false;
		}
		return writeSuccessful;
	}

	@EnableMonitoring
	@Override
	public boolean deleteUserSROByEmail(String email) {
		boolean deleteSuccessful = true;
		Key key = null;
		try {
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_SET.getValue(), email);

			boolean result = umsAerospikeClient.delete(
					this.config.getWritePolicy(), key);
			logCacheOperation(OPERATION.EVICT, null, email, result);

		} catch (AerospikeException aex) {
			handleAerospikeExceptionEvictKey(key,
					"Unable to delete from aerospike: ", aex);

			deleteSuccessful = false;
		}
		return deleteSuccessful;
	}

	@EnableMonitoring
	@Override
	public UserSRO getUserSROById(int userId) {
		Key key = null;
		UserSRO userSRO = null;
		try {
			// Fetch user email by id
			String email;
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_ID_EMAIL_MAP.getValue(),
					userId);
			Record record = umsAerospikeClient.get(this.config.getReadPolicy(),
					key);
			logCacheOperation(OPERATION.GET,
					AerospikeProperties.Bin.USER_ID_EMAIL_BIN, userId, record);

			if (record != null) {
				email = (String) record
						.getValue(AerospikeProperties.Bin.USER_ID_EMAIL_BIN
								.getValue());
				userSRO = getUserSROByEmail(email);
			}
		} catch (AerospikeException aex) {
			handleAerospikeException(key,
					"Unable to fetch record from aerospike", aex);

		}
		return userSRO;
	}

	@EnableMonitoring
	@Override
	public boolean putEmailByIdMapping(int userId, String email) {
		boolean writeSuccessful = true;
		Key key = null;
		try {
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_ID_EMAIL_MAP.getValue(),
					userId);
			Bin bin = new Bin(
					AerospikeProperties.Bin.USER_ID_EMAIL_BIN.getValue(), email);
			logCacheOperation(OPERATION.PUT,
					AerospikeProperties.Bin.USER_ID_EMAIL_BIN, userId, email);
			umsAerospikeClient.put(this.config.getWritePolicy(), key, bin);
		} catch (AerospikeException aex) {
			handleAerospikeExceptionEvictKey(key,
					"Unable to write data in aerospike: ", aex);
			writeSuccessful = false;
		}
		return writeSuccessful;
	}

	@EnableMonitoring
	@Override
	public String getEmailByIdMapping(int userId) {
		String email = null;
		Key key = null;
		try {
			key = new Key(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_ID_EMAIL_MAP.getValue(),
					userId);
			Record record = umsAerospikeClient.get(this.config.getReadPolicy(),
					key);
			if (record != null) {
				email = (String) record
						.getValue(AerospikeProperties.Bin.USER_ID_EMAIL_BIN
								.getValue());
			}
			logCacheOperation(OPERATION.GET,
					AerospikeProperties.Bin.USER_ID_EMAIL_BIN, userId, email);

		} catch (AerospikeException aex) {
			handleAerospikeException(key,
					"Unable to get data from aerospike: ", aex);

		}
		return email;
	}

	@EnableMonitoring
	@Override
	public boolean putUserSROById(int userId, UserSRO userSRO) {
		boolean sroWriteSuccessful = true;
		boolean mapWriteSuccessful = false;
		Key emailKey = null;
		try {
			String email = userSRO.getEmail();

			// Always update the latest UserSRO into Aerospike - This takes care
			// of scenario, when a new developer might update userID Vs UserSRO
			// mapping instead of updating emailID Vs SRO.
			sroWriteSuccessful = putUserSROByEmail(email, userSRO);
			if (sroWriteSuccessful) {
				mapWriteSuccessful = putEmailByIdMapping(userId, email);
			}
		} catch (AerospikeException aex) {
			handleAerospikeExceptionEvictKey(emailKey,
					"Unable to write data to aerospike", aex);
			return false;
		}
		return (sroWriteSuccessful && mapWriteSuccessful);
	}

	private enum OPERATION {
		GET, PUT, EVICT
	}

	private void logCacheOperation(OPERATION op, AerospikeProperties.Bin bin,
			Object key, Object value) {
		LOG.info("{} | {} | {} {}", new Object[] { op, bin, key, value });
		LOG.info((new StringBuilder(op.toString()).append("|").append(bin)
				.append("|").append(key).append(" ").append(value)).toString());
	}

	/**
	 * Handles Aerospike exceptions by
	 * 
	 * 1. Logging it 2. Sending UMSNotification via email
	 * 
	 * @param key
	 * @param msg
	 * @param aerospikeException
	 */
	@EnableMonitoring
	private void handleAerospikeExceptionEvictKey(Key key, String msg,
			AerospikeException aerospikeException) {

		handleAerospikeException(key, msg, aerospikeException);
		try {

			scheduleCachedKeyEviction(key);
		} catch (org.springframework.dao.DataIntegrityViolationException dataIntegrityViolationException) {
			LOG.info("*Possible that key: " + key + " already in db.",
					dataIntegrityViolationException);
		} catch (HibernateException hibernateException) {
			LOG.error(
					"HibernateException while calling saveCacheKeyToBeEvicted for key: "
							+ key, hibernateException);
		}

		catch (Exception exception) {
			LOG.error(
					"Exception while calling saveCacheKeyToBeEvicted for key: "
							+ key, exception);
		}

	}

	/**
	 * @param key
	 */
	// @Transactional
	@Override
	public void scheduleCachedKeyEviction(String namespace, String set,
			String key) {
		scheduleCachedKeyEviction(new Key(namespace, set, key));

	}

	@Override
	public void scheduleCachedKeyEviction(Key key) {
		try {
			cacheKeyToBeEvictedServiceImpl.saveCacheKeyToBeEvicted(key);
		} catch (Exception e) {
			LOG.warn("Exception while saving cache key: " + key
					+ " for eviction.", e);
		}
		// executorService.execute(new SaveCacheKeyToBeEvictedRunnable(key));
		// cacheKeyToBeEvictedServiceImpl.saveCacheKeyToBeEvicted(key);

	}

	// private class SaveCacheKeyToBeEvictedRunnable implements Runnable {
	//
	// private Key key;
	//
	// public SaveCacheKeyToBeEvictedRunnable(Key key) {
	// super();
	// this.key = key;
	// }
	//
	// public void run() {
	// try{
	// cacheKeyToBeEvictedServiceImpl.saveCacheKeyToBeEvicted(key);
	// }
	// catch(Exception e){
	// LOG.warn("Exception while saving cache key: "+key+" for eviction.",e);
	// }
	//
	// }
	// }
	/**
	 * @param context
	 * @param msg
	 * @param aerospikeException
	 * @return
	 */
	private void handleAerospikeException(Object context, String msg,
			AerospikeException aerospikeException) {

		StringBuilder errorMsgBuilder = new StringBuilder();
		errorMsgBuilder.append(msg).append(" Context: ").append(context);
		LOG.error(errorMsgBuilder.toString(), aerospikeException);
		errorMsgBuilder.append("Exception msg: ")
				.append(aerospikeException.getMessage()).append(" Cause: ")
				.append(aerospikeException.getCause()).append(" StackTrace: ")
				.append(aerospikeException.getStackTrace());
		executorService.execute(new UMSNotifierRunnable(errorMsgBuilder
				.toString()));

		// SendUMSNotificationRequest sendUMSNotificationRequest = new
		// SendUMSNotificationRequest(
		// errorMsgBuilder.toString());
		// umsNotificationService
		// .sendUMSNotification(sendUMSNotificationRequest);

	}

	private final ExecutorService executorService = new ThreadPoolExecutor(1,
			5, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	private class UMSNotifierRunnable implements Runnable {

		private String msg;

		public UMSNotifierRunnable(String msg) {
			super();
			this.msg = msg;
		}

		public void run() {
			SendUMSNotificationRequest sendUMSNotificationRequest = new SendUMSNotificationRequest(
					msg, AEROSPIKE_ISSUE_NOTIFICATION_SUBJECT);
			umsNotificationService
					.sendTechBreachNotification(sendUMSNotificationRequest);
		}
	}

	@Override
	public void evictInConsistentKeys() {
		// Passing "1" because it is the first time this method is being invoked
		// in this flow!
		evictInConsistentKeys(1);
	}

	/**
	 * Evicts inconsistent keys from Aerospike if it has been enabled.
	 */
	private void evictInConsistentKeys(int aerospikeEvictionIterationCount) {

		LOG.info("Attempting eviction of inconsistent cache keys.");

		boolean isAerospikeInconsistentKeyEvictionEnabled = CacheManager
				.getInstance().getCache(UMSPropertiesCache.class)
				.isAerospikeInconsistentKeyEvictionEnabled();

		if (!isAerospikeInconsistentKeyEvictionEnabled) {
			LOG.info("AerospikeInconsistentKeyEvictionEnabled = FALSE. Skipping eviction!");
			return;
		}

		boolean isConnected = false;
		int attemptCount = 1;
		while (attemptCount++ < 4) {
			isConnected = isConnected();
			if (isConnected) {
				break;
			} else {
				LOG.info("Could not connect to Aerospike. Attempt#"
						+ attemptCount);
				try {
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					// Do nothing
				}
			}
		}
		if (!isConnected) {
			LOG.info("Can't proceed with eviction of inconsistent cache keys because umsAerospikeClient.isConnected() = FALSE.");
			return;
		}

		List<CacheKeyToBeEvictedDO> cacheKeyToBeEvictedDOs = cacheKeyToBeEvictedServiceImpl
				.getFirstSetOfCacheKeysToBeEvicted();
		if (cacheKeyToBeEvictedDOs == null || cacheKeyToBeEvictedDOs.size() < 1) {
			// Don't have to do anything
			LOG.info("There are no keys to evict from Aerospike in this run!");

			return;
		}

		int aerospikeTransactionIterationLimit = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class)
				.getAerospikeEvictionTransactionIterationLimit();
		if (aerospikeEvictionIterationCount > aerospikeTransactionIterationLimit) {

			String transactionCountExceededWarnning = "Unable to keep up with key eviction. Have exceeded db set transaction iteration limit of "
					+ aerospikeTransactionIterationLimit
					+ " PLEASE CHECK! IT COULD OVERLOAD UMS SERVER!";
			LOG.warn(transactionCountExceededWarnning);
			umsNotificationService
					.sendTechBreachNotification(new SendUMSNotificationRequest(
							transactionCountExceededWarnning,
							AEROSPIKE_ISSUE_NOTIFICATION_SUBJECT));

		}

		// If we are here, that means we have to evict a few keys from
		// Aerospike.
		LOG.info("Have to evict " + cacheKeyToBeEvictedDOs.size()
				+ " key(s) in this run!");
		List<Integer> keysEvicted = new ArrayList<Integer>(
				cacheKeyToBeEvictedDOs.size());
		Iterator<CacheKeyToBeEvictedDO> it = cacheKeyToBeEvictedDOs.iterator();
		while (it.hasNext()) {

			CacheKeyToBeEvictedDO cacheKeyToBeEvictedDO = it.next();
			if (cacheKeyToBeEvictedDO != null) {
				try {
					Key key = new Key(cacheKeyToBeEvictedDO.getNamespace(),
							cacheKeyToBeEvictedDO.getSet(),
							cacheKeyToBeEvictedDO.getKey());

					umsAerospikeClient
							.delete(this.config.getWritePolicy(), key);
					// In case of any Aerospike connectivity/communication
					// issue, we
					// will get Aerospike exception.
					// If it is not encountered, then we can assume they key has
					// been evicted - if it was indeed present in Aerospike.
					keysEvicted.add(cacheKeyToBeEvictedDO.getId());
				} catch (AerospikeException aex) {
					LOG.error(
							"AerospikeException while trying to evict inconsistent Key: "
									+ cacheKeyToBeEvictedDO.getKey(), aex);
				} catch (Exception e) {
					LOG.error(
							"Exception while trying to evict inconsistent Key: "
									+ cacheKeyToBeEvictedDO.getKey(), e);
				}
			}
		}

		if (keysEvicted.size() != cacheKeyToBeEvictedDOs.size()) {
			LOG.warn("Keys evicted is < to be evicted - {}", new Object[] {
					keysEvicted.size(), cacheKeyToBeEvictedDOs.size() });
			umsNotificationService
					.sendTechBreachNotification(new SendUMSNotificationRequest(
							"Despite Aerospike being avilable, UMS "
									+ CacheManager.getInstance()
											.getCache(UMSPropertiesCache.class)
											.getServerId()
									+ " is not able to evict all inconsistent keys! Pls check!",
							AEROSPIKE_ISSUE_NOTIFICATION_SUBJECT));
		}
		if (keysEvicted.size() > 0) {
			// Increment aerospikeEvictionIterationCount counter!
			aerospikeEvictionIterationCount++;

			cacheKeyToBeEvictedServiceImpl
					.deleteCacheKeysAfterEviction(keysEvicted);
			// Do it again until there are no keys to be evicted from Aerospike.
			evictInConsistentKeys(++aerospikeEvictionIterationCount);
		}

	}
}
