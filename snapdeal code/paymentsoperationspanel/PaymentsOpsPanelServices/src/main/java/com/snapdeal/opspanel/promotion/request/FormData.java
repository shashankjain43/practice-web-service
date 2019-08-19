package com.snapdeal.opspanel.promotion.request;

import java.sql.Timestamp;

import lombok.Data;

import com.snapdeal.opspanel.promotion.enums.InstrumentType;

@Data
public class FormData {
	
	private String eventContex;
	private String businessEntity;
	private String corpId;
	private String activity;
	private InstrumentType instrument;
	private String id_type;
	private Boolean isPark;
	private String idempotencyId;
	private String smsTemplateId;
	private String emailTemplateId;
	private Timestamp uploadTimestamp;
	private Boolean isEmailSuppressed;
	private Boolean isSMSSuppressed;
	private Boolean isWalletNotificationSuppressed;
	private String fileName;
	
}
