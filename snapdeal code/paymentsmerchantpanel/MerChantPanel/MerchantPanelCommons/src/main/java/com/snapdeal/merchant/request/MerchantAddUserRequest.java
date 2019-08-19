package com.snapdeal.merchant.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.annotation.Password;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.errorcodes.ErrorConstants;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude="password")
public class MerchantAddUserRequest extends AbstractMerchantRequest {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message=ErrorConstants.USER_NAME_IS_BLANK_CODE)
	private String userName;
	
	@NotBlank(message= ErrorConstants.LOGIN_NAME_IS_BLANK_CODE)
	private String loginName;
	
	@Email(mandatory = true)
	private String email;
	
	private String password;
	
	private List<MerchantRoleDTO> roleList;
	
}
