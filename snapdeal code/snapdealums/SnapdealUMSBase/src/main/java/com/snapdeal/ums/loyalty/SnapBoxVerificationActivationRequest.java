package com.snapdeal.ums.loyalty;

import com.dyuproject.protostuff.Tag;

/**
 * 
 * Request which requires the parameter we send to the customer for verification
 * purposes
 * 
 * @author ashish
 * 
 */
public class SnapBoxVerificationActivationRequest extends
SnapBoxActivationRequest
{

    private static final long serialVersionUID = -4237716350591824926L;

    @Tag(4)
    private String paramaterString;

    public SnapBoxVerificationActivationRequest()
    {

        super();
    }

    public SnapBoxVerificationActivationRequest(String requestedFromEmailID, String paramString)
    {

        super(requestedFromEmailID);
        this.paramaterString = paramString;
    }

    public String getParamaterString()
    {

        return paramaterString;
    }

    public void setParamaterString(String paramaterString)
    {

        this.paramaterString = paramaterString;
    }

}
