package com.snapdeal.ims.cache.set;


public interface IIMSAerospikeSet<Key, Value> {

   public IMSAerospikeRecord<Value> get(Key keyContent);

   public boolean insert(Key keyContent, Value value);

   public boolean update(Key keyContent, IMSAerospikeRecord<Value> valueRecord);

   public boolean delete(Key keyContent);
   
   public void refreshTTL(Key keyContent, int ttlSeconds);
   
   public boolean exist(Key keyContent);
}
