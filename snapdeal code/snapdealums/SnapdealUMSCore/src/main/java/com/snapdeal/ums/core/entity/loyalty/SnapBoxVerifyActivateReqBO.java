package com.snapdeal.ums.core.entity.loyalty;


public class SnapBoxVerifyActivateReqBO
{

    private String requestedFromEmailID;

    private String recipientEmailID;

    private String verificationCode;
    

//    public SnapBoxVerificationActivationRqBO(SnapBoxActivationRequest request)
//    {
//
//        this.requestedFromEmailID = recipientEmailID = request.getRequestedFromEmailID();
//    }

    public String getRequestedFromEmailID()
    {

        return requestedFromEmailID;
    }

    public void setRequestedFromEmailID(String requestedFromEmailID)
    {

        this.requestedFromEmailID = requestedFromEmailID;
    }

    public String getRecipientEmailID()
    {

        return recipientEmailID;
    }

    public void setRecipientEmailID(String recipientEmailID)
    {

        this.recipientEmailID = recipientEmailID;
    }

    public String getVerificationCode()
    {

        return verificationCode;
    }

    public void setVerificationCode(String verificationCode)
    {

        this.verificationCode = verificationCode;
    }

    /**
     * 
     * @param requestedFromEmailID
     *            activation request from emailID
     * @param recipientEmailID
     *            emailID to which the email was sent - TO BE decrypted from the
     *            link sent by SnapDeal to the user
     * @param verificationCode
     *            Code encrypted to the link in the email
     */
    public SnapBoxVerifyActivateReqBO(String requestedFromEmailID, String recipientEmailID,
        String verificationCode)
    {

        super();
        this.requestedFromEmailID = requestedFromEmailID;
        this.recipientEmailID = recipientEmailID;
        this.verificationCode = verificationCode;
    }

}
