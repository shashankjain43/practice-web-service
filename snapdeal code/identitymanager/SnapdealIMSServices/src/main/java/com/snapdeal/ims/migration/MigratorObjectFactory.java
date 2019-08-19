package com.snapdeal.ims.migration;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.util.UserAccountUtil;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MigratorObjectFactory {

   @Autowired
   @Qualifier("ExistFCSide")
   UserAccountExist existFCSide;

   @Autowired
   @Qualifier("ExistSDSide")
   UserAccountExist existSDSide;

   @Autowired
   @Qualifier("NotExistOnAnySide")
   UserAccountExist notExistOnAnySide;

   @Autowired
   @Qualifier("ExistBothSide")
   UserAccountExist existBothSide;

   @Qualifier("sdUserAccount")
   @Autowired
   private UserAccountUtil sdUserAccount;

   @Qualifier("fcUserAccount")
   @Autowired
   private UserAccountUtil fcUserAccount;

   @Qualifier("ocUserAccount")
   @Autowired
   private UserAccountUtil ocUserAccount;

   @Autowired
   private UmsMerchantProvider merchantProvider;

   public UserAccountExist getMigratorObject(State currentState, Merchant originatingSource) {

      switch (currentState) {
         case FC_ACCOUNT_EXISTS_AND_ENABLED:
            return existFCSide;

         case SD_ACCOUNT_EXISTS_AND_ENABLED:
            return existSDSide;

         case SD_FC_ACCOUNT_EXISTS_AND_ENABLED:
         case SD_ENABLED_FC_DISABLED_EXISTS:
         case SD_DISABLED_FC_ENABLED_EXISTS:
            return existBothSide;

         case OC_ACCOUNT_NOT_EXISTS:
            return notExistOnAnySide;
         case OC_ACCOUNT_EXISTS:
         case SD_ACCOUNT_MIGRATED:
         case FC_ACCOUNT_MIGRATED:
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.ACCOUNT_ALREADY_MIGRATED.errCode(),
                     IMSMigrationExceptionCodes.ACCOUNT_ALREADY_MIGRATED.errMsg());
         default:
            if (originatingSource == Merchant.FREECHARGE
                     && currentState == State.SD_ACCOUNT_EXISTS_AND_DISABLED) {
               return existSDSide;
            } else if (originatingSource == Merchant.SNAPDEAL
                     && currentState == State.FC_ACCOUNT_EXISTS_AND_DISABLED) {
               return existFCSide;
            } else {
               throw new IMSServiceException(
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
            }
      }
   }

   public UserAccountUtil getAccountUtil(Merchant merchant) {
      switch (merchant) {
         case SNAPDEAL:
            return sdUserAccount;
         case FREECHARGE:
            return fcUserAccount;
         case ONECHECK:
            return ocUserAccount;
         default:
            throw new IMSServiceException(
                     IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                     IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }

   public UserAccountUtil getAccountUtil() {
      return getAccountUtil(merchantProvider.getMerchant());
   }
}