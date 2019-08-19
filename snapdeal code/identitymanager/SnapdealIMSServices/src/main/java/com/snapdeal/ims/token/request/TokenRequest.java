package com.snapdeal.ims.token.request;

import com.snapdeal.ims.request.AbstractRequest;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TokenRequest extends AbstractRequest {

    private static final long serialVersionUID = 7119621223312433172L;
    @NotBlank
    private String token;

	@NotBlank
	private String clientId;

   private boolean linkUpgradeFlow;
}
