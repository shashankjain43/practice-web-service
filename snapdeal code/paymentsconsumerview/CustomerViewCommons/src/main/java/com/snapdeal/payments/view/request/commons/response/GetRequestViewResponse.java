package com.snapdeal.payments.view.request.commons.response;

import java.util.List;

import lombok.Data;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.request.commons.dto.RVTransactionDto;

public @Data class GetRequestViewResponse  extends AbstractResponse{

	private static final long serialVersionUID = 1L;
		
	private List<RVTransactionDto> requestViewTransactionDto ;
}
