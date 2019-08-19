package com.snapdeal.opspanel.promotion.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.Data;

@Data
public class BulkPromotionSharedObject {

	private int processingRows;

	public void setProcessingRows( int processingRows ) {
		Lock lock = new ReentrantLock();
		lock.lock();
		this.processingRows = processingRows;
		lock.unlock();
	}
}
