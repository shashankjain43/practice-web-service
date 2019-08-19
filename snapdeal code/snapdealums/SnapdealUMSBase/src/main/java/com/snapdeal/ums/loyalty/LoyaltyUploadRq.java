package com.snapdeal.ums.loyalty;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;
@AuditableClass
public class LoyaltyUploadRq extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2302874582449755128L;

    @AuditableField
    @Tag(3)
    private LoyaltyProgram loyaltyProgram;
    @AuditableField
    @Tag(4)
    private LoyaltyStatus loyaltyStatus;
    @AuditableField
    @Tag(5)
    private String fileName;

    @Tag(6)
    private byte[] fileContent;

    public LoyaltyProgram getLoyaltyProgram()
    {

        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram)
    {

        this.loyaltyProgram = loyaltyProgram;
    }

    public LoyaltyStatus getLoyaltyStatus()
    {

        return loyaltyStatus;
    }

    public void setLoyaltyStatus(LoyaltyStatus loyaltyStatus)
    {

        this.loyaltyStatus = loyaltyStatus;
    }

    public String getFileName()
    {

        return fileName;
    }

    public void setFileName(String fileName)
    {

        this.fileName = fileName;
    }

    public byte[] getFileContent()
    {

        return fileContent;
    }

    public void setFileContent(byte[] fileContent)
    {

        this.fileContent = fileContent;
    }

    public LoyaltyUploadRq()
    {

        super();
    }

}
