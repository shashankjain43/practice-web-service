/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 30-Apr-2013
 *  @author amd
 */
package com.snapdeal.task.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public abstract class AbstractTask implements Job {

    @Override
    public abstract void execute(JobExecutionContext context) throws JobExecutionException;

}
