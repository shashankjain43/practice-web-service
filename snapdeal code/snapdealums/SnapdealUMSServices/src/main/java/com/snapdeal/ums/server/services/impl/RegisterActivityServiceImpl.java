/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 15, 2010
 *  @author rahul
 */
package com.snapdeal.ums.server.services.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.core.entity.Activity;
import com.snapdeal.ums.server.services.IActivityServiceInternal;
import com.snapdeal.ums.server.services.IRegisterActivityService;


@Service("umsRegisterActivityService")
public class RegisterActivityServiceImpl implements IRegisterActivityService {

    private static final Logger LOG             = LoggerFactory.getLogger(RegisterActivityServiceImpl.class);

    @Autowired
    private IActivityServiceInternal    activityService;

    private ExecutorService     executorService = new ThreadPoolExecutor(20, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Override
    public void registerActivity(final Activity activity) {

        LOG.info(activity.toString());

        if (activity.getActivityType().getEnabled() && !activity.getActivityType().getAsync()) {
            executorService.execute(new RegisterActivity(activity));
        }

    }

    private final class RegisterActivity implements Runnable {

        private Activity activity;

        public RegisterActivity(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            activityService.processActivity(activity);
        }

    }

}
