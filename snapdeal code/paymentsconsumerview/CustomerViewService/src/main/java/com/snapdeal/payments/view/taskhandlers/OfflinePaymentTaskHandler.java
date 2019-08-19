/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.tsm.entity.NotificationMessage;

/**
 * @author shubham.bansal
 *
 */
@Service
public class OfflinePaymentTaskHandler extends BaseTaskHandler {
	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;

	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception{
		onlineTaskHandler.execute(notifactionMsg);
		
	}
	
	

}
