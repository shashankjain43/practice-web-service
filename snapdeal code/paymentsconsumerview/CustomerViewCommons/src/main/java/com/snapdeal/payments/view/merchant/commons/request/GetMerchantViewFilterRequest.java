package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;

import javax.validation.Valid;

import com.snapdeal.payments.view.commons.enums.ClientStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetMerchantViewFilterRequest extends AbstractMerchantViewRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @Valid
   private MerchantViewFilters filters;
   
   private int orderby= 1; //1 for descending(default) and ascending for others on txn_date

}
