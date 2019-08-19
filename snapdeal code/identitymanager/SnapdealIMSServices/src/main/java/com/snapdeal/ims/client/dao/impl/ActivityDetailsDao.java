package com.snapdeal.ims.client.dao.impl;

import java.util.Calendar;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.activity.dbmapper.IActivityDetailsMapper;
import com.snapdeal.ims.client.dao.IActivityDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Activity;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository
@Slf4j
public class ActivityDetailsDao implements IActivityDetailsDao{
	
	@Autowired
	private IActivityDetailsMapper activityDetailsMapper;
	
	@Override
   @Timed
   @Marked
	public long createActivity(Activity activity) {
		long id = -1;
		try{
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH)+1;
			String monthZeroPrepend=(month<10?"0":"")+month;
			activity.setTableName("activity_log_"+monthZeroPrepend+"_"+year);
			id = activityDetailsMapper.createActivity(activity);
		} catch(RuntimeException e){
			log.error("Exception occured while persisting activity in "
					+ "database: " + activity, e);
		}
		return id;
	}
}
