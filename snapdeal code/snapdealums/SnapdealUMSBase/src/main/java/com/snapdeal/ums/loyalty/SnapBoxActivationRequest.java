package com.snapdeal.ums.loyalty;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

/**
 * 
 * Request to activate SnapBox for the emailID in request
 * 
 * @author ashish
 * 
 */
public class SnapBoxActivationRequest extends ServiceRequest
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 8134112365058535063L;

    @Tag(3)
    private String requestedFromEmailID;

    public String getRequestedFromEmailID()
    {

        return requestedFromEmailID;
    }

    public SnapBoxActivationRequest()
    {

        super();
    }

    public SnapBoxActivationRequest(String requestedFromEmailID)
    {

        super();
        this.requestedFromEmailID = requestedFromEmailID;
    }

    public void setRequestedFromEmailID(String requestedFromEmailID)
    {

        this.requestedFromEmailID = requestedFromEmailID;
    }

}
