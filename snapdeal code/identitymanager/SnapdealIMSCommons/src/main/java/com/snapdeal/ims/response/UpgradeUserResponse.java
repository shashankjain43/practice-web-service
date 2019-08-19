package com.snapdeal.ims.response;

import com.snapdeal.ims.dto.UserDetailsDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UpgradeUserResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;
	private boolean success;
   private UserDetailsDTO userDetails;
}