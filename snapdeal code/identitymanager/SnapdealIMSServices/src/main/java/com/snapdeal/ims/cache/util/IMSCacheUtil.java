package com.snapdeal.ims.cache.util;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.snapdeal.ims.constants.AerospikeProperties;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IMSCacheUtil {
   
   public void handleAerospikeExceptionEvictKey(Key key, String string,
            AerospikeException aex) {
      log.error(string, aex);
   }

   public void logCacheOperation(OPERATION op, AerospikeProperties.Set set,
            AerospikeProperties.Bin bin, Object key, Object value) {
      log.trace("{} | {} | {} | {} => {}", new Object[] { op, set, bin, key, value });
   }
   
   public enum OPERATION {
      GET, PUT, EVICT, TOUCH
   }

}
