package com.snapdeal.ums.userNeftDetails;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

/**
 * Request to add user NEFT details
 * 
 * @author ashish
 * 
 */
@AuditableClass
public class VerifyUserNEFTDetailsRequest extends ServiceRequest
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 813414536535063L;

    @AuditableField
    @Tag(3)
    private int neftDetailsID;

    public VerifyUserNEFTDetailsRequest()
    {

        super();
    }

    public VerifyUserNEFTDetailsRequest(int userNEFTDetails)
    {

        this.neftDetailsID = userNEFTDetails;
    }

    public int getNeftDetailsID()
    {

        return neftDetailsID;
    }

    public void setNeftDetailsID(int neftDetailsID)
    {

        this.neftDetailsID = neftDetailsID;
    }

}