
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;

public class GetBulkEspCityMappingForCityResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2330960785406913228L;
	@Tag(5)
    private EmailBulkEspCityMappingSRO getBulkEspCityMappingForCity;

    public GetBulkEspCityMappingForCityResponse() {
    }

    public GetBulkEspCityMappingForCityResponse(EmailBulkEspCityMappingSRO getBulkEspCityMappingForCity) {
        super();
        this.getBulkEspCityMappingForCity = getBulkEspCityMappingForCity;
    }

    public EmailBulkEspCityMappingSRO getGetBulkEspCityMappingForCity() {
        return getBulkEspCityMappingForCity;
    }

    public void setGetBulkEspCityMappingForCity(EmailBulkEspCityMappingSRO getBulkEspCityMappingForCity) {
        this.getBulkEspCityMappingForCity = getBulkEspCityMappingForCity;
    }

}
