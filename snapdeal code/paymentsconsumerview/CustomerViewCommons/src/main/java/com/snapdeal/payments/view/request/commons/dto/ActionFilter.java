package com.snapdeal.payments.view.request.commons.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.request.commons.enums.TxnStatus;

public @Data class ActionFilter {

	private Date startDate;
	private Date endDate;
	private String userId;
	private String otherPartyId;
	private List<ActionType> actionTypes;
	private List<TxnStatus> txnStatus;
	private String referenceId;
	private List<String> referenceTypes;
	private Integer limit = 50;
	private Date lastEvaluatedkey;
	private boolean isPrevious = false;

}
