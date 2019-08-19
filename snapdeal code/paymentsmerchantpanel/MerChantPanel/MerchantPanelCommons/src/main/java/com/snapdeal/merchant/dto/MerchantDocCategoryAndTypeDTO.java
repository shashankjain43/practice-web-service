package com.snapdeal.merchant.dto;

import java.util.List;

import lombok.Data;

@Data
public class MerchantDocCategoryAndTypeDTO {

	String docCategory;
	private List<String> docType;
}
