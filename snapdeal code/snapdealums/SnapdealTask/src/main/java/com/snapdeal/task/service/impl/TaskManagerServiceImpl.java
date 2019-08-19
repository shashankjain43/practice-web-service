/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 30-Apr-2013
 *  @author amd
 */
package com.snapdeal.task.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.snapdeal.task.annotation.Clustered;
import com.snapdeal.task.base.AbstractTask;
import com.snapdeal.task.base.AutowiringSpringBeanJobFactory;
import com.snapdeal.task.exception.TaskException;
import com.snapdeal.task.service.ITaskManagerService;

@Service("taskManagerService")
public class TaskManagerServiceImpl implements ITaskManagerService, ApplicationContextAware {

    private static final Logger  LOG = LoggerFactory.getLogger(TaskManagerServiceImpl.class);

    private ApplicationContext   applicationContext;

    @Autowired
    @Qualifier("clusteredScheduler")
    private SchedulerFactoryBean clusteredSchedulerFactoryBean;

    @Autowired
    @Qualifier("concurrentScheduler")
    private SchedulerFactoryBean concurrentSchedulerFactoryBean;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize() throws TaskException {
        initializeConcurrentScheduler();
        initializeClusteredScheduler();
    }

    private boolean initializeConcurrentScheduler() throws TaskException {
        AutowiringSpringBeanJobFactory autowiringJobFactory = new AutowiringSpringBeanJobFactory();
        autowiringJobFactory.setApplicationContext(applicationContext);
        concurrentSchedulerFactoryBean.setJobFactory(autowiringJobFactory);
        Scheduler scheduler = concurrentSchedulerFactoryBean.getScheduler();
        try {
            scheduler.setJobFactory(autowiringJobFactory);
            LOG.info("Concurrent scheduler initialized successfully!");
        } catch (SchedulerException e) {
            LOG.error("Exception occoured while initializing concurrent scheduler", e);
            throw new TaskException();
        }
        return true;
    }

    private boolean initializeClusteredScheduler() throws TaskException {
        AutowiringSpringBeanJobFactory autowiringJobFactory = new AutowiringSpringBeanJobFactory();
        autowiringJobFactory.setApplicationContext(applicationContext);
        clusteredSchedulerFactoryBean.setJobFactory(autowiringJobFactory);
        Scheduler scheduler = clusteredSchedulerFactoryBean.getScheduler();
        try {
            scheduler.setJobFactory(autowiringJobFactory);
            LOG.info("Clustered scheduler initialized successfully!");
        } catch (SchedulerException e) {
            LOG.error("Exception occoured while initializing synchronized scheduler", e);
            throw new TaskException();
        }
        return true;
    }

    @Override
    public boolean addTask(String clientName, Class<? extends AbstractTask> taskClass) throws TaskException {
        JobDetail jd = null;
        Scheduler sch = getScheduler(taskClass);
        try {
            jd = sch.getJobDetail(JobKey.jobKey(taskClass.getName(), clientName));
        } catch (SchedulerException e) {
            LOG.error("Error occured while getting job detail", e);
            throw new TaskException();
        }
        if (jd == null) {
            jd = getJobDetail(taskClass, clientName);
        }
        LOG.info("Added task {} to group {}", taskClass.getName(), clientName);
        return true;
    }

    @Override
    public boolean addOrUpdateSchedule(String clientName, Class<? extends AbstractTask> taskClass, String cronExpression) throws TaskException {
        JobDetail jd = null;
        Scheduler sch = getScheduler(taskClass);
        try {
            sch.deleteJob(JobKey.jobKey(taskClass.getName(), clientName));
            jd = getJobDetail(taskClass, clientName);
            //TODO: alternative for system.nanoTime()
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(String.valueOf(System.nanoTime()), clientName).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
            sch.scheduleJob(jd, trigger);
        } catch (SchedulerException e) {
            LOG.info("Error occured while modifying trigger for task {} Group[{}]", taskClass.getName(), clientName);
            throw new TaskException();
        }
        LOG.info("Successfully changed cron expression for task {} to {}", taskClass.getName(), cronExpression);
        return true;
    }

    private Scheduler getScheduler(Class<? extends AbstractTask> clazz) {
        return clazz.isAnnotationPresent(Clustered.class) ? clusteredSchedulerFactoryBean.getScheduler() : concurrentSchedulerFactoryBean.getScheduler();
    }

    private JobDetail getJobDetail(Class<? extends AbstractTask> clazz, String clientName) {
        return JobBuilder.newJob(clazz).withIdentity(clazz.getName(), clientName).storeDurably().build();
    }
}
