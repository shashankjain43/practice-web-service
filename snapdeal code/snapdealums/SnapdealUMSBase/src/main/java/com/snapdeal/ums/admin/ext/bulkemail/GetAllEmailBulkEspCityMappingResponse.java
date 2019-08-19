
package com.snapdeal.ums.admin.ext.bulkemail;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;

public class GetAllEmailBulkEspCityMappingResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2815945095821937500L;
	@Tag(5)
    private List<EmailBulkEspCityMappingSRO> getAllEmailBulkEspCityMapping = new ArrayList<EmailBulkEspCityMappingSRO>();

    public GetAllEmailBulkEspCityMappingResponse() {
    }

    public GetAllEmailBulkEspCityMappingResponse(List<EmailBulkEspCityMappingSRO> getAllEmailBulkEspCityMapping) {
        super();
        this.getAllEmailBulkEspCityMapping = getAllEmailBulkEspCityMapping;
    }

    public List<EmailBulkEspCityMappingSRO> getGetAllEmailBulkEspCityMapping() {
        return getAllEmailBulkEspCityMapping;
    }

    public void setGetAllEmailBulkEspCityMapping(List<EmailBulkEspCityMappingSRO> getAllEmailBulkEspCityMapping) {
        this.getAllEmailBulkEspCityMapping = getAllEmailBulkEspCityMapping;
    }

}
