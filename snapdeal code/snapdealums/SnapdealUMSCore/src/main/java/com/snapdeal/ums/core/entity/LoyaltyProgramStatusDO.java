package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity object representing loyalty program status
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "loyalty_program_status", catalog = "ums")
public class LoyaltyProgramStatusDO implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7491007568212909276L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public LoyaltyProgramStatusDO(int id, String name, boolean isDeleted)
    {
        super();
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public LoyaltyProgramStatusDO()
    {

    }
}
