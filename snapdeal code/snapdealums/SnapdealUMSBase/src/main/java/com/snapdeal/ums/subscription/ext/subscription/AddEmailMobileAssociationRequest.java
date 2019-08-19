
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;


public class AddEmailMobileAssociationRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2061628187958179931L;
    @Tag(3)
    private EmailMobileAssociationSRO association;

    public AddEmailMobileAssociationRequest() {
    }
    public AddEmailMobileAssociationRequest(EmailMobileAssociationSRO association) {
        super();
        this.association = association;
    }

    public EmailMobileAssociationSRO getAssociation() {
        return association;
    }

    public void setAssociation(EmailMobileAssociationSRO association) {
        this.association = association;
    }

}
