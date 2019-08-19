package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import javax.validation.Valid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class GetMerchantViewSearchRequest extends AbstractMerchantViewRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @Valid
   private MerchantViewSearch searchCriteria;
   
   private int orderby= 1; //1 for descending(default) and ascending for others on txn_date
   
}
