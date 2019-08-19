package com.snapdeal.ims.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.snapdeal.ims.dto.UpgradationInformationDTO;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpgradationResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;

	private UpgradationInformationDTO upgradationInformation ;
}