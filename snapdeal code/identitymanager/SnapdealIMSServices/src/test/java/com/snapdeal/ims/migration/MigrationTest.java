package com.snapdeal.ims.migration;

import static org.mockito.Matchers.eq;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.provider.RandomUpgradeChoiceUtil;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.token.service.impl.BaseTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Random;


public class MigrationTest extends BaseTest {

   @InjectMocks
   Migration migration;

   @Spy
   AuthorizationContext context = this.authContext;

   @Mock
   IPasswordUpgradeCacheService passwordCacheService;

   @Mock
   MigrationDao migrationDao;

   @Mock(name = "imsService")
   IUMSService imsService;

   @Mock(name = "umsServiceFC")
   IUMSService umsServiceFC;

   @Mock(name = "umsServiceSD")
   IUMSService umsServiceSD;
   
   @Mock(name="randomUpgradeChoiceUtil")
   RandomUpgradeChoiceUtil randomUpgradeChoiceUtil;

   @Mock
   private UmsMerchantProvider merchantProvider;

   @Before
   public void setup() {
      initConfig();
      MockitoAnnotations.initMocks(this);
   }

   /**
    * 
    * login from snapdeal but one check is not created
    * 
    * **/
   @Test
   public void getMigrationStatusTest__SNAPDEAL__SD_ACCOUNT_EXISTS_AND_ENABLED() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);
      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(null);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_ACCOUNT_EXISTS_AND_ENABLED);

   }

   @Test
   public void getMigrationStatusTest__SNAPDEAL__SD_ACCOUNT_EXISTS_AND_ENABLED_intermediate() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   
   public void getMigrationStatusTest__SNAPDEAL__SD_ACCOUNT_MIGRATED() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.SD_ACCOUNT_MIGRATED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);
      Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_ACCOUNT_MIGRATED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_COMPLETED);

   }

   @Test
   public void getMigrationStatusTest__SNAPDEAL__FC_ACCOUNT_MIGRATED() {
      Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.FC_ACCOUNT_MIGRATED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.FC_ACCOUNT_MIGRATED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.LINK_SD_ACCOUNT);

   }

   @Test
   
   public void getMigrationStatusTest__SNAPDEAL__OC_ACCOUNT_NOT_EXISTS() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);


      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   
   public void getMigrationStatusTest__SNAPDEAL__SD_FC_ACCOUNT_EXISTS_AND_ENABLED() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      // Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn();

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   public void getMigrationStatusTest__SNAPDEAL__SD_FC_ACCOUNT_EXISTS_AND_ENABLED_intermediate() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   
   public void getMigrationStatusTest__SNAPDEAL__SD_ENABLED_FC_DISABLED_EXISTS() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);


      // Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn();

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test(expected = IMSServiceException.class)
   
   public void getMigrationStatusTest__SNAPDEAL__SD_FC_OC_ACCOUNT_EXISTS_AND_ENABLED_migrationData_not_exist() {
      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);
   }

   @Test
   
   public void getMigrationStatusTest__SNAPDEAL__SD_FC_OC_ACCOUNT_MIGRATED() {
      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.OC_ACCOUNT_EXISTS);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.OC_ACCOUNT_EXISTS);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_COMPLETED);

   }

   /***
    * Login from Freecharge
    * **/
   @Test
   
   public void getMigrationStatusTest__FREECHARGE__FC_ACCOUNT_EXISTS_AND_ENABLED() {

      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(null);

      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(null);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   
   public void getMigrationStatusTest__FREECHARGE__FC_ACCOUNT_EXISTS_AND_ENABLED_intermediate() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.FC_ACCOUNT_EXISTS_AND_ENABLED);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Override
   public void changeClientMerchant(Merchant merchant) {
      Mockito.when(merchantProvider.getMerchant()).thenReturn(merchant);
      super.changeClientMerchant(merchant);
   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__FC_ACCOUNT_MIGRATED() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.FC_ACCOUNT_MIGRATED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);
      Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.FREECHARGE);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.FC_ACCOUNT_MIGRATED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_COMPLETED);

   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__OC_ACCOUNT_NOT_EXISTS() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__SD_ACCOUNT_MIGRATED() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.SD_ACCOUNT_MIGRATED);

      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);
      
      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_ACCOUNT_MIGRATED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.LINK_FC_ACCOUNT);

   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__SD_FC_ACCOUNT_EXISTS_AND_ENABLED() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      // Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn();

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__SD_FC_ACCOUNT_EXISTS_AND_ENABLED_intermediate() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test
   
   public void getMigrationStatusTest__FREECHARGE__FC_ENABLED_SD_DISABLED_EXISTS() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponseEnabled = new GetUserResponse();
      UserDetailsDTO userDetailsEnabled = new UserDetailsDTO();
      userDetailsEnabled.setMobileVerified(true);
      userDetailsEnabled.setEnabledState(true);
      getUserResponseEnabled.setUserDetails(userDetailsEnabled);
      
      GetUserResponse getUserResponseDisabled = new GetUserResponse();
      UserDetailsDTO userDetailsDisabled = new UserDetailsDTO();
      userDetailsEnabled.setMobileVerified(true);
      getUserResponseDisabled.setUserDetails(userDetailsDisabled);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponseDisabled);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponseEnabled);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      // Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn();

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.SD_DISABLED_FC_ENABLED_EXISTS);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_RECOMMENDED);

   }

   @Test(expected = IMSServiceException.class)
   
   public void getMigrationStatusTest__FREECHARGE__SD_FC_OC_ACCOUNT_EXISTS_AND_ENABLED_migrationData_not_exist() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

   }

   @Test
   public void getMigrationStatusTest__FREECHARGE__SD_FC_OC_ACCOUNT_MIGRATED() {
      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);

      Mockito.when(passwordCacheService.getUserUpgradeStatus(eq(email))).thenReturn(false);
      Mockito.when(randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(Matchers.any(String.class), eq(email))).thenReturn(true);

      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setCurrentState(State.OC_ACCOUNT_EXISTS);
      upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
      Mockito.when(migrationDao.getLatestUpgradeStatus(eq(email))).thenReturn(upgradeStatus);

      UpgradationInformationDTO response = migration.getMigrationStatus(email, false);

      Assert.assertEquals(response.getState(), State.OC_ACCOUNT_EXISTS);
      Assert.assertEquals(response.getUpgrade(), Upgrade.UPGRADE_COMPLETED);

   }

   @Test
   public void testUpgradePecentage() {

      Integer configUpgradePercentage = Integer.parseInt(Configuration
               .getGlobalProperty(ConfigurationConstants.UPGRADE_PERCENTAGE));

      int upgradeCount = 0;

      Random random = new Random();

      for (int i = 0; i < 100; i++) {
         String clientId = String.valueOf(random.nextInt(100));
         upgradeCount += randomUpgradeChoiceUtil
                  .isEmailToBePickedForUpgrade(clientId,clientId+"@abc.com") ? 1 : 0;
      }

      Assert.assertTrue(configUpgradePercentage >= upgradeCount);

   }

   /****
    * Signup with freecharge
    * ***/

   /*
    * Account doesn't exist anywhere
    */
   @Test
   public void getIMSMigrationStatusTest__FREECHARGE__OC_ACCOUNT_NOT_EXISTS() {

      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      UpgradeStatus response = migration.getIMSMigrationStatus(email);

      Assert.assertEquals(response.getInitialState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getCurrentState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getUpgradeStatus(), Upgrade.UPGRADE_RECOMMENDED);
   }


   /*
    * Account exist in sd and not in oc
    */
   @Test
   public void getIMSMigrationStatusTest__FREECHARGE__SD_ACCOUNT_EXISTS() {

      changeClientMerchant(Merchant.FREECHARGE);

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      UpgradeStatus response = migration.getIMSMigrationStatus(email);

      Assert.assertEquals(response.getInitialState(), State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getCurrentState(), State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgradeStatus(), Upgrade.UPGRADE_RECOMMENDED);
   }
   


   /****
    * Signup with snapdeal
    * ***/

   /*
    * Account doesn't exist anywhere
    */
   @Test
   public void getIMSMigrationStatusTest__SNAPDEAL__OC_ACCOUNT_NOT_EXISTS() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      UpgradeStatus response = migration.getIMSMigrationStatus(email);

      Assert.assertEquals(response.getInitialState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getCurrentState(), State.OC_ACCOUNT_NOT_EXISTS);
      Assert.assertEquals(response.getUpgradeStatus(), Upgrade.UPGRADE_RECOMMENDED);
   }


   /*
    * Account exist in fc and not in oc
    */
   @Test
   public void getIMSMigrationStatusTest__SNAPDEAL__FC_ACCOUNT_EXISTS() {

      String email = "abc@gmail.com";

      GetUserByEmailRequest getUserRequest = new GetUserByEmailRequest();
      getUserRequest.setEmailId(email);

      GetUserResponse getUserResponse = new GetUserResponse();
      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setMobileVerified(true);
      userDetails.setEnabledState(true);
      getUserResponse.setUserDetails(userDetails);

      Mockito.when(imsService.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceFC.getUserByEmail(eq(getUserRequest))).thenReturn(getUserResponse);
      Mockito.when(umsServiceSD.getUserByEmail(eq(getUserRequest))).thenReturn(null);
      UpgradeStatus response = migration.getIMSMigrationStatus(email);

      Assert.assertEquals(response.getInitialState(), State.FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getCurrentState(), State.FC_ACCOUNT_EXISTS_AND_ENABLED);
      Assert.assertEquals(response.getUpgradeStatus(), Upgrade.UPGRADE_RECOMMENDED);
   }
}
