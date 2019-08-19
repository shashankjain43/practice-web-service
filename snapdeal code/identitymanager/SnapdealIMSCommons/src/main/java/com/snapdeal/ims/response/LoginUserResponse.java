package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;

@ToString
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginUserResponse extends AbstractResponse {

	private static final long serialVersionUID = -7365290884337207840L;

	private TokenInformationDTO tokenInformation;

	private UserDetailsDTO userDetails;

	private UpgradationInformationDTO upgradationInformation;

	private UserSocialDetailsDTO userSocialDetails;
}