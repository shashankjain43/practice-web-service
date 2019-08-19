
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsEmailMobileAssociationResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -809306506266878076L;
    @Tag(5)
    private boolean isEmailMobileAssociation;

    public IsEmailMobileAssociationResponse() {
    }

    public IsEmailMobileAssociationResponse(boolean isEmailMobileAssociation) {
        super();
        this.isEmailMobileAssociation = isEmailMobileAssociation;
    }

    public boolean isEmailMobileAssociation() {
        return isEmailMobileAssociation;
    }

    public void setIsEmailMobileAssociation(boolean isEmailMobileAssociation) {
        this.isEmailMobileAssociation = isEmailMobileAssociation;
    }

}
