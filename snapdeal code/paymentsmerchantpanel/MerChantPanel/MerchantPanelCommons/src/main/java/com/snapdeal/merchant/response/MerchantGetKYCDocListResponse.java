package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantDocCategoryAndTypeDTO;

import lombok.Data;

@Data
public class MerchantGetKYCDocListResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;

	List<MerchantDocCategoryAndTypeDTO> merchantKycDocList;
}
