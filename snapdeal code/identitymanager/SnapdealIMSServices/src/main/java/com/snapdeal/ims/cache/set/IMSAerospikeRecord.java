package com.snapdeal.ims.cache.set;

import lombok.Data;

import com.aerospike.client.Record;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;

@Data
public class IMSAerospikeRecord<ValueType> {

   private ValueType value;
   
   private Integer expiration;
   
   private Integer generation;

   public IMSAerospikeRecord(){}
   
   public IMSAerospikeRecord(Record record, Bin bin){
      value = (ValueType)record.getValue(bin.getValue());
      expiration = record.expiration;
      generation = record.generation;
   }
   
}
