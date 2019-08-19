/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ims.constants;

/**
 * @version 1.0, June 2, 2015
 * @author Kishan
 */

public class AerospikeProperties {

   public enum Namespace {
      USER_NAMESPACE("BasicUserSpace");

      private String value;

      Namespace(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }

   }

   public enum Set {
      GTOKEN_ID_OBJECT_SET("GtokenObjMapping"), GTOKEN_ID_SET("GtokenIdSet"), USER_VERIFICATION_SET(
               "UserVerificationSet"), USER_EMAIL_SET("UserEmailSet"), USER_MOBILE_SET(
               "UserMobileSet"), USER_SD_USERID_SET("UserSdUserIdSet"), USER_FC_USERID_SET(
               "UserFcUserIdSet"), USER_SD_FC_USERID_SET("UserSdFcUserIdSet"), USER_DETAILS_SET(
               "UserObjectSet"), EMAIL_SD_FC_PASSWORD_SET("sdFcPasswordSet"), SDUSERID_EMAIL_SET(
               "sdUserIDemail"), FCUSERID_EMAIL_SET("fcUserIDemail"), IMSUSERID_EMAIL_SET(
               "ocUserIDemail");

      private String value;

      Set(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }
   }

   public enum Bin {
      GTOKEN_ID_OBJECT_BIN("Gtoken"), GTOKEN_ID_BIN("gtokenIdBin"), USER_VERIFICATION_BIN(
               "UserVerfction"), USER_DETAILS_BIN("userObjectBin"), USER_EMAIL_BIN("userEmailBin"), USER_ID_BIN(
               "userIdBin"), SD_FC_PASSWORD_BIN("sdFcPassword"), SDUSERID_EMAIL_BIN("sdUserIDemail"), FCUSERID_EMAIL_BIN(
               "fcUserIDemail"), IMSUSERID_EMAIL_BIN("ocUserIDemail");

      private String value;

      Bin(String value) {
         this.value = value;
      }

      public String getValue() {
         return this.value;
      }
   }
}