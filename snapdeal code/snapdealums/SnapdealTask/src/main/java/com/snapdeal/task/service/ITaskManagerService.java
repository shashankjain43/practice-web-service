/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 30-Apr-2013
 *  @author amd
 */
package com.snapdeal.task.service;

import com.snapdeal.task.base.AbstractTask;
import com.snapdeal.task.exception.TaskException;

public interface ITaskManagerService {

    public void initialize() throws TaskException;

    public boolean addOrUpdateSchedule(String clientName, Class<? extends AbstractTask> taskClass, String cronExpression) throws TaskException;

    public boolean addTask(String clientName, Class<? extends AbstractTask> taskClass) throws TaskException;

}
