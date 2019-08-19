package com.snapdeal.ims.token.service;

import com.snapdeal.ims.token.dto.TokenMetadata;

/**
 * API for generating and validating token. This service will have various
 * version, and required for backward compatibility.
 */
public interface ITokenGenerationService<T extends TokenMetadata> {

    /**
	 * This is an utility method.
	 * 
	 * @param plainTokenString
	 * @return
	 */
	T getTokenDataFromDecryptedToken(String plainTokenString);

	/**
	 * Get plain text token formed by TokenVersionService.
	 * 
	 * @param loginTokenRequest
	 * @return
	 */
	String generatePlainToken(T tokenDetails);
}