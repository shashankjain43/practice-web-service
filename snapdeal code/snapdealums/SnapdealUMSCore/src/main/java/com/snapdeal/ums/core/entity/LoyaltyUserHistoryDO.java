package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

/**
 * Entity object representating snapbox and customer association
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "loyalty_user_history", catalog = "ums")
public class LoyaltyUserHistoryDO implements Serializable
{

    private static final long serialVersionUID = 8302471436154082303L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setEmail(String email)
    {

        this.email = email;
    }

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "previous_status")
    private String previousStatus;

    @Column(name = "updated_status")
    private String updatedStatus;

    @Column(name = "loyalty_program")
    private String loyaltyProgram;

    @Column(name = "lastUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    public LoyaltyUserHistoryDO()
    {

        super();
    }

    public LoyaltyUserHistoryDO(User user, String email, String loyaltyProgram, String previousStatus,
        String updatedStatus
        , Date statusChangedOn)
    {

        super();
        this.loyaltyProgram = loyaltyProgram;
        this.user = user;
        this.email = email;
        this.previousStatus = previousStatus;
        this.updatedStatus = updatedStatus;
        this.lastUpdated = statusChangedOn;
    }

    public String getLoyaltyProgram()
    {

        return loyaltyProgram;
    }

    public void setLoyaltyProgram(String loyaltyProgram)
    {

        this.loyaltyProgram = loyaltyProgram;
    }

    public int getId()
    {

        return id;
    }

    // public void setId(int id) {
    // this.id = id;
    // }

    public String getEmail()
    {

        return email;
    }

    public User getUser()
    {

        return user;
    }

    public void setUser(User user)
    {

        this.user = user;
    }

    public String getPreviousStatus()
    {

        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus)
    {

        this.previousStatus = previousStatus;
    }

    public String getUpdatedStatus()
    {

        return updatedStatus;
    }

    public void setUpdatedStatus(String updatedStatus)
    {

        this.updatedStatus = updatedStatus;
    }

    public static long getSerialversionuid()
    {

        return serialVersionUID;
    }

    public void setId(int id)
    {

        this.id = id;
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
