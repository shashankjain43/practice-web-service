package com.snapdeal.ims.dao.aerospike.common;

import java.util.HashMap;
import java.util.Map;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.service.impl.IMSAerospikeClient;

public class AerospikeClientMock extends IMSAerospikeClient{

   static HashMap<String, Map<String, Object>> testdataMap = new HashMap<String, Map<String, Object>>();
   
   AerospikeClientMock() {
      super(null, new Host[]{new Host("assdasd", 9300)});
   }
   
   @Override
   public Record get(Policy policy, Key key) {
      Record rec = new Record(testdataMap.get(key.namespace+"=="+key.setName+"=="+key.userKey), 0, 9); 
      return rec;
   }
   
   @Override
   public void put(WritePolicy writePolicy, Key key, Bin bin) {
      Map<String, Object> data = new HashMap<String, Object>();
      data.put(bin.name, bin.value.getObject());
      
      if(writePolicy.recordExistsAction == RecordExistsAction.CREATE_ONLY){
         if(testdataMap.containsKey(key.namespace+"=="+key.setName+"=="+key.userKey)){
            throw new AerospikeException();
         }
      }
      testdataMap.put(key.namespace+"=="+key.setName+"=="+key.userKey, data);
   }
   
   @Override
   public boolean delete(WritePolicy writePolicy, Key key) {
      testdataMap.remove(key.namespace+"=="+key.setName+"=="+key.userKey);
      return true;
   }
   
   @Override
   public void touch(WritePolicy writePolicy, Key key) {
   }
   
   @Override
   public boolean exists(WritePolicy writePolicy, Key key) {
      return testdataMap.containsKey(key.namespace+"=="+key.setName+"=="+key.userKey);
   }
}
