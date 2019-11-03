package com.snapdeal.payments.view.taskhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.tsm.entity.NotificationMessage;

@Component
public class EscrowPaymentTaskHandler  extends BaseTaskHandler {
	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;

	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception{
		onlineTaskHandler.execute(notifactionMsg);
	}
	
	

}