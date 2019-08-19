
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;

public class UpdateRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 43918910334651383L;
	@Tag(3)
    private EmailBulkEspCityMappingSRO filterCityMapping;

    public UpdateRequest() {
    }

    public EmailBulkEspCityMappingSRO getFilterCityMapping() {
        return filterCityMapping;
    }

    public void setFilterCityMapping(EmailBulkEspCityMappingSRO filterCityMapping) {
        this.filterCityMapping = filterCityMapping;
    }

    public UpdateRequest(EmailBulkEspCityMappingSRO filterCityMapping) {
        this.filterCityMapping = filterCityMapping;
    }

}
