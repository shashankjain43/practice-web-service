package com.snapdeal.ums.userNeftDetails;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
@AuditableClass
public class UserNEFTDetailsSRO implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2114364170279508513L;
    @AuditableField
    @Tag(1)
    private String email;

    @AuditableField
    @Tag(2)
    private String ifscCode;

    @AuditableField
    @Tag(3)
    private String branchName;

    @AuditableField
    @Tag(4)
    private String bankName;

    @AuditableField
    @Tag(5)
    private String accountNo;

    @AuditableField
    @Tag(6)
    private String accHolderName;
    
    @AuditableField
    @Tag(7)
    private int id;
    
    

    
    public int getId()
    {
    
        return id;
    }

    
    public void setId(int id)
    {
    
        this.id = id;
    }

    public String getEmail()
    {

        return email;
    }

    public void setEmail(String email)
    {

        this.email = email;
    }

    public String getIfscCode()
    {

        return ifscCode;
    }

    public void setIfscCode(String ifscCode)
    {

        this.ifscCode = ifscCode;
    }

    public String getBranchName()
    {

        return branchName;
    }

    public void setBranchName(String branchName)
    {

        this.branchName = branchName;
    }

    public String getBankName()
    {

        return bankName;
    }

    public void setBankName(String bankName)
    {

        this.bankName = bankName;
    }

    
    
    
    
    public String getAccHolderName()
    {
    
        return accHolderName;
    }

    
    public void setAccHolderName(String name)
    {
    
        this.accHolderName = name;
    }

    
    public void setAccountNo(String accountNo)
    {
    
        this.accountNo = accountNo;
    }

    public String getAccountNo()
    {

        return accountNo;
    }

    // public void setAccountNo(String accountNo) {
    // this.accountNo = accountNo;
    // }

//    public UserNEFTDetailsSRO(int id, String email, String ifscCode, String branchName,
//        String bankName, String accountNo, String name)
//    {
//
//        super();
//        this.id=id;
//        this.email = email;
//        this.ifscCode = ifscCode;
//        this.branchName = branchName;
//        this.bankName = bankName;
//        this.accountNo = accountNo;
//        this.accHolderName = name;
//    }

    public UserNEFTDetailsSRO()
    {

        super();
    }

}
