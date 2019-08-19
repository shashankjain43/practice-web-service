package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.dao.entity.IMSAPIDetails;

public enum IMSApisComparator implements Comparator<IMSAPIDetails> {

   apiUri_sort{
      public int compare(IMSAPIDetails details1, IMSAPIDetails details2) {
      
         if(details1.getApiURI() == null){
            return -1;
         }
         if(details2.getApiURI() == null){
            return 1;
         }  

         return (details1.getApiURI().compareTo(details2.getApiURI()));
      }
   },
   
   apiMethod_sort{
      public int compare(IMSAPIDetails details1, IMSAPIDetails details2) {
         
         if(details1.getApiMethod() == null){
            return -1;
         }
         if(details2.getApiMethod() == null){
            return 1;
         }  

         return (details1.getApiMethod().compareTo(details2.getApiMethod()));
      }
   },
   
   apiAlias_sort{
      public int compare(IMSAPIDetails details1, IMSAPIDetails details2) {
         
         if(details1.getAlias() == null){
            return -1;
         }
         if(details2.getAlias() == null){
            return 1;
         }  

         return (details1.getAlias().compareTo(details2.getAlias()));
      }
   };
   
    public static Comparator<IMSAPIDetails> descending(final Comparator<IMSAPIDetails> other) {
           return new Comparator<IMSAPIDetails>() {
               public int compare(IMSAPIDetails details1, IMSAPIDetails details2) {
                   return -1 * other.compare(details1, details2);
               }
           };
}
}
