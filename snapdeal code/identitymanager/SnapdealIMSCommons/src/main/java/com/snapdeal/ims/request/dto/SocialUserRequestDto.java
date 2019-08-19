package com.snapdeal.ims.request.dto;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.MobileNumber;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString   
public class SocialUserRequestDto extends SocialUserDetailRequestDto
		implements Serializable {

	private static final long serialVersionUID = 4056158352168364886L;

   @MobileNumber
   private String mobileNumber;
	
   @Email
   private String emailId;

}