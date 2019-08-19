package com.snapdeal.ims.entity;

import java.util.Date;

import com.snapdeal.ims.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearchEnteredEntity extends AbstractRequest {
	private static final long serialVersionUID = 714837881116422105L;
	private String userId;
	private int sdfc_user_id;
	private String name;
	private String email;
	private String mobile;
	private Date fromDate;
	private Date toDate;
}
