package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.WhiteListAPI;

public enum WhitelistApiComparator implements Comparator<WhiteListAPI> {

   apiUri_sort{
      public int compare(WhiteListAPI details1, WhiteListAPI details2) {
      
         if(details1.getApiDetails().getApiURI() == null){
            return -1;
         }
         if(details2.getApiDetails().getApiURI() == null){
            return 1;
         }  

         return (details1.getApiDetails().getApiURI().compareTo(details2.getApiDetails().getApiURI()));
      }
   },
   
   apiMethod_sort{
      public int compare(WhiteListAPI details1, WhiteListAPI details2) {
         
         if(details1.getApiDetails().getApiMethod() == null){
            return -1;
         }
         if(details2.getApiDetails().getApiMethod() == null){
            return 1;
         }  

         return (details1.getApiDetails().getApiMethod().compareTo(details2.getApiDetails().getApiMethod()));
      }
   },
   
   apiAlias_sort{
      public int compare(WhiteListAPI details1, WhiteListAPI details2) {
         
         if(details1.getApiDetails().getAlias() == null){
            return -1;
         }
         if(details2.getApiDetails().getAlias() == null){
            return 1;
         }  

         return (details1.getApiDetails().getAlias().compareTo(details2.getApiDetails().getAlias()));
      }
   },
   
   apiAllowed_sort{
      public int compare(WhiteListAPI details1, WhiteListAPI details2) {
         
         if(details1.isAllowed() == false){
            return -1;
         }
         if(details2.isAllowed() == false){
            return 1;
         }  

         return 0;
      }
   };
   
    public static Comparator<WhiteListAPI> descending(final Comparator<WhiteListAPI> other) {
           return new Comparator<WhiteListAPI>() {
               public int compare(WhiteListAPI details1, WhiteListAPI details2) {
                   return -1 * other.compare(details1, details2);
               }
           };
}
}
