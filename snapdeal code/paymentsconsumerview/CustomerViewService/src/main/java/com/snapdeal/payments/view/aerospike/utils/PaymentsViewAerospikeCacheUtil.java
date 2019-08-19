package com.snapdeal.payments.view.aerospike.utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;

@Slf4j
@Service
public class PaymentsViewAerospikeCacheUtil {
   
   public void handleAerospikeExceptionEvictKey(Key key, String string,
            AerospikeException aex) {
      log.error(string, aex);
   }

   public void logCacheOperation(OPERATION op, AerospikeProperties.Set set,
            AerospikeProperties.Bin bin, Object key, Object value) {
      log.debug("{} | {} | {} | {} => {}", new Object[] { op, set, bin, key, value });
   }
   
   public enum OPERATION {
      GET, PUT, EVICT, TOUCH
   }

}
