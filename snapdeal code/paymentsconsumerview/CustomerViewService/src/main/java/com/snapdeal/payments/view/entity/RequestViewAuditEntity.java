package com.snapdeal.payments.view.entity;

import lombok.Data;

public @Data class RequestViewAuditEntity extends PaymentsViewAuditEntity {

	public String sourceUserId;

}