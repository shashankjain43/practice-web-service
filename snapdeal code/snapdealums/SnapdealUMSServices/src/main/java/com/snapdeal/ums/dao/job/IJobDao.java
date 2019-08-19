package com.snapdeal.ums.dao.job;

import java.util.Collection;
import java.util.Date;

import com.snapdeal.ums.constants.JobType;
import com.snapdeal.ums.core.entity.job.JobDO;

public interface IJobDao
{

    public Collection<JobDO> getJobs(JobType jobType, Date beforeDateTime);

    public void updateJob(Collection<JobDO> jobDOs);

    public void deleteJobs(Collection<JobDO> jobDOs);

    public void deleteJob(JobDO jobDO);

    
    public JobDO addJob(JobDO jobDO);
    
}
