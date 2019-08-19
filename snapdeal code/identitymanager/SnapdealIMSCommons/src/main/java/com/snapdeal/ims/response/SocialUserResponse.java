package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class SocialUserResponse extends GetUserResponse {

   private static final long serialVersionUID = -5452165715895247359L;
   private UserSocialDetailsDTO userSocialDetails;
   private TokenInformationDTO tokenInformation;
   private UpgradationInformationDTO upgradationInformation;
}