package com.snapdeal.payments.view.request.commons.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.request.commons.dto.ActionDto;

public @Data class GetUserPendingActionsResponse extends AbstractResponse implements Serializable{

	private static final long serialVersionUID = -5400580223345395340L;
	
	private List<ActionDto> actionsList;
	
	private Long lastEvaluatedkey;

}
