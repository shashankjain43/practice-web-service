package com.snapdeal.ums.loyalty;

import java.util.Collection;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;
@AuditableClass
public class LoyaltyUploadRs extends ServiceResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 8288007844307794798L;

    @AuditableField
    @Tag(4)
    private List<String> invalidEmailIDs;
    @AuditableField
    @Tag(5)
    private List<String> existingLoyaltyEmails;
    @AuditableField
    @Tag(6)
    private int madeEligibleCount;
    @AuditableField
    @Tag(7)
    private int invalidCount;
    @AuditableField
    @Tag(8)
    private int totalEntriesCount;
    @AuditableField
    @Tag(9)
    private int existentEmailCount;

    public Collection<String> getInvalidEmailIDs()
    {

        return invalidEmailIDs;
    }

    public void setInvalidEmailIDs(List<String> invalidEmailIDs)
    {

        this.invalidEmailIDs = invalidEmailIDs;
    }

    public int getMadeEligibleCount()
    {

        return madeEligibleCount;
    }

    public void setMadeEligibleCount(int madeEligibleCount)
    {

        this.madeEligibleCount = madeEligibleCount;
    }

    public int getInvalidCount()
    {

        return invalidCount;
    }

    public void setInvalidCount(int invalidCount)
    {

        this.invalidCount = invalidCount;
    }

    public LoyaltyUploadRs(List<String> invalidEmailIDs, int madeEligibleCount, int invalidCount,int existentEmailCount,
        List<String> existingLoyaltyEmails)
    {

        super();
        this.invalidEmailIDs = invalidEmailIDs;
        this.madeEligibleCount = madeEligibleCount;
        this.invalidCount = invalidCount;
        this.existingLoyaltyEmails = existingLoyaltyEmails;
        this.existentEmailCount=existentEmailCount;
    }

    
    public int getExistentEmailCount()
    {
    
        return existentEmailCount;
    }

    
    public void setExistentEmailCount(int existentEmailCount)
    {
    
        this.existentEmailCount = existentEmailCount;
    }

    public LoyaltyUploadRs()
    {

        super();
    }

    public List<String> getExistingLoyaltyEmails()
    {

        return existingLoyaltyEmails;
    }

    public void setExistingLoyaltyEmails(List<String> existingLoyaltyEmails)
    {

        this.existingLoyaltyEmails = existingLoyaltyEmails;
    }

    public int getTotalEntriesCount()
    {

        return totalEntriesCount;
    }

    public void setTotalEntriesCount(int emailIDCountInRq)
    {

        this.totalEntriesCount = emailIDCountInRq;
    }

}
