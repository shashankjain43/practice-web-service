package com.snapdeal.payments.view.commons.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;

@Getter
@Setter
public class CreateClientRequest extends AbstractRequest {
   
   private static final long serialVersionUID = -874147784843776198L;

   @NotBlank(message = PaymentsViewConstants.CLIENT_NAME_IS_BLANK)
   @Size(max = 128, message = PaymentsViewConstants.CLIENT_NAME_MAX_LENGTH)
   private String clientName;
   

}
