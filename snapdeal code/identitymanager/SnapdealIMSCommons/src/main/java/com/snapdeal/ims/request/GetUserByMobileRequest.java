package com.snapdeal.ims.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.validator.annotation.MobileNumber;

/**
 * @author shachi
 *
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)
public class GetUserByMobileRequest extends AbstractRequest {

	private static final long serialVersionUID = -3701658681071802563L;
	@MobileNumber(mandatory=true)
	private String mobileNumber;

}
