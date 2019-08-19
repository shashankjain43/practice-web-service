package com.snapdeal.ums.loyalty;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;

/**
 * Response to get user's detail of the loyalty program and status
 * 
 * @author ashish
 * 
 */
public  class LoyaltyUserStatusResponse extends ServiceResponse
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 813411234536535063L;

    @Tag(5)
    private String userEmailID;

    @Tag(6)
    private LoyaltyConstants.LoyaltyProgram loyaltyProgram;

    @Tag(7)
    LoyaltyConstants.LoyaltyStatus loyaltyStatus;

    public LoyaltyUserStatusResponse(){}
    
    public LoyaltyConstants.LoyaltyStatus getLoyaltyStatus()
    {

        return loyaltyStatus;
    }

    public void setLoyaltyStatus(LoyaltyConstants.LoyaltyStatus loyaltyStatus)
    {

        this.loyaltyStatus = loyaltyStatus;
    }

    public LoyaltyUserStatusResponse(String userEmailID, LoyaltyProgram loyaltyProgram, LoyaltyStatus loyaltyStatus)
    {

        super();
        this.userEmailID = userEmailID;
        this.loyaltyProgram = loyaltyProgram;
        this.loyaltyStatus = loyaltyStatus;
    }

    public String getUserEmailID()
    {

        return userEmailID;
    }

    public void setUserEmailID(String userEmailID)
    {

        this.userEmailID = userEmailID;
    }

    public LoyaltyConstants.LoyaltyProgram getLoyaltyProgram()
    {

        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyConstants.LoyaltyProgram loyaltyProgram)
    {

        this.loyaltyProgram = loyaltyProgram;
    }

}
