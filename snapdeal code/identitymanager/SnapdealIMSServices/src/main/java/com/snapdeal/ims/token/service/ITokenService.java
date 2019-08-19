package com.snapdeal.ims.token.service;

import com.snapdeal.ims.dto.AccessTokenInformationDTO;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.request.AccessTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;

public interface ITokenService {

	/**
	 * API to retrieve "userId", by passing a token.
	 * 
	 * @param token
	 *            : String
	 * @return userId : {@link String}
	 */
	public String getUserIdByToken(String token);

	/**
	 * API to retrieve "userId", by passing a token.
	 * 
	 * @param token
	 *            : String
	 * @return userId : {@link String}
	 */
	public String getUserIdByToken(String token, boolean isOCLinkFlow);

	/**
	 * API to retrieve GlobalTokenDetailsEntity , by passing a local token.
	 * 
	 * @param token
	 *            : String
	 * @return {@link GlobalTokenDetailsEntity}
	 */
	public GlobalTokenDetailsEntity getGlobalTokenDetailsForToken(String token);

	/**
	 * API to retrive TokenDetails from local token
	 * 
	 * @param token
	 * @return
	 */
	public TokenDetailsDTO getTokenDetailsForToken(String token);

	/**
	 * API to generate token for a global token. This api will be called for
	 * each client logging in with global token.
	 * 
	 * @param globalTokenDetails
	 *            : {@link GlobalTokenDetailsDTO}
	 * @param clientId
	 *            : {@link String}
	 * 
	 * @param isLinkUpgradeFlow
	 *            : boolean
	 * @return
	 */
	public String generateTokenForGlobalToken(GlobalTokenDetailsDTO globalTokenDetails, String clientId,
			boolean isLinkUpgradeFlow);

	/**
	 * API to validate token.
	 * 
	 * @param request
	 *            : {@link TokenRequest}
	 * @return
	 */
	public boolean isTokenValid(TokenRequest request);

	/**
	 * API to invalidate/retire a token for no future use.
	 * 
	 * @param signoutRequest
	 *            : {@link SignoutRequest}
	 * @return {@link SignoutResponse}
	 */
	public SignoutResponse signOut(SignoutRequest signoutRequest);

	/**
	 * API to signout user based on userId from all device.
	 * 
	 * @param true/false:
	 *            success / failure
	 * @return {@link SignoutResponse}
	 */
	public boolean signOutUser(String userId);

	/**
	 * API to invalidate/retire a token for no future use retaining the global
	 * token of current request.
	 * 
	 * @param signoutRequest
	 *            : {@link SignoutRequest}
	 * @return {@link SignoutResponse}
	 */
	boolean signoutAllOtherTokens(String token);

	/**
	 * API to provide a handshake token when user goes from Snapdeal to one
	 * check. This api will be used to get the transfer token everytime the user
	 * clicks One Check Wallet to complete the handshake.
	 * 
	 * @param token
	 *            : {@link String}
	 * @return {@link GetTransferTokenResponse}
	 */
	public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request);

	/**
	 * Fetch Token version after decrypting token.
	 * 
	 * @param token
	 * @return
	 */
	public String getTokenVersion(String token);

	public int getGTokenIDSetSizeByUserId(String userId);

	public AccessTokenInformationDTO getAccessTokenFromAuthCode(AccessTokenRequest request);

	public OAuthTokenInformationDTO getOAuthTokenInfo(String token);
}