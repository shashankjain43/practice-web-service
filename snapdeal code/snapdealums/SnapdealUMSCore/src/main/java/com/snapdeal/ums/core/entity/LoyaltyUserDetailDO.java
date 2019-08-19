package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity object representing loyalty user details 
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "loyalty_user_details", catalog = "ums")
public class LoyaltyUserDetailDO implements Serializable
{

    private static final long serialVersionUID = 8302471436154082303L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loyalty_status")
    private LoyaltyProgramStatusDO loyaltyProgramStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loyalty_program")
    private LoyaltyProgramDO loyaltyProgram;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "verification_code", unique = true)
    private String verificationCode;

    public String getVerificationCode()
    {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode)
    {
        this.verificationCode = verificationCode;
    }

    protected LoyaltyUserDetailDO()
    {
        super();
    }

    public LoyaltyUserDetailDO(String email, LoyaltyProgramDO loyaltyProgram,
        LoyaltyProgramStatusDO loyaltyProgramStatus, User user, String dbEncryptedVerificationCode)
    {
        super();
        this.email = email;
        this.loyaltyProgramStatus = loyaltyProgramStatus;
        this.loyaltyProgram = loyaltyProgram;
        this.user = user;
        this.verificationCode = dbEncryptedVerificationCode;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public LoyaltyProgramDO getLoyaltyProgram()
    {
        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyProgramDO loyaltyProgram)
    {
        this.loyaltyProgram = loyaltyProgram;
    }

    public void setLoyaltyProgramStatus(LoyaltyProgramStatusDO loyaltyProgramStatus)
    {
        this.loyaltyProgramStatus = loyaltyProgramStatus;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public LoyaltyProgramStatusDO getLoyaltyProgramStatus()
    {
        return this.loyaltyProgramStatus;
    }

    public void setStatus(LoyaltyProgramStatusDO loyaltyProgramStatus)
    {
        this.loyaltyProgramStatus = loyaltyProgramStatus;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

}
