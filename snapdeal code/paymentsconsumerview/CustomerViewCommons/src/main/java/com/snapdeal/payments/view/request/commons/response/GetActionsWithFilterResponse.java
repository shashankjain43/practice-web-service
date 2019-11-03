package com.snapdeal.payments.view.request.commons.response;

import java.util.List;

import lombok.Data;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.request.commons.dto.ActionDto;

public @Data class GetActionsWithFilterResponse extends AbstractResponse {

	private static final long serialVersionUID = 7577134073913947872L;
	
	private List<ActionDto> actionsList ;
	
	private Long lastEvaluatedkey;

}