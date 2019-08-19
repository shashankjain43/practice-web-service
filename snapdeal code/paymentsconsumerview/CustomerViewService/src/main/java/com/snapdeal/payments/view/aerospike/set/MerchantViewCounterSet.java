package com.snapdeal.payments.view.aerospike.set;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeRecord;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Bin;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Set;

@Service("MerchantViewCounter")
public class MerchantViewCounterSet extends PaymentsViewAerospikeBaseClass<String, Integer> {

   public MerchantViewCounterSet() {
      super(Set.MERCHANT_COUNTER);
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(Integer value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.UPDATE;
      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   WritePolicy getUpdatePolicy(PaymentsViewAerospikeRecord<Integer> existingRecord) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   Bin getBin() {
      return Bin.MERCHANT_COUNTER;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {
      // TODO Auto-generated method stub

   }
}
