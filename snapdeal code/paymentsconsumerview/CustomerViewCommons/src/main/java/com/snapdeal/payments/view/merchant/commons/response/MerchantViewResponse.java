package com.snapdeal.payments.view.merchant.commons.response;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode 
public class MerchantViewResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Date transactionDate ;
    private Date settlementDate ;
    private String fcTransactionId ;
    private String merchantTransactionId ;
    private String settlementId ;
    private String orderId ;
    private String txnStatus ;
    private Double merchantFee ;
    private Double serviceTax ;
    private Double swachhBharatCess ;
    private Double totalTxnAmount ;
    private Double netDeduction ;
    private Double amountPayable ;
    private String merchantId ;
    private String storeName ;
    private String storeId ;
    private String terminalId ;
    private String customerId ;
    private String customerName ;
   private String productId ;
    private String merchantName ;
    private String customerIP ;
    private String location ;
    private String shippingCity ;
    private String platform ;
}
