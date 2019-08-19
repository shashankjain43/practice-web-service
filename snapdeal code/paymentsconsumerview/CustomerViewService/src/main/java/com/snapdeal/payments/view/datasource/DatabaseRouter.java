package com.snapdeal.payments.view.datasource;

import com.snapdeal.payments.configuration.RoutingDataSource;


public class DatabaseRouter extends RoutingDataSource {

   @Override
   protected String databaseAllocationLogicHandler(String shardingKey) {
      return shardingKey;
   }
}
