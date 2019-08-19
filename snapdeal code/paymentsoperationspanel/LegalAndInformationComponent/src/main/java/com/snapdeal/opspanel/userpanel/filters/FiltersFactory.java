package com.snapdeal.opspanel.userpanel.filters;

import com.snapdeal.opspanel.userpanel.response.TransactionDetails;

public class FiltersFactory {

   private FiltersFactory() {}

   private static FiltersFactory filtersFactory;

   public static FiltersFactory getInstance() {
      if( filtersFactory == null ) {
         filtersFactory = new FiltersFactory();
      }
      return filtersFactory;
   }

   public Filter getFilter( Class filterClass  ) {
      if( filterClass == TransactionDetails.class ) {
         return new TransactionDetailsFilter();
      }
      return null;
   }
}
