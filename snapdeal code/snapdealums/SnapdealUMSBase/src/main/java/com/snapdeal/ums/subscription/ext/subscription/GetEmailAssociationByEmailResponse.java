
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;

public class GetEmailAssociationByEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 8358419821684782898L;
    @Tag(5)
    private List<EmailMobileAssociationSRO>  getEmailAssociationByEmail = new ArrayList<EmailMobileAssociationSRO>();

    public GetEmailAssociationByEmailResponse() {
    }

    public GetEmailAssociationByEmailResponse(List<EmailMobileAssociationSRO>  getEmailAssociationByEmail) {
        super();
        this.getEmailAssociationByEmail = getEmailAssociationByEmail;
    }

    public List<EmailMobileAssociationSRO>  getEmailAssociationByEmail() {
        return getEmailAssociationByEmail;
    }

    public void setEmailAssociationByEmail(List<EmailMobileAssociationSRO>  getEmailAssociationByEmail) {
        this.getEmailAssociationByEmail = getEmailAssociationByEmail;
    }

}
