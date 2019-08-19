package com.snapdeal.payments.view.aerospike.set;

import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeRecord;



public interface IPaymentsViewAerospikeSet<Key, Value> {
   

   public PaymentsViewAerospikeRecord<Value> get(Key keyContent);

   public boolean insert(Key keyContent, Value value);

   public void refreshTTL(Key keyContent, int ttlSeconds);
   
   public boolean exist(Key keyContent);
}
