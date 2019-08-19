package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.WhiteListAPI;

public class WhitelistApiSorting {
   
   public static final String API_URI_ASC = "apiUri ASC";
   public static final String API_URI_DESC = "apiUri DESC";
   public static final String API_METHOD_ASC = "apiMethod ASC";
   public static final String API_METHOD_DESC = "apiMethod DESC";
   public static final String ALIAS_ASC = "apiAlias ASC";
   public static final String ALIAS_DESC = "apiAlias DESC";
   public static final String ALLOWED_ASC = "isAllowed ASC";
   public static final String ALLOWED_DESC = "isAllowed DESC";
   
   
   public static Comparator<WhiteListAPI> getComparator(String sortingParam) {
      
      Comparator<WhiteListAPI> fieldName = null;
      
      switch (sortingParam){
      
      case API_URI_ASC: 
         fieldName = WhitelistApiComparator.apiUri_sort;
         break;
         
      case API_URI_DESC:
         fieldName = WhitelistApiComparator.descending(WhitelistApiComparator.apiUri_sort);
         break;
      
      case API_METHOD_ASC: 
         fieldName = WhitelistApiComparator.apiMethod_sort;
         break;
         
      case API_METHOD_DESC:
         fieldName = WhitelistApiComparator.descending(WhitelistApiComparator.apiMethod_sort);
         break;
         
      case ALIAS_ASC: 
         fieldName = WhitelistApiComparator.apiAlias_sort;
         break;
         
      case ALIAS_DESC:
         fieldName = WhitelistApiComparator.descending(WhitelistApiComparator.apiAlias_sort);
         break;
         
      case ALLOWED_ASC: 
         fieldName = WhitelistApiComparator.apiAllowed_sort;
         break;
         
      case ALLOWED_DESC:
         fieldName = WhitelistApiComparator.descending(WhitelistApiComparator.apiAllowed_sort);
         break;
      
      }
      return fieldName;
   }
}
