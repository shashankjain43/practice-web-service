package com.snapdeal.ims.service;

import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;

public interface IBlackWhiteListService {

	public WhitelistEmailResponse WhitelistEmail(WhitelistEmailRequest request);

	public BlacklistEntityResponse addBlacklistEntity(
			BlacklistEntityRequest request);

	public BlacklistEntityResponse removeBlacklistEntity(
			BlacklistEntityRequest request);

}
