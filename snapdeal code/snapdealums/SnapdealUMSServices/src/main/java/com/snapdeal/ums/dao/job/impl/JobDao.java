package com.snapdeal.ums.dao.job.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.constants.JobType;
import com.snapdeal.ums.core.entity.job.JobDO;
import com.snapdeal.ums.dao.job.IJobDao;

@Repository
public class JobDao implements IJobDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<JobDO> getJobs(JobType jobType, Date beforeDateTime)
    {

        StatelessSession statelessSession = sessionFactory.openStatelessSession();
        Query query = statelessSession.createQuery("from JobDO where jobType=:jobType and scheduledOn<:beforeDateTime");
        query.setParameter("jobType", jobType);
        query.setParameter("beforeDateTime", beforeDateTime);
        return query.list();

    }

    @Override
    public void deleteJob(JobDO jobDO){
        sessionFactory.getCurrentSession().delete(jobDO);
    }

    @Override
    public void updateJob(Collection<JobDO> jobDOs)
    {

        // TODO Auto-generated method stub

    }

    @Override
    public void deleteJobs(Collection<JobDO> jobDOs)
    {

        // TODO Auto-generated method stub

    }

    @Override
    public JobDO addJob(JobDO jobDO)
    {

        sessionFactory.getCurrentSession().save(jobDO);
        return jobDO;
    }

}
