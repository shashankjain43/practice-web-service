
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterESPMappingRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -419131590604735822L;
	@Tag(3)
    private int newsletterId;
    @Tag(4)
    private int espId;

    public GetNewsletterESPMappingRequest() {
    }

    public int getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(int newsletterId) {
        this.newsletterId = newsletterId;
    }

    public int getEspId() {
        return espId;
    }

    public void setEspId(int espId) {
        this.espId = espId;
    }

    public GetNewsletterESPMappingRequest(int newsletterId, int espId) {
        this.newsletterId = newsletterId;
        this.espId = espId;
    }

}
