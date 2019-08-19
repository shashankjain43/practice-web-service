package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity object representing user NEFT details
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "user_neft_details", catalog = "ums")
public class UserNeftDetailsDO implements Serializable
{

    private static final long serialVersionUID = 8302471436154082303L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "last_verified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVerified;
    
    
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "branch")
    private String branch;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "accHolderName", nullable = false)
    private String accHolderName;

    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @Column(name = "is_active")
    private boolean isActive;

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

    public Date getLastUpdated()
    {

        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {

        this.lastUpdated = lastUpdated;
    }

    public String getIfscCode()
    {

        return ifscCode;
    }

    public void setIfscCode(String ifscCode)
    {

        this.ifscCode = ifscCode;
    }

    public String getBranch()
    {

        return branch;
    }

    public void setBranch(String branch)
    {

        this.branch = branch;
    }

    public String getBankName()
    {

        return bankName;
    }

    public void setBankName(String bankName)
    {

        this.bankName = bankName;
    }

    public String getAccountNo()
    {

        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {

        this.accountNo = accountNo;
    }

    public UserNeftDetailsDO()
    {

        super();
        // TODO Auto-generated constructor stub
    }

    public UserNeftDetailsDO(String email,
        String ifscCode, String branch, String bankName, String accountNo, boolean isActive, Date lastVerified,
        String accHolderName, Date createdDate)
    {

        super();
        this.email = email;
        this.ifscCode = ifscCode;
        this.branch = branch;
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.isActive = isActive;
        this.lastVerified = lastVerified;
        this.accHolderName = accHolderName;
        this.createdDate=createdDate;
    }

    public boolean isActive()
    {

        return isActive;
    }

    public void setActive(boolean isActive)
    {

        this.isActive = isActive;
    }

    public Date getLastVerified()
    {

        return lastVerified;
    }

    public void setLastVerified(Date lastVerified)
    {

        this.lastVerified = lastVerified;
    }

    
    public String getAccHolderName()
    {
    
        return accHolderName;
    }

    
    public void setAccHolderName(String accHolderName)
    {
    
        this.accHolderName = accHolderName;
    }

    
    
}
