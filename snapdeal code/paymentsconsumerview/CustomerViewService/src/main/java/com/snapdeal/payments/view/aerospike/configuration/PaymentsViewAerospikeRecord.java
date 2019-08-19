package com.snapdeal.payments.view.aerospike.configuration;

import lombok.Data;

import com.aerospike.client.Record;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Bin;

@Data
public class PaymentsViewAerospikeRecord<ValueType> {

   private ValueType value;
   
   private Integer expiration;
   
   private Integer generation;

   public PaymentsViewAerospikeRecord(){}
   
   public PaymentsViewAerospikeRecord(Record record, Bin bin){
      value = (ValueType)record.getValue(bin.getValue());
      expiration = record.expiration;
      generation = record.generation;
   }
   
}
