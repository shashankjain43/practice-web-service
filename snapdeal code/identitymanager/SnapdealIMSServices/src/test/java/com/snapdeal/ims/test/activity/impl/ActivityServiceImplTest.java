package com.snapdeal.ims.test.activity.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.activity.impl.ActivityServiceImpl;
import com.snapdeal.ims.activity.task.ActivityTask;

public class ActivityServiceImplTest {

	@InjectMocks
	ActivityServiceImpl activityService;

	@org.mockito.Mock
	private ActivityTask activityTask;	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void logActivity() {
		Mockito.doNothing().when(activityTask)
		.logActivity(Mockito.any(Object.class), 
					 Mockito.any(Object.class), 
					 Mockito.any(String.class), 
					 Mockito.any(String.class));
		
		boolean response = activityService.logActivity(new Object(), 
									new Object(), 
									new String("className"), 
									new String("methodName"));
		assertTrue(response);
	}
}
