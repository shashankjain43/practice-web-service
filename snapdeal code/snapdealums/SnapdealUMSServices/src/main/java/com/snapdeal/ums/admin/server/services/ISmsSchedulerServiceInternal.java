
package com.snapdeal.ums.admin.server.services;

import java.util.Date;
import java.util.List;

import com.snapdeal.ums.core.entity.SmsScheduler;


public interface ISmsSchedulerServiceInternal {

	public void persist(SmsScheduler smsScheduler);

	public SmsScheduler getSmsSchedulerById(int id);

	public List<SmsScheduler> getSmsSchedulerList(Date date, String status);

	public List<SmsScheduler> getSmsScheduler(int cityId, Date date, String status);

	public List<SmsScheduler> getSmsScheduler(List<Integer> cityIds, Date date, String status);

	public List<SmsScheduler> getSmsSchedulerList(Date date);

	public List<SmsScheduler> getSmsScheduler(int cityId, Date date);

	public List<SmsScheduler> getSmsScheduler(List<Integer> cityIds, Date date);

	public void update(SmsScheduler smsScheduler);

}
