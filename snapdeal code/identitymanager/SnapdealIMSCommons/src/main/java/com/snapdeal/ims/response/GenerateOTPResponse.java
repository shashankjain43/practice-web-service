package com.snapdeal.ims.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateOTPResponse extends AbstractResponse {

	private static final long serialVersionUID = 1L;
	private String otpId;
}
