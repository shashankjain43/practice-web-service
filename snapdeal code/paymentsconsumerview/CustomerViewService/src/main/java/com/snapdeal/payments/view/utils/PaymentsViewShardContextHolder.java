package com.snapdeal.payments.view.utils;

import java.util.Stack;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Slf4j
public class PaymentsViewShardContextHolder {

	private static final ThreadLocal<Stack<String>> contextHolder = new ThreadLocal();

	public static void setShardKey(String shardKey) {
		Assert.notNull(shardKey, "shardKey cannot be null");
		if (contextHolder.get() != null) {
			((Stack) contextHolder.get()).push(shardKey);
		} else {
			Stack<String> instrumentStack = new Stack();
			instrumentStack.push(shardKey);
			contextHolder.set(instrumentStack);
		}
	}

	public static String getShardKey() {
		if ((contextHolder.get() == null)
				|| (((Stack) contextHolder.get()).empty()))
			return null;
		return (String) ((Stack) contextHolder.get()).peek();
	}

	public static void clearShardKey() {
		if ((contextHolder.get() == null)
				|| (((Stack) contextHolder.get()).empty())) {
			throw new RuntimeException(
					"Reset UserContext Without Set is Invalid");
		}
		//log.debug("Resetting contextHolder shard key Size @Stack:"
			//	+ ((Stack) contextHolder.get()).size());
		((Stack) contextHolder.get()).pop();
		if (((Stack) contextHolder.get()).empty()) {
			contextHolder.remove();
		}
	}
}