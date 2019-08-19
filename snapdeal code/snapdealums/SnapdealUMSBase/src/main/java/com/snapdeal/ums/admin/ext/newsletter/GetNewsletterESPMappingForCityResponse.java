
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterEspMappingSRO;

public class GetNewsletterESPMappingForCityResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1750030921785740492L;
	@Tag(5)
    private NewsletterEspMappingSRO getNewsletterESPMappingForCity;

    public GetNewsletterESPMappingForCityResponse() {
    }

    public GetNewsletterESPMappingForCityResponse(NewsletterEspMappingSRO getNewsletterESPMappingForCity) {
        super();
        this.getNewsletterESPMappingForCity = getNewsletterESPMappingForCity;
    }

    public NewsletterEspMappingSRO getGetNewsletterESPMappingForCity() {
        return getNewsletterESPMappingForCity;
    }

    public void setGetNewsletterESPMappingForCity(NewsletterEspMappingSRO getNewsletterESPMappingForCity) {
        this.getNewsletterESPMappingForCity = getNewsletterESPMappingForCity;
    }

}
