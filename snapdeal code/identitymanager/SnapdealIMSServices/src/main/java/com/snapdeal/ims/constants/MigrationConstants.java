package com.snapdeal.ims.constants;

public class MigrationConstants {

   public static final String MigrationEnabled = "migration enabled ";
   public static final String USER_EXISTS_IMS_EMAIL_ID = "User exists in ims with email id ";
   public static final String USER_FETCH_FC = "user does not exists in FC with email id ";
   public static final String USER_FETCH_SD = "user does not exists in SD with email id ";
   public static final String ILLEGAL_MIGRATION_STATE = "user has never seen upgrade screen and exists on OC, this is a special case";
   public static final String MIGRATION_NEVER_INITIATED = "Migration never initiated";
   public static final String SKIP_MIGRATION = "Migration skipped for user with email id ";
   public static final String MIGRATION_STARTS = "Migration starts for user with email id";
   public static final String MIGRATION_ENDS = "Migration ends for user with email id";
   public static final String WITH_STATE = "with state ";
   public static final String MERGING_USER = "Merging User,since password matched on both FC and SD  for user with email ";
   public static final String SKIPPING_MERGE = "Skipping Merging User,since password not matched on both FC and SD  for user with email ";

}
