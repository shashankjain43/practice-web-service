package com.snapdeal.merchant.request;

import com.snapdeal.merchant.dto.MPSearch;

import lombok.Data;

@Data
public class MerchantGetSearchTransactionRequest extends AbstractMerchantRequest {

   private static final long serialVersionUID = 4101490595868797606L;

   private MPSearch searchCriteria;

   private int orderby = 1;
   
   private Integer page = 1;

   private Integer limit = 10;

}
