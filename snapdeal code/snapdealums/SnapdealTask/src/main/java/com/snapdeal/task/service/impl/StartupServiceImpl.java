/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-May-2013
 *  @author amd
 */
package com.snapdeal.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.task.exception.TaskException;
import com.snapdeal.task.service.IStartupService;
import com.snapdeal.task.service.ITaskManagerService;

@Service("startupService")
public class StartupServiceImpl implements IStartupService {

    private static final Logger LOG = LoggerFactory.getLogger(StartupServiceImpl.class);

    @Autowired
    private ITaskManagerService taskManagerService;

    @Override
    public void loadAll() {
        try {
            LOG.info("Initializing schedulers..");
            taskManagerService.initialize();
            LOG.info("Schedulers initialized SUCCESSFULLY!");
        } catch (TaskException e) {
            LOG.error("Error occured while initializing schedulers. Exiting.", e);
            throw new RuntimeException(e);
        }
    }
}
