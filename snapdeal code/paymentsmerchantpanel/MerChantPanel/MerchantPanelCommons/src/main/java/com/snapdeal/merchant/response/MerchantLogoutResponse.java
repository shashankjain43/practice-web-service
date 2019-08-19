package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantLogoutResponse extends AbstractResponse{

	private boolean success;
}
