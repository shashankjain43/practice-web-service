package com.snapdeal.opspanel.audit.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AuditRequest {

	private String email;
	@NotNull
	@NotBlank
	private String context;
	
	private String searchId;
	
	@NotBlank
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss ", timezone="IST" )
	private String startDate;
	
	@NotBlank
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss ", timezone="IST" )
	private String endDate;
	@NotNull
	private Integer pageSize;
	@NotNull
	private Integer offset;

}
