package com.snapdeal.merchant.dto;

import lombok.Data;

@Data
public class MerchantBusinessInfo {

	private String businessType;	

	private String businessCategory;

	private String subCategory;

	private String tin;

	private String businessName;

	private String merchantName;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String pincode;

	private String stdCode;

	private String landLineNumber;

	private String primaryMobile;

	private String secondaryEmail;

	private String secondaryMobile;

	private String website;
	private String appName;

	private String dateOfFormation;

	private String logoUrl;

	private MerchantTDRDetailsDTO tdrDetailsDTO;

	private String velocityLimits;
	private String merchantReserves;

	private String shopName;

}
