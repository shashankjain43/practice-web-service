package com.snapdeal.ims.token.service;

import com.snapdeal.ims.dto.AuthCodeInformationDTO;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.request.AuthCodeRequest;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;

public interface IGlobalTokenService {

	/**
	 * API to retrieve "userId", by passing a token. Following steps are carried
	 * out:
	 * <ol>
	 * <li>Decrypt token and get version service and get {@link TokenDetailsDTO}
	 * .</li>
	 * </ol>
	 * 
	 * @param clientId
	 *            TODO
	 * 
	 * @param data.token
	 * @return userId
	 */
	public String getUserIdByGlobalToken(String globalToken, String clientId);

	/**
	 * API used to create token and global token on login from a client and
	 * user-agent.
	 * 
	 * @param loginTokenRequest
	 * @return
	 */
	public TokenInformationDTO createTokenOnLogin(LoginTokenRequest loginTokenRequest);

	/**
	 * API used to create oauth code and global token on request for a merchant
	 * 
	 * @param authCodeRequest
	 * @return
	 */
	public AuthCodeInformationDTO createAuthCode(AuthCodeRequest authCodeRequest);

	/**
	 * API used to login with global token. This api will verify the token
	 * present in the request and then create a token associated for the client.
	 * 
	 * @param tokenRequest
	 * @return
	 */
	public TokenInformationDTO getTokenFromGlobalToken(TokenRequest tokenRequest);

	/**
	 * API used to great login
	 * 
	 * @param loginTokenRequest
	 * @return
	 */
	public boolean isTokenValid(TokenRequest request);

	public OAuthTokenInformationDTO getOAuthTokenInfo(String token);
}