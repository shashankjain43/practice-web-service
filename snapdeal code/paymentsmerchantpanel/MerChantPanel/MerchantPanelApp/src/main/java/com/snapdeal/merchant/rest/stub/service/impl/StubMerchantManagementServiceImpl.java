package com.snapdeal.merchant.rest.stub.service.impl;

import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantContactInfo;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;
import com.snapdeal.merchant.rest.service.IMerchantManagementService;

@Service
public class StubMerchantManagementServiceImpl implements IMerchantManagementService {

   @Override
   public MerchantDetailResponse getMerchantDetails(MerchantProfileRequest request)
            throws MerchantException {

      MerchantDetailResponse response = new MerchantDetailResponse();
      response.setBankInfo(createBankInfo());
      response.setBusinessInfo(createBusinessInfo());
      response.setContactInfo(createContactInfo());

      return response;

   }

   public MerchantContactInfo createContactInfo() {
      MerchantContactInfo contactInfo = new MerchantContactInfo();
      contactInfo.setAddesss("addesss");
      contactInfo.setLandLine("011909090");
      contactInfo.setMobile("90909090");
      contactInfo.setPrimaryEmail("primaryEmail@gamil.com");
      contactInfo.setSecondaryEmail("SecondaryEmail@gamil.com");
      return contactInfo;

   }

   public MerchantBankInfo createBankInfo() {
      MerchantBankInfo bankInfo = new MerchantBankInfo();
      bankInfo.setBankAccount("0010110101");
      bankInfo.setBankName("bankName");
      bankInfo.setIfsc("bn0001");
      return bankInfo;
   }

   public MerchantBusinessInfo createBusinessInfo() {
      MerchantBusinessInfo businessInfo = new MerchantBusinessInfo();
      businessInfo.setBusinessCategory("businessCategory");
      businessInfo.setBusinessType("businessType");
      businessInfo.setSubCategory("subCategory");
      businessInfo.setTin("tin");
      return businessInfo;
   }

   @Override
   public MerchantRoleResponse getMerchantRoles(MerchantRoleRequest request)
            throws MerchantException {
      // TODO Auto-generated method stub
      return null;
   }

}
