package com.snapdeal.ums.userNeftDetails;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;

/**
 * 
 * EnhancedUserNEFTDetails includes a masked account number besides the
 * attributes inherited from the parent.
 * 
 * Use UserNeftHelper#getEnhancedUserNEFTDetails() to get this object. DO not initialize it by yourself.
 * Else, isExpired will not be set!
 * 
 * @author ashish
 * 
 */
@AuditableClass
public class EnhancedUserNEFTDetailsSRO extends UserNEFTDetailsSRO
{

    /**
     * 
     */
    private static final long serialVersionUID = 9087334284397728799L;

    @AuditableField
    @Tag(11)
    private String maskedAccountNo;

    @AuditableField
    @Tag(12)
    private boolean isExpired;

    public static long getSerialversionuid()
    {

        return serialVersionUID;
    }

    public boolean isExpired()
    {

        return isExpired;
    }

    public String getMaskedAccountNo()
    {

        return maskedAccountNo;
    }

    public EnhancedUserNEFTDetailsSRO()
    
    {
        super();
    }

    // public EnhancedUserNEFTDetails(String email, String ifscCode,
    // String branchName, String bankName, String accountNo) {
    // super(email, ifscCode, branchName, bankName, accountNo);
    // }

    public EnhancedUserNEFTDetailsSRO(int id,String email, String ifscCode,
        String branchName, String bankName, String accountNo, String accHolderName,
        String maskedAccountNo, boolean isExpired)
    {

        super();
        setId(id);
        setIfscCode(ifscCode);
        setBranchName(branchName);
        setBankName(bankName);
        setAccountNo(accountNo);
        setAccHolderName(accHolderName);
        this.maskedAccountNo = maskedAccountNo;
        this.isExpired = isExpired;
    }

}
