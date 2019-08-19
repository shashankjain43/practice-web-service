package com.snapdeal.ims.activity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.activity.IActivityService;
import com.snapdeal.ims.activity.task.ActivityTask;

@Service
public class ActivityServiceImpl implements IActivityService {

	@Autowired
	private ActivityTask activityTask;

	@Override
	public boolean logActivity(Object request, Object response,
			String className, String methodName) {

		activityTask.logActivity(request, response, className, methodName);
		return true;
	}

}
