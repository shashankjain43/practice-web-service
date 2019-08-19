package com.snapdeal.ims.errorcodes;

public enum IMSMigrationExceptionCodes {

   ILLEGAL_MIGRATION_STATE_EXCEPTION("ER-8101","Error while Migrating user"),
   INVALID_UPGRADE_SOURCE("ER-8102","Invalid Upgrade Source"),
   OTP_MOBILE_MISSMATCH("ER-8103","Mobile number doesn't match with OTP sent."), 
   MOBILE_ALREADY_REGISTERED("ER-8104","Mobile number already registered against email id {0}"),
   ACCOUNT_ALREADY_MIGRATED("ER-8105", "ACCOUNT_ALREADY_MIGRATED"),
   OTHER_SIDE_SERVICE_DOWN("ER-8106", "Upgrade can't be performed at this time, as the service is down"),
   EMAIL_MISS_MATCH_VERIFICATION("ER-8107", "User logged in with different email id"),
   INVALID_USER_VERIFIED_THROUGH("ER-8108","Invalid User Identity Verified Through."),
   USER_INTERMEDIATE_STATE("ER-8109","Cannot blacklist user , as it is in linked state"),
   USER_BLACKLISTED("ER-8110","Blacklist user, unable to upgrade"),
   MIGRATION_NEVER_INITIATED_EXCEPTION("ER-8111","Migration never initiated for this user.");
   
   private String errCode;
   private String errMsg;

   private IMSMigrationExceptionCodes(String errCode, String errMsg) {
      this.errCode = errCode;
      this.errMsg = errMsg;
   }

   public String errCode() {
      return this.errCode;
   }

   public String errMsg() {
      return this.errMsg;
   }

}
