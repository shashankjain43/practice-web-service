package com.snapdeal.ims.migration;

import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.migration.PostUpgradeStatusInfo.CreateOrUpdate;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.migration.util.UserAccountUtil;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.service.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("NotExistOnAnySide")
public class NotExistOnAnySide extends UserAccountExist {
   
   @Qualifier("ocUserAccount")
   @Autowired
   private UserAccountUtil ocUserAccount;
   
   @Autowired
   private IUserDao userDaoImpl;
   
   @Autowired
   private IUserCacheService userCacheService;

   
   @Override
   public PostUpgradeStatusInfo performTask(UserUpgradeRequest request, Merchant originatingSource,
            UpgradationInformationDTO upgradeInfo) {
      
      if(request.getUpgradeSource()==UpgradeSource.SIGN_UP){
         final PostUpgradeStatusInfo postUpgradeStatusInfo=new PostUpgradeStatusInfo();
         postUpgradeStatusInfo.setInitialState(upgradeInfo.getState());
         postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
         postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         /*	sendEmail(request.getEmailId(), 
    				ConfigurationConstants.WELCOME_USER_BY_EMAIL_TEMPLATE, false,
    				imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE,
    						ConfigurationConstants.SEND_EMAIL_WELCOME_EMAIL));*/
         setEmailData(postUpgradeStatusInfo, ConfigurationConstants.WELCOME_USER_BY_EMAIL_TEMPLATE,
                  ConfigurationConstants.SEND_EMAIL_WELCOME_EMAIL);
         return postUpgradeStatusInfo;
      }      
      
      /*Step0. Getting ocUserDto from OC User*/
      UserDTO ocUser = 
               IMSServiceObjectsMapper.mapUserToUserDTO(fetchUserFromOC(request.getEmailId()));
      
      /*Step1: Get upgradeDTO from ocUserDTO*/
      UpgradeDto upgradeDto = new UpgradeDto();
      upgradeDto = mapUserDetailsToUpgradeDTO(upgradeDto, ocUser);
      upgradeDto.setVerifiedType(request.getVerifiedType());
      
      /*Step2: Mark user to REGISTERED and email/mobile as verified*/
      // ocUserAccount.createUser(upgradeDto);
      final PostUpgradeStatusInfo postUpgradeStatusInfo=new PostUpgradeStatusInfo();
      postUpgradeStatusInfo.setInitialState(upgradeInfo.getState());
      postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
      postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
      postUpgradeStatusInfo.setUpgradeDto(upgradeDto);
      postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.OC_CREATE);
      return postUpgradeStatusInfo;
   }
   
   /**
    * 
    * @param email
    * @return
    */
   private User fetchUserFromOC(String mobileNumber) {
      try {
         return userDaoImpl.getUserByEmail(mobileNumber);
      } catch (Exception ex) {
         return null;
      }
   }
   
   /**
    * 
    * @param upgradeDto
    * @param targetDTO
    * @returnSD_FC_ACCOUNT_EXISTS_AND_ENABLED
    */
   private UpgradeDto mapUserDetailsToUpgradeDTO(UpgradeDto upgradeDto, UserDTO targetDTO) {
      upgradeDto.setEmailId(targetDTO.getEmailId());
      upgradeDto.setMobileNumber(targetDTO.getMobileNumber());
      upgradeDto.setFirstName(targetDTO.getFirstName());
      upgradeDto.setMiddleName(targetDTO.getMiddleName());
      upgradeDto.setLastName(targetDTO.getLastName());
      upgradeDto.setDisplayName(targetDTO.getDisplayName());
      upgradeDto.setGender(targetDTO.getGender());
      upgradeDto.setDob(targetDTO.getDob());
      upgradeDto.setLanguagePref(targetDTO.getLanguagePref());
      return upgradeDto;
   }

}
