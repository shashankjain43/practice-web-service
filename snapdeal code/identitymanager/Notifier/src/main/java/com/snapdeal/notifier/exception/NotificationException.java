package com.snapdeal.notifier.exception;

import lombok.Builder;

@Builder
public class NotificationException extends RuntimeException {

	private String errorCode;

	private String reason;

	private static final long serialVersionUID = 1L;
}
