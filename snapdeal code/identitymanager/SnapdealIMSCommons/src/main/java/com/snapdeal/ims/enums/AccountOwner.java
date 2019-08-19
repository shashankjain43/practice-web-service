/**
 * 
 */
package com.snapdeal.ims.enums;

public enum AccountOwner {

   SD("SNAPDEAL"), FC("FREECHARGE"), ONE_CHECK("ONECHECK");

   private String accountOwnerName;

   private AccountOwner(String accountOwnerName) {
      this.accountOwnerName = accountOwnerName;
   }

   public String getAccountOwnerName() {
      return accountOwnerName;
   }
   
   public static AccountOwner fromString(String text) {
      if (text != null) {
         for (AccountOwner accountOwner : AccountOwner.values()) {
            if (text.equalsIgnoreCase(accountOwner.getAccountOwnerName())) {
               return accountOwner;
            }
         }
      }
      return null;
    }
}
