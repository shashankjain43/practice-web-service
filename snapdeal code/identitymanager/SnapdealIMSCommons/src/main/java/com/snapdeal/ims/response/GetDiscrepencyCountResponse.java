package com.snapdeal.ims.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetDiscrepencyCountResponse  extends AbstractResponse {

	private static final long serialVersionUID = -2729271756810755378L;
	Integer sdNullCount;
	Integer fcNullCount;
	Integer sdFcNullcount;

}
