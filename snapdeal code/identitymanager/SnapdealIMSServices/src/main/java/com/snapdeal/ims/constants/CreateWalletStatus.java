package com.snapdeal.ims.constants;

public enum CreateWalletStatus {
   CREATED("CREATED"),
   FAILED("FAILED"),
   NOT_CREATED("NOT_CREATED"),
   IN_PROGRESS("IN_PROGRESS");
   
   private String createWalletStatus;

   private CreateWalletStatus(String createWalletStatus) {
      this.createWalletStatus = createWalletStatus;
   }
   
   public String getCreateWalletStatus() {
      return createWalletStatus;
   }
}
