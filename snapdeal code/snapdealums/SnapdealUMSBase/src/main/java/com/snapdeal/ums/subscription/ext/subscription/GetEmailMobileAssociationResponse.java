
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;

public class GetEmailMobileAssociationResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 2425137367759695564L;
    @Tag(5)
    private EmailMobileAssociationSRO getEmailMobileAssociation;

    public GetEmailMobileAssociationResponse() {
    }

    public GetEmailMobileAssociationResponse(EmailMobileAssociationSRO getEmailMobileAssociation) {
        super();
        this.getEmailMobileAssociation = getEmailMobileAssociation;
    }

    public EmailMobileAssociationSRO getGetEmailMobileAssociation() {
        return getEmailMobileAssociation;
    }

    public void setGetEmailMobileAssociation(EmailMobileAssociationSRO getEmailMobileAssociation) {
        this.getEmailMobileAssociation = getEmailMobileAssociation;
    }

}
