package com.snapdeal.ums.services.job;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ums.constants.JobType;
import com.snapdeal.ums.core.entity.job.JobDO;
import com.snapdeal.ums.dao.job.IJobDao;

@Component
public class JobManager
{

    @Autowired
    private IJobDao jobDao;

    @Autowired
    private JobExecuter jobExecuter;

    private static final Logger LOG = LoggerFactory.getLogger(JobManager.class);

    public void addAndExecute(JobDO jobDO)
    {

        if (jobDO == null) {
            LOG.error("NULL job encountered!");
            return;
        }
        LOG.info("Add and execute: " + jobDO.toString());
        JobDO jobToBeExecuted = jobDao.addJob(jobDO);
        boolean isSucess = jobExecuter.execute(jobToBeExecuted);
        if (isSucess) {
            // Now, delete this jobDO entry - as it already has been executed!
            LOG.info("Delete " + jobDO.toString());
            jobDao.deleteJob(jobDO);
        }
        else {
            // DO nothing!
            // This job will be picked up later when the scheduler runs...
        }
    }

    public Collection<JobDO> getJobs(JobType jobType)
    {

        if (jobType == null) {
            return null;
        }
        // Delay the call a little to pick up very very recent jobs as well
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            // Do nothing..we anyway are just killing time!...carry on with the
            // work! No issues if the sleep time has been reduced!
        }
        return jobDao.getJobs(jobType, new Date());

    }

}
