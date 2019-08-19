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
public class GetActiveUserNEFTDetailsRequest extends ServiceRequest
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 813414536535063L;

    @AuditableField
    @Tag(3)
    private String emailID;

    public GetActiveUserNEFTDetailsRequest()
    {

        super();
    }

    
    public String getEmailID()
    {
    
        return emailID;
    }

    
    public void setEmailID(String emailID)
    {
    
        this.emailID = emailID;
    }


    public GetActiveUserNEFTDetailsRequest(String emailID)
    {

        super();
        this.emailID = emailID;
    }

    
}