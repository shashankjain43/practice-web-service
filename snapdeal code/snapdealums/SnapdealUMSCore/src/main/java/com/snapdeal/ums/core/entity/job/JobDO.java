package com.snapdeal.ums.core.entity.job;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.ums.constants.JobType;

@Entity
@Table(name = "job_sheet", catalog = "ums")
public class JobDO
{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * It is the ID on which the job should be executed. Could be an email ID or
     * a User ID.
     */
    @Column(name = "context_id")
    private String contextID;

    @Column(name = "job_type")
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(name = "retry_attempts")
    private int retryAttempts;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_on")
    private Date scheduledOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_actioned_on")
    private Date lastActionedOn;

    public int getId()
    {

        return id;
    }

    public String getContextID()
    {

        return contextID;
    }

    public JobType getJobType()
    {

        return jobType;
    }

    public int getRetryAttempts()
    {

        return retryAttempts;
    }

    public Date getScheduledON()
    {

        return scheduledOn;
    }

    public Date getLastActionedOn()
    {

        return lastActionedOn;
    }

    public void setRetryAttempts(int retryAttempts)
    {

        this.retryAttempts = retryAttempts;
    }

    public void setLastActionedOn(Date lastActionedOn)
    {

        this.lastActionedOn = lastActionedOn;
    }

    public JobDO(String contextID, JobType jobType, Date scheduledON)
    {

        this.contextID = contextID;
        this.jobType = jobType;
        this.scheduledOn = scheduledON;
    }

    public JobDO()
    {

    }

    @Override
    public String toString()
    {

        return "Job: Type - " + this.jobType + " for - " + this.getContextID();
    }

}
