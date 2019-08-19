package com.snapdeal.ims.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserStatusRequest extends AbstractRequest{
	@NotBlank
private String emailId;	
}
