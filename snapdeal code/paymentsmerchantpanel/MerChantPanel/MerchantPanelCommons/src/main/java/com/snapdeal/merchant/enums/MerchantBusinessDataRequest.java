package com.snapdeal.merchant.enums;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.request.AbstractMerchantRequest;
import com.snapdeal.mob.request.GetUIDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewAbstractRequest;

import lombok.Data;

@Data
public class MerchantBusinessDataRequest extends AbstractMerchantRequest{
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message="TYPE_IS_BLANK")
	 private String type;
	
	 @NotBlank(message="PARENT_IS_BLANK")
	 private String parent;
	 
	 private String integrationMode;

}
