package com.snapdeal.payments.view.commons.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.enums.ClientStatus;

@Setter
@Getter
@ToString(exclude={"clientKey"})
public class Client implements Serializable {

	private static final long serialVersionUID = -2304911600307800509L;

	private String id;
	private String clientName; 
	private String clientKey; 
	private ClientStatus clientStatus;
	private Date createdTime;
	private Date updatedTime;
}