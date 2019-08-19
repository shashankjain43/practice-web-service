
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;

public class UpdateResponse extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -890991685550952218L;
	@Tag(5)
    private EmailBulkEspCityMappingSRO update;

    public UpdateResponse() {
    }

    public UpdateResponse(EmailBulkEspCityMappingSRO update) {
        super();
        this.update = update;
    }

    public EmailBulkEspCityMappingSRO getUpdate() {
        return update;
    }

    public void setUpdate(EmailBulkEspCityMappingSRO update) {
        this.update = update;
    }

}
