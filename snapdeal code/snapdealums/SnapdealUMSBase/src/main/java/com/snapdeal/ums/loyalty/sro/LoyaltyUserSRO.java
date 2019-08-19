package com.snapdeal.ums.loyalty.sro;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.loyalty.LoyaltyConstants;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;

/**
 * SRO representing a user's profile of the loyalty program and its status
 * 
 * @author ashish
 * 
 */
public class LoyaltyUserSRO implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 6763251694565387323L;

    @Tag(1)
    private String userEmailID;

    @Tag(2)
    private LoyaltyConstants.LoyaltyProgram loyaltyProgram;

    @Tag(3)
    private LoyaltyConstants.LoyaltyStatus loyaltyStatus;

    public LoyaltyUserSRO(String userEmailID, LoyaltyProgram loyaltyProgram, LoyaltyStatus loyaltyStatus)
    {

        super();
        this.userEmailID = userEmailID;
        this.loyaltyProgram = loyaltyProgram;
        this.loyaltyStatus = loyaltyStatus;
    }

    public LoyaltyConstants.LoyaltyProgram getLoyaltyProgram()
    {

        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyConstants.LoyaltyProgram loyaltyProgram)
    {

        this.loyaltyProgram = loyaltyProgram;
    }

    public String getUserEmailID()
    {

        return userEmailID;
    }

    public LoyaltyConstants.LoyaltyStatus getLoyaltyStatus()
    {

        return loyaltyStatus;
    }

}
