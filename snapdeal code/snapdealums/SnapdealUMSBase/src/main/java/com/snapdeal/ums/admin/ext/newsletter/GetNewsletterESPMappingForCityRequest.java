
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterESPMappingForCityRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4994306619489453373L;
	@Tag(3)
    private int newsletterId;
    @Tag(4)
    private int espId;
    @Tag(5)
    private int cityId;
    @Tag(6)
    private String filterType;

    public GetNewsletterESPMappingForCityRequest() {
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public GetNewsletterESPMappingForCityRequest(int newsletterId, int espId, int cityId, String filterType) {
        this.newsletterId = newsletterId;
        this.espId = espId;
        this.cityId = cityId;
        this.filterType = filterType;
    }

}
