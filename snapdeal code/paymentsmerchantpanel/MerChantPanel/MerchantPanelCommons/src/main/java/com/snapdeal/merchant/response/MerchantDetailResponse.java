package com.snapdeal.merchant.response;


import lombok.Data;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantContactInfo;
import com.snapdeal.merchant.dto.MerchantUploadedDocsDTO;

@Data
public class MerchantDetailResponse extends AbstractResponse {

   private static final long serialVersionUID = 1L;
   
   private MerchantBankInfo bankInfo;
   
   private MerchantBusinessInfo businessInfo;
   
   private List<MerchantUploadedDocsDTO> uploadedDocDTO; 

}
