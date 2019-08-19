package com.snapdeal.opspanel.promotion.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class BulkPromotionRequest {

	@NotBlank( message= "businessEntity cannot be left blank !")
	private String businessEntity;

	@NotBlank( message= "corpId cannot be left blank !")
	private String corpId;

	@NotBlank( message= "activity cannot be left blank !")
	private String activity;

	@NotBlank( message= "instrument cannot be left blank ! ")
	private String instrument;

	@NotBlank( message= "id_type cannot be left blank ! ")
	private String id_type;

	@NotBlank( message= "idempotencyId cannot be left blank !")
	private String idempotencyId;

	@NotNull( message = "isPark cannot be null !")
	private Boolean isPark;

	@NotBlank( message= "smsTemplateId cannot be left blank !")
	private String smsTemplateId;

	@NotBlank( message= "emailTemplateId cannot be left blank !")
	private String emailTemplateId;

	@NotNull( message= "isEmailSuppressed cannot be blank !")
	private Boolean isEmailSuppressed;

	@NotNull( message = "isSMSSuppressed cannot be blank !" )
	private Boolean isSMSSuppressed;

	@NotNull( message = "isWalletNotificationSuppressed !" )
	private Boolean isWalletNotificationSuppressed;

}
