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
 * Entity object representing the email to be sent to the loyalty user *
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "Scheduled_emails_toBeSent", catalog = "ums")
public class ScheduledEmailToBeSent implements Serializable
{

    private static final long serialVersionUID = 8302471436154082303L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "email_template_name")
    private String emailTemplateName;

    // @Column(name = "updated_status")
    // private String updatedStatus;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "failed_attempts")
    private int failedAttempts;

    public String getEmailTemplateName()
    {

        return emailTemplateName;
    }

    public void setEmailTemplateName(String emailTemplateName)
    {

        this.emailTemplateName = emailTemplateName;
    }

    public Date getLastUpdated()
    {

        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated)
    {

        this.lastUpdated = lastUpdated;
    }

    public int getFailedAttempts()
    {

        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts)
    {

        this.failedAttempts = failedAttempts;
    }

    public String getEmail()
    {

        return email;
    }

    public void setEmail(String email)
    {

        this.email = email;
    }

    public ScheduledEmailToBeSent()
    {

        super();
    }
}
