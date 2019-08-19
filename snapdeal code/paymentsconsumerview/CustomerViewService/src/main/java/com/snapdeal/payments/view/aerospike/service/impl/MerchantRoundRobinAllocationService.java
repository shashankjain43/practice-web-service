package com.snapdeal.payments.view.aerospike.service.impl;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.aerospike.service.IShardAllocationService;
import com.snapdeal.payments.view.aerospike.set.MerchantViewCounterSet;
import com.snapdeal.payments.view.datasource.DataBaseShardRelationMap;

@Service("MerchantRoundRobinAllocationService")
@Slf4j
public class MerchantRoundRobinAllocationService implements IShardAllocationService {

   @Autowired
   @Qualifier("databaseShardRelationMap")
   private DataBaseShardRelationMap databaseShardRelationMap;

   @Autowired
   @Qualifier("MerchantViewCounter")
   private MerchantViewCounterSet merchantCounter;

   public boolean put(String mercahntId, Integer count) {
      boolean writeSuccessful = true;
      writeSuccessful = merchantCounter.insert(mercahntId, count);
      return writeSuccessful;
   }

   public Integer get(String key) {
      Integer count = null;
      count = merchantCounter.get(key).getValue();
      return count;
   }

   @Override
   public String getShard(String key) {
      Map<String, List<String>> databaseShardMap = databaseShardRelationMap
               .getDataBaseShardRelationMap();
      List<String> shards = databaseShardMap.get(key);

      Integer counter = get(RowKey);
      if (counter != null)
         put(RowKey, ++counter);
      else {
         put(RowKey, ShardStartIndex);
         counter = ShardStartIndex;
      }
      
      log.info("total merchant counter :"+counter );

      return shards.get(counter % shards.size());
   }
}
