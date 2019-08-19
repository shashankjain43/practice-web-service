
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;

public class GetMobileAssociationByMobileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -225102325575202349L;
    @Tag(5)
    private List<EmailMobileAssociationSRO> getMobileAssociationByMobile = new ArrayList<EmailMobileAssociationSRO>();

    public GetMobileAssociationByMobileResponse() {
    }

    public GetMobileAssociationByMobileResponse(List<EmailMobileAssociationSRO> getMobileAssociationByMobile) {
        super();
        this.getMobileAssociationByMobile = getMobileAssociationByMobile;
    }

    public List<EmailMobileAssociationSRO> getMobileAssociationByMobile() {
        return getMobileAssociationByMobile;
    }

    public void setMobileAssociationByMobile(List<EmailMobileAssociationSRO> getMobileAssociationByMobile) {
        this.getMobileAssociationByMobile = getMobileAssociationByMobile;
    }

}
