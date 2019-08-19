package com.snapdeal.merchant.request;

import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;

import lombok.Data;

@Data
public class MerchantUpdateDetailsRequest extends AbstractMerchantRequest {

	private static final long serialVersionUID = 1L;
	private MerchantBusinessInfo businessInformationDTO;
	private MerchantBankInfo bankAccountDetailsDTO;
}
