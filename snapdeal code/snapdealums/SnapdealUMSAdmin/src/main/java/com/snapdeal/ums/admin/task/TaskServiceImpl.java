/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 30-Apr-2013
 *  @author amd
 */
package com.snapdeal.ums.admin.task;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.model.common.Component;
import com.snapdeal.task.base.AbstractTask;
import com.snapdeal.task.base.AutowiringSpringBeanJobFactory;
import com.snapdeal.ums.core.entity.UmsTask;
import com.snapdeal.ums.task.dao.ITaskDao;
import com.snapdeal.task.exception.TaskException;
import com.snapdeal.ums.admin.task.ITaskService;
import com.snapdeal.task.service.RuntimeUtils;

@Service("taskService")
@SuppressWarnings("unchecked")
public class TaskServiceImpl implements ITaskService, ApplicationContextAware {

    private static final Logger  LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    private ApplicationContext   applicationContext;

    private List<String>         loadedClassNames = new ArrayList<String>();


    
    private static final String UMS = "UMS";

    @Autowired
    private ITaskDao             taskDao;

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
    public void startTasks() throws TaskException {
        try {
            concurrentSchedulerFactoryBean.getScheduler().start();
            clusteredSchedulerFactoryBean.getScheduler().start();
        } catch (SchedulerException e) {
            LOG.error("Error occured while starting schedulers", e);
            throw new TaskException();
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadTasks() throws TaskException {
        try {
            concurrentSchedulerFactoryBean.getScheduler().clear();
            clusteredSchedulerFactoryBean.getScheduler().clear();
        } catch (SchedulerException e) {
            LOG.error("Error occured while starting schedulers", e);
            throw new TaskException();
        }
        for (UmsTask task : taskDao.getTasks()) {
            try {
                if (!getScheduler(task).checkExists(JobKey.jobKey(task.getImplClass(), UMS))) {
                    if (!loadedClassNames.contains(task.getImplClass())) {
                        List<Class<?>> annotations = new ArrayList<Class<?>>(2);
                        annotations.add(PersistJobDataAfterExecution.class);
                        if (!task.isConcurrent()) {
                            annotations.add(DisallowConcurrentExecution.class);
                        }
                        RuntimeUtils.addAnnotations(task.getImplClass(), annotations);
                        loadedClassNames.add(task.getImplClass());
                    }
                    addTask(UMS, task);
                }
            } catch (SchedulerException e) {
                LOG.error("Schedular Exception occured", e);
                throw new TaskException();
            } catch (Exception e) {
                LOG.error("Failed to add Quartz annotations to task classes", e);
                throw new TaskException();
            }
            addOrUpdateSchedule(UMS, task, task.getCronExpression());
        }
        initializeTasks();
    }

    private void initializeTasks() throws TaskException {
        initializeScheduler(true);
        initializeScheduler(false);
    }

    private void initializeScheduler(boolean concurrent) throws TaskException {
        AutowiringSpringBeanJobFactory autowiringJobFactory = new AutowiringSpringBeanJobFactory();
        autowiringJobFactory.setApplicationContext(applicationContext);
        Scheduler scheduler = null;
        if (concurrent) {
            concurrentSchedulerFactoryBean.setJobFactory(autowiringJobFactory);
            scheduler = concurrentSchedulerFactoryBean.getScheduler();
        } else {
            clusteredSchedulerFactoryBean.setJobFactory(autowiringJobFactory);
            scheduler = clusteredSchedulerFactoryBean.getScheduler();
        }
        try {
            scheduler.setJobFactory(autowiringJobFactory);
        } catch (SchedulerException e) {
            LOG.error("Exception occoured while initializing scheduler", e);
            throw new TaskException();
        }
    }

    private Scheduler getScheduler(UmsTask task) {
        return task.isClustered() ? clusteredSchedulerFactoryBean.getScheduler() : concurrentSchedulerFactoryBean.getScheduler();
    }

    private void addTask(String clientName, UmsTask task) throws TaskException {
        JobDetail jd = null;
        Scheduler sch = getScheduler(task);
        try {
            jd = sch.getJobDetail(JobKey.jobKey(task.getImplClass(), clientName));
        } catch (SchedulerException e) {
            LOG.error("Error occured while getting job detail", e);
            throw new TaskException();
        }
        if (jd == null) {
            try {
                jd = getJobDetail((Class<? extends AbstractTask>) Class.forName(task.getImplClass()), clientName);
            } catch (ClassNotFoundException e) {
                LOG.error("Class not found: ", e);
                throw new TaskException();
            }
        }
        LOG.info("Added task {} to group {}", task.getImplClass(), clientName);
    }

    private void addOrUpdateSchedule(String clientName, UmsTask task, String cronExpression) throws TaskException {
        JobDetail jd = null;
        Scheduler sch = getScheduler(task);
        JobKey key = JobKey.jobKey(task.getImplClass(), clientName);
        try {
            if (sch.checkExists(key)) {
                sch.deleteJob(key);
            }
            try {
                jd = getJobDetail((Class<? extends AbstractTask>) Class.forName(task.getImplClass()), clientName);
            } catch (ClassNotFoundException e) {
                LOG.error("Class not found: ", e);
                throw new TaskException();
            }
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(String.valueOf(System.nanoTime()), clientName).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
            sch.scheduleJob(jd, trigger);
        } catch (SchedulerException e) {
            LOG.info("Error occured while modifying trigger for task {} Group[{}]", task.getImplClass(), clientName);
            throw new TaskException();
        }
        LOG.info("Successfully changed cron expression for task {} to {}", task.getImplClass(), cronExpression);
    }

    private JobDetail getJobDetail(Class<? extends AbstractTask> clazz, String clientName) {
        return JobBuilder.newJob(clazz).withIdentity(clazz.getName(), clientName).storeDurably().build();
    }
    
    @Transactional
    @Override
    public List<UmsTask> getAllTasks() {
        return taskDao.getAllTasks();
    }
    
    @Transactional
    @Override
    public List<UmsTask> getTasks() {
        return taskDao.getTasks();
    }
    @Transactional
    @Override
    public void runRecurrentTask(Integer taskId) {
       UmsTask task =  taskDao.getRecurrentTaskById(taskId);
       Scheduler scheduler = getScheduler(task);
       JobKey jobKey = JobKey.jobKey(task.getImplClass(), UMS);
       try {
        scheduler.triggerJob(jobKey);
    } catch (SchedulerException e) {
        LOG.error("Unable to load task:" + task.getName(), e);
    }
    }
    
    @Transactional
    @Override
    public UmsTask getRecurrentTaskById(Integer taskId) {
        return taskDao.getRecurrentTaskById(taskId);
    }
    
    @Override
    @Transactional
    public UmsTask updateTask(UmsTask task) {
       return taskDao.updateTask(task);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTaskScheduler(UmsTask task) throws TaskException {
        addOrUpdateSchedule(UMS, task, task.getCronExpression());
    }

    @Override
    @Transactional
    public UmsTask getRecurrentTaskByName(String taskname) {
        return taskDao.getRecurrentTaskByName(taskname);
    }
}
