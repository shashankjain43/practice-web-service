package com.snapdeal.ims.objectMapper;


import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.dbmapper.entity.SocialUser;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;
import com.snapdeal.ims.enums.AccountOwner;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.utils.DateUtil;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class IMSServiceObjectsMapper {

	// Request Object to Entity object mappers

	public static User mapUserDetailsByEmailRequestDtoToUser(
			UserDetailsByEmailRequestDto requestUserDetails,
			Merchant originatingSource) {

		User user = mapUserDetailsRequestDtoToUser(requestUserDetails,
				originatingSource);
		user.setEmailId(requestUserDetails.getEmailId());
		user.setPassword(requestUserDetails.getPassword());
		// set default values

		if (user.getStatus() == null) {
			user.setStatus(UserStatus.UNVERIFIED);
		}

		if (user.getLanguagePref() == null) {
			user.setLanguagePref(Language.ENGLISH);
		}

		if (requestUserDetails.getPassword() != null) {
			user.setUserSetPassword(true);
		}
		return user;
	}

	public static User mapUserRequestDTOToUser(UserRequestDto userRequestDto,
			Merchant originatingSource) {

		User user = mapUserDetailsByEmailRequestDtoToUser(userRequestDto,
				originatingSource);
		user.setMobileNumber(userRequestDto.getMobileNumber());
		return user;
	}
	public static UserDetailsRequestDto getNewUserDetails(User user,UserDetailsRequestDto oldUser){
		UserDetailsRequestDto userCopy=new UserDetailsRequestDto();
		if(user.getDob() == null) {
			if(oldUser.getDob()!=null)
			userCopy.setDob(oldUser.getDob().toString());
			else
				userCopy.setDob(null);
		}
		else 
			userCopy.setDob(user.getDob().toString());
		if(user.getFirstName()==null){
		userCopy.setFirstName(oldUser.getFirstName());
		}
		else
			userCopy.setFirstName(user.getFirstName());
		if(user.getLastName()==null){
			userCopy.setLastName(oldUser.getLastName());
		}
		else
			userCopy.setLastName(user.getLastName());
		if(user.getDisplayName()==null){
		userCopy.setDisplayName(oldUser.getDisplayName());
		}
		else
			userCopy.setDisplayName(user.getDisplayName());
		if(user.getGender()==null){
		userCopy.setGender(oldUser.getGender());
		}
		else
			userCopy.setGender(user.getGender());
		if(user.getMiddleName()==null){
		userCopy.setMiddleName(oldUser.getMiddleName());
		}
		else
			userCopy.setMiddleName(user.getMiddleName());
		if(user.getLanguagePref()==null){
		userCopy.setLanguagePref(oldUser.getLanguagePref());
		}
		else
			userCopy.setLanguagePref(user.getLanguagePref());
		return userCopy;
	}
	
	public static UserDetailsRequestDto getUserDetails(User user){
		UserDetailsRequestDto userCopy=new UserDetailsRequestDto();
		if(user.getDob() != null) {
			userCopy.setDob(user.getDob().toString());
		}
		userCopy.setFirstName(user.getFirstName());
		userCopy.setLastName(user.getLastName());
		userCopy.setDisplayName(user.getDisplayName());
		userCopy.setGender(user.getGender());
		userCopy.setMiddleName(user.getMiddleName());
		userCopy.setLanguagePref(user.getLanguagePref());
		return userCopy;
	}
	
		public static User updateUserEntityFromUserDetailsRequestDto(
			UserDetailsRequestDto userDetails, User user) {

		user.setDisplayName(userDetails.getDisplayName());
		user.setDob(dateFromString(userDetails.getDob()));
		user.setFirstName(userDetails.getFirstName());
		user.setGender(userDetails.getGender());
		user.setLanguagePref(userDetails.getLanguagePref());
		user.setLastName(userDetails.getLastName());
		user.setMiddleName(userDetails.getMiddleName());
		return user;
	}

	public static User mapUserDetailsRequestDtoToUser(
			UserDetailsRequestDto userDetails, Merchant originatingSource) {

		User user = new User();

		user.setOriginatingSrc(originatingSource);
		user.setDisplayName(userDetails.getDisplayName());

		user.setDob(dateFromString(userDetails.getDob()));
		user.setFirstName(userDetails.getFirstName());
		user.setGender(userDetails.getGender());
		user.setLanguagePref(userDetails.getLanguagePref());
		user.setLastName(userDetails.getLastName());
		user.setMiddleName(userDetails.getMiddleName());
		
		return user;
	}

	public static SocialUser mapCreateSocialUserRequestToSocialUser(
			SocialUserRequestDto socialUserDetails) {
		SocialUser socialUser = new SocialUser();

		socialUser.setAboutMe(socialUserDetails.getAboutMe());
		// TODO: socialUser.setExpiry(new Timestamp());
		socialUser.setPhotoURL(socialUserDetails.getPhotoURL());
		socialUser.setSecret(socialUserDetails.getSocialSecret());
		socialUser.setSocialId(socialUserDetails.getSocialId());
		socialUserDetails.setSocialSrc(StringUtils.upperCase(socialUserDetails.getSocialSrc()));
      socialUser.setSocialSrc(SocialSource.forName(socialUserDetails.getSocialSrc()));
		socialUser.setSocialToken(socialUserDetails.getSocialToken());

		return socialUser;
	}

   public static User mapCreateSocialUserRequestToUser(SocialUserRequestDto SocialUserRequestDto,
            Merchant originatingSource) {
      User user = new User();
      return mapSocialUserRequestToUser(SocialUserRequestDto, originatingSource, user);
   }

   public static User mapSocialUserRequestToUser(SocialUserRequestDto socialUserRequestDto,
            Merchant originatingSource, User user) {
      user.setOriginatingSrc(originatingSource);
      user.setDisplayName(socialUserRequestDto.getDisplayName());

      user.setDob(dateFromString(socialUserRequestDto.getDob()));

      user.setMobileNumber(socialUserRequestDto.getMobileNumber());
      user.setEmailId(socialUserRequestDto.getEmailId());
      user.setFirstName(socialUserRequestDto.getFirstName());
      user.setGender(socialUserRequestDto.getGender());
      user.setLanguagePref(socialUserRequestDto.getLanguagePref());
      user.setLastName(socialUserRequestDto.getLastName());
      user.setMiddleName(socialUserRequestDto.getMiddleName());
      // set google fb flag
      if (SocialSource.forName(socialUserRequestDto.getSocialSrc()) == SocialSource.GOOGLE) {
         user.setGoogleUser(true);
      } else if (SocialSource.forName(socialUserRequestDto.getSocialSrc()) == SocialSource.FACEBOOK) {
         user.setFacebookUser(true);
      }
      // set default values
      if (user.getStatus() == null) {
         user.setStatus(UserStatus.REGISTERED);
      }

      if (user.getLanguagePref() == null) {
         user.setLanguagePref(Language.ENGLISH);
      }

      return user;
   }
   
   public static User mapUpdateUserByIdRequestToUser(
            UpdateUserByIdRequest request, Merchant originatingSource) {


      UserDetailsRequestDto userDetails = request.getUserDetailsRequestDto();
      User user = mapUserDetailsRequestDtoToUser(userDetails,originatingSource);
      
      //remove default Values
      
      return user;

   }

   public static User mapUpdateUserByTokenRequestToUser(

            UpdateUserByTokenRequest request, Merchant originatingSource) {

      UserDetailsRequestDto userDetails = request.getUserDetailsRequestDto();
      User user = mapUserDetailsRequestDtoToUser(userDetails,originatingSource);
      
      //remove default Values
      
      return user;

   }
	// Object to object mappers
	public static UserDetailsDTO mapUserToUserDetailsDTO(User user) {
	   
		UserDetailsDTO userDetails = new UserDetailsDTO();

      Integer sdUserId = (user.getSdUserId() == null ? 0 : user.getSdUserId());
      Integer fcUserId = (user.getFcUserId() == null ? 0 : user.getFcUserId());
      Integer sdFcUserId = (user.getSdFcUserId() == null ? 0 : user.getSdFcUserId());
      
      if (user.getOriginatingSrc() != null) {
         userDetails.setAccountOwner(getAccountOwnerForUser(user));
      }
      userDetails.setDisplayName(user.getDisplayName());
		userDetails.setDob(formatDate(user.getDob()));
		userDetails.setEmailId(user.getEmailId());
		userDetails.setEmailVerified(user.isEmailVerified());
		userDetails.setFirstName(user.getFirstName());
		userDetails.setGender(user.getGender());
		userDetails.setLanguagePref(user.getLanguagePref());
		userDetails.setLastName(user.getLastName());
		userDetails.setMiddleName(user.getMiddleName());
		userDetails.setMobileNumber(user.getMobileNumber());
		userDetails.setMobileVerified(user.isMobileVerified());
		userDetails.setUserId(user.getUserId());
		userDetails.setAccountState(user.getStatus().getValue());
		userDetails.setEnabledState(user.isEnabled());
		userDetails.setMobileOnly(user.isMobileOnly());
		if(user.getWalletStatus() != null)
		userDetails.setWalletStatus(user.getWalletStatus().toString());
      if (sdUserId <= 0) {
			userDetails.setSdUserId(Integer.valueOf(sdFcUserId));
		} else {
			userDetails.setSdUserId(Integer.valueOf(sdUserId));
		}
      if (fcUserId <= 0) {
			userDetails.setFcUserId(Integer.valueOf(sdFcUserId));
		} else {
			userDetails.setFcUserId(Integer.valueOf(fcUserId));
		}
		if (user.getCreatedTime() != null) {
			userDetails.setCreatedTime(new Timestamp(user.getCreatedTime()
					.getTime()));
		}
		return userDetails;

	}
	
   private static AccountOwner getAccountOwnerForUser(User user) {
      Integer sdUserId = (user.getSdUserId() == null ? 0 : user.getSdUserId());
      Integer fcUserId = (user.getFcUserId() == null ? 0 : user.getFcUserId());
      Merchant originatingSrc = user.getOriginatingSrc();
      switch (originatingSrc) {
         case SNAPDEAL:
            if (sdUserId <= 0 && fcUserId <= 0) {
               return AccountOwner.ONE_CHECK;
            } else if (sdUserId <= 0 && fcUserId > 0) {
               return AccountOwner.FC;
            } else {
               return AccountOwner.SD;
            }
         case FREECHARGE:
            if (sdUserId <= 0 && fcUserId <= 0) {
               return AccountOwner.ONE_CHECK;
            } else if (sdUserId > 0 && fcUserId <= 0) {
               return AccountOwner.SD;
            } else {
               return AccountOwner.FC;
            }
         case ONECHECK:
            return AccountOwner.ONE_CHECK;
         default:
            throw new IMSServiceException(
                     IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                     IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }

   }

	// Entity to response object mappers
	public static CreateUserResponse mapUserToCreateUserResponse(User user) {

		CreateUserResponse response = new CreateUserResponse();
		response.setUserDetails(mapUserToUserDetailsDTO(user));
		return response;

	}

	public static SocialUserResponse mapSocialUserToSocialUserResponse(
SocialUser socialUser,
            User user) {

      SocialUserResponse response = new SocialUserResponse();

      if (socialUser == null || user == null) {
			return response;
		}
		
		UserDetailsDTO userDetails = mapUserToUserDetailsDTO(user);
		if(socialUser.getSocialSrc() !=null){
			if(socialUser.getSocialSrc() ==SocialSource.FACEBOOK){
				userDetails.setFbSocialId(socialUser.getSocialId());
			}else if(socialUser.getSocialSrc() ==SocialSource.GOOGLE){
				userDetails.setGoogleSocialId(socialUser.getSocialId());
			}			
		}
		response.setUserDetails(userDetails);

		UserSocialDetailsDTO userSocialDetails = new UserSocialDetailsDTO();

		userSocialDetails.setAboutMe(socialUser.getAboutMe());
		userSocialDetails.setPhotoURL(socialUser.getPhotoURL());
		userSocialDetails.setSocialId(socialUser.getSocialId());
		userSocialDetails.setSocialSource(socialUser.getSocialSrc());

		response.setUserSocialDetails(userSocialDetails);

		return response;
	}
	
	

	public static UserDTO mapUserToUserDTO(User user) {

		return mapUserToUserDTO(user, true);
	}
    
    public static UserDTO mapUserToUserDTO(User user,boolean setSDFCId) {
        UserDTO dto = new UserDTO();
     
      if (user.getOriginatingSrc() != null) {
         dto.setAccountOwner(getAccountOwnerForUser(user));
      }
        dto.setEmailId(user.getEmailId());
        dto.setUserId(user.getUserId());
        if(setSDFCId){
        	setSDFCId(user, dto);	
        }
        
        if (user.getCreatedTime() != null) {
            dto.setCreatedTime(new Timestamp(user.getCreatedTime()
                    .getTime()));
        }
        dto.setMobileNumber(user.getMobileNumber());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setDisplayName(user.getDisplayName());
        dto.setGender(user.getGender());
        dto.setDob(formatDate(user.getDob()));
        dto.setLanguagePref(user.getLanguagePref());
        dto.setMobileVerified(user.isMobileVerified());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setEnabledState(user.isEnabled());
        dto.setMobileOnly(user.isMobileOnly());
        if(user.getWalletStatus() != null)
        dto.setWalletStatus(user.getWalletStatus().toString());
        if(user.getStatus() != null)
        {
        	dto.setAccountState(user.getStatus().getValue());	
        }
        return dto;
    }

	private static void setSDFCId(User user, UserDTO dto) {
		if (user.getSdUserId() == null || user.getSdUserId() <= 0) {
            dto.setSdUserId(Integer.valueOf(user.getSdFcUserId()));
        } else {
            dto.setSdUserId(Integer.valueOf(user.getSdUserId()));
        }
        if (user.getFcUserId() == null || user.getFcUserId() <= 0) {
            dto.setFcUserId(Integer.valueOf(user.getSdFcUserId()));
        } else {
            dto.setFcUserId(Integer.valueOf(user.getFcUserId()));
        }
	}


	public static GetUserResponse mapUserToGetUserResponse(User user) {
		GetUserResponse response = new GetUserResponse();
		response.setUserDetails(mapUserToUserDetailsDTO(user));
		return response;
	}

   public static User mapUpgradeDtoToUser(User userFromDb, UpgradeDto upgradeDto) {
     userFromDb.setEmailId(upgradeDto.getEmailId());
     userFromDb.setPassword(upgradeDto.getFinalOCPassword());
     userFromDb.setUserSetPassword(true);
     userFromDb.setMobileNumber(upgradeDto.getMobileNumber());
     userFromDb.setFirstName(upgradeDto.getFirstName());
     userFromDb.setMiddleName(upgradeDto.getMiddleName());
     userFromDb.setLastName(upgradeDto.getLastName());
     userFromDb.setDisplayName(upgradeDto.getDisplayName());
     userFromDb.setDob(dateFromString(upgradeDto.getDob()));
     userFromDb.setLanguagePref(upgradeDto.getLanguagePref());
     userFromDb.setSdUserId((int) upgradeDto.getSdId());
     userFromDb.setFcUserId((int) upgradeDto.getFcId());
      return userFromDb;
   }
   
   private static Date dateFromString(String dateOfBirth)
   {
	   // causing bug SNAPDEALTECH-53754 hencee using StringUtils.isBlank
      if (StringUtils.isBlank(dateOfBirth)) {
         return null;
      }

      Date dob = DateUtil.dateFromString(dateOfBirth, CommonConstants.DATE_FORMAT);

      Calendar calendar18YrsBefore = Calendar.getInstance();
      calendar18YrsBefore.add(Calendar.YEAR, -18);

      if (dob.after(calendar18YrsBefore.getTime()))
         return null;

      return dob;
   }
   
   private static String formatDate(Date dateOfBirth)
   {
      if (dateOfBirth == null) {
         return null;
      }
      
      return DateUtil.formatDate(dateOfBirth, CommonConstants.DATE_FORMAT);
   }

   public static User mapUpgradeDtoToUser(UpgradeDto upgradeDto) {
      User user=new User();
      user.setEmailId(upgradeDto.getEmailId());
      user.setPassword(upgradeDto.getFinalOCPassword());
      user.setUserSetPassword(true);
      user.setMobileNumber(upgradeDto.getMobileNumber());
      user.setFirstName(upgradeDto.getFirstName());
      user.setMiddleName(upgradeDto.getMiddleName());
      user.setLastName(upgradeDto.getLastName());
      user.setDisplayName(upgradeDto.getDisplayName());
      user.setDob(dateFromString(upgradeDto.getDob()));
      user.setLanguagePref(upgradeDto.getLanguagePref());
      user.setSdUserId((int) upgradeDto.getSdId());
      user.setFcUserId((int) upgradeDto.getFcId());
      user.setGender(upgradeDto.getGender());
      if (user.getLanguagePref() == null) {
         user.setLanguagePref(Language.ENGLISH);
      }
      if (upgradeDto.getFinalOCPassword() != null) {
         user.setUserSetPassword(true);
      }
      user.setStatus(upgradeDto.getUserStatus());
      return user;
   }
   
   public static BlackList mapBlacklistEntityRequestToBlackList(BlacklistEntityRequest request) {

      BlackList blackList = new BlackList();
      blackList.setEntity(request.getEntity());
      blackList.setEntityType(request.getBlackListType());

      return blackList;
   }

}
