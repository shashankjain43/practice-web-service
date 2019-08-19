package com.snapdeal.ims.service;

import com.snapdeal.ims.request.GetDiscrepencyCountServiceRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesServiceRequest;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;

/**
 * 
 * @author radhika
 *
 */

public interface IDiscrepencyCountService {
	
	public GetDiscrepencyCountResponse getDiscrepencyCountForUsers(GetDiscrepencyCountServiceRequest request);

	public DiscrepencyCasesEmailResponse getAllEmailForDiscrepencyCases(GetEmailForDiscrepencyCasesServiceRequest request);

}


