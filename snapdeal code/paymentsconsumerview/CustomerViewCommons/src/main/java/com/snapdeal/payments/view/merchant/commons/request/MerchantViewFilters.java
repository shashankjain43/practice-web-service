package com.snapdeal.payments.view.merchant.commons.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)
public class MerchantViewFilters implements Serializable {

   private static final long serialVersionUID = 799172098753697890L;

   private List<MVTransactionType> txnTypeList; // filter
   private List<MVTransactionStatus> txnStatusList; // filter
   private BigDecimal fromAmount;
   private BigDecimal toAmount;
   private Date startDate;
   private Date endDate;
   
   private String merchantTag ;
}
