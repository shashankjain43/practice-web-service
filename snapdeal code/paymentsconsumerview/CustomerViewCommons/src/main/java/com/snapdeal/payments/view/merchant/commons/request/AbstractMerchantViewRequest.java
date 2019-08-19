package com.snapdeal.payments.view.merchant.commons.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;
import com.snapdeal.payments.view.commons.request.AbstractRequest;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonPropertyOrder(alphabetic = true)
public class AbstractMerchantViewRequest extends AbstractRequest {

   private static final long serialVersionUID = 5800720770929744482L;

   @NotBlank(message = PaymentsViewExceptionConstants.MERCHANT_ID_IS_BLANK)
   private String merchantId;
   @Min(value = 1,message = PaymentsViewExceptionConstants.MIN_PAGE_SIZE)
   private Integer page = 1;
   
   @Max(value=1000,message=PaymentsViewExceptionConstants.MAX_LIMIT)
   private Integer limit = 50;
}
