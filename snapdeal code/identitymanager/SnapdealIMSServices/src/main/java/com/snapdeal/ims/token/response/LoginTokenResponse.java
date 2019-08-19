/**
 * LoginTokenResponse.java
 */
package com.snapdeal.ims.token.response;

import java.util.Date;

import com.snapdeal.ims.response.AbstractResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response class with token and tokenExpiry.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LoginTokenResponse extends AbstractResponse {

    private static final long serialVersionUID = 2117128900347062448L;
    
    private String token;
    private Date tokenExpiry;

	private String globalToken;
	private Date globalTokenExpiry;
}