package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.dao.entity.IMSAPIDetails;

public class IMSApisSorting {
   
   public static final String API_URI_ASC = "apiURI ASC";
   public static final String API_URI_DESC = "apiURI DESC";
   public static final String API_METHOD_ASC = "apiMethod ASC";
   public static final String API_METHOD_DESC = "apiMethod DESC";
   public static final String ALIAS_ASC = "alias ASC";
   public static final String ALIAS_DESC = "alias DESC";
   
   public static Comparator<IMSAPIDetails> getComparator(String sortingParam) {
      
      Comparator<IMSAPIDetails> fieldName = null;
      
      switch (sortingParam){
      
      case API_URI_ASC: 
         fieldName = IMSApisComparator.apiUri_sort;
         break;
         
      case API_URI_DESC:
         fieldName = IMSApisComparator.descending(IMSApisComparator.apiUri_sort);
         break;
      
      case API_METHOD_ASC: 
         fieldName = IMSApisComparator.apiMethod_sort;
         break;
         
      case API_METHOD_DESC:
         fieldName = IMSApisComparator.descending(IMSApisComparator.apiMethod_sort);
         break;
         
      case ALIAS_ASC: 
         fieldName = IMSApisComparator.apiAlias_sort;
         break;
         
      case ALIAS_DESC:
         fieldName = IMSApisComparator.descending(IMSApisComparator.apiAlias_sort);
         break;
               
      }
      return fieldName;
   }
}
