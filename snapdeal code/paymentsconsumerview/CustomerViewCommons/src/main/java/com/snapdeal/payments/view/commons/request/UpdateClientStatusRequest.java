package com.snapdeal.payments.view.commons.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ClientStatus;

@Getter
@Setter
public class UpdateClientStatusRequest extends AbstractRequest {

	private static final long serialVersionUID = -4357989705456164711L;

	@NotNull(message = PaymentsViewConstants.CLIENT_STATUS_IS_NULL)
	private ClientStatus clientStatus;
	
	@NotNull(message = PaymentsViewConstants.CLIENT_NAME_IS_BLANK)
	private String clientName ;
	

}
