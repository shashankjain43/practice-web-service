package com.snapdeal.payments.view.aerospike.utils;

/**
 * 
 * @author abhishek
 *
 */

public class AerospikeProperties {

   public enum Namespace {
      USER_SHARD_SPACE("BasicUserShardSpace");

      private String value;

      Namespace(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }

   }

   public enum Set {
      USER_ID_SHARD_KEY_SET("UserIdShardKeySet"), MERCHANT_ID_SHARD_KEY_SET("merchantIdShardKeySet"), MERCHANT_COUNTER(
               "merchantCounterSet");
      private String value;

      Set(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }
   }

   public enum Bin {
      USER_ID_BIN("userId"), MERCHANT_COUNTER("mCounter");

      private String value;

      Bin(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }
   }
}