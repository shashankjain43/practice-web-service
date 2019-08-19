package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import com.snapdeal.payments.view.commons.response.AbstractResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MerchantViewServiceResponse  extends AbstractResponse{
	
	private static final long serialVersionUID = 1L;
	private List<MerchantViewResponse> response;
	
}
