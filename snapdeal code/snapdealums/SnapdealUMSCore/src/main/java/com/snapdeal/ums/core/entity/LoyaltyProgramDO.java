package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity object representing loyalty program 
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "loyalty_programs", catalog = "ums")
public class LoyaltyProgramDO implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3700725355695583108L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    // TODO: Ashish - Future enhancements to loyalty program impl
    // @Transient
    // private Set<LoyaltyProgramStatusDO> supportedStatus;

    // public void addSupportedStatus(LoyaltyProgramStatusDO
    // loyaltyProgramStatus)
    // {
    // if (this.supportedStatus == null) {
    // this.supportedStatus = new HashSet<LoyaltyProgramStatusDO>();
    // }
    // this.supportedStatus.add(loyaltyProgramStatus);
    // }
    //
    // public Set<LoyaltyProgramStatusDO> getSupportedStatus()
    // {
    // return supportedStatus;
    // }
    //
    // public void setSupportedStatus(Set<LoyaltyProgramStatusDO>
    // supportedStatus)
    // {
    // this.supportedStatus = supportedStatus;
    // }

    public int getId()
    {

        return id;
    }

    public LoyaltyProgramDO(int id, String name, boolean isDeleted)
    {

        super();
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public LoyaltyProgramDO()
    {

        super();
    }

    public String getName()
    {

        return name;
    }

    public void setName(String name)
    {

        this.name = name;
    }

    public boolean isDeleted()
    {

        return isDeleted;
    }

    public void setDeleted(boolean isDeleted)
    {

        this.isDeleted = isDeleted;
    }

}
