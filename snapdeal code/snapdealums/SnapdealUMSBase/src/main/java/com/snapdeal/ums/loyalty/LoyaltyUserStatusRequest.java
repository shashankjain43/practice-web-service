package com.snapdeal.ums.loyalty;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;

/**
 * Request to get user's detail of the status of the loyalty program in the
 * request context
 * 
 * @author ashish
 * 
 */
public class LoyaltyUserStatusRequest extends ServiceRequest
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 813411234536535063L;

    @Tag(3)
    private String userEmailID;

    @Tag(4)
    private LoyaltyConstants.LoyaltyProgram loyaltyProgram;

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

    public LoyaltyUserStatusRequest()
    {

    }

    public LoyaltyUserStatusRequest(String userEmailID, LoyaltyProgram loyaltyProgram)
    {

        super();
        this.userEmailID = userEmailID;
        this.loyaltyProgram = loyaltyProgram;
    }

}
