package com.snapdeal.payments.view.aerospike.set;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeRecord;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Bin;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Set;

@Service("mercahntViewMerchantIdShardKeySet")
public class MercahntViewMerchantIdShardKeySet extends PaymentsViewAerospikeBaseClass<String, String> {

	   public MercahntViewMerchantIdShardKeySet() {
	      super(Set.MERCHANT_ID_SHARD_KEY_SET);
	   }

	   @Override
	   Key getKey(String keyContent) {
	      return new Key(namespace.getValue(), set.getValue(), keyContent);
	   }

	   @Override
	   WritePolicy getWritePolicy(String value) {
	      WritePolicy writePolicy = new WritePolicy();
	      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;      
	      return writePolicy;
	   }

	   @Override
	   WritePolicy getDeletePolicy() {
	      // TODO Auto-generated method stub
	      return null;
	   }

	   @Override
	   WritePolicy getUpdatePolicy(PaymentsViewAerospikeRecord<String> existingRecord) {
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
	      return Bin.USER_ID_BIN;
	   }

	   @Override
	   void checkAndRefreshValueTTL(String keyContent, int expiration) {
	      // TODO Auto-generated method stub
	      
	   }
}
