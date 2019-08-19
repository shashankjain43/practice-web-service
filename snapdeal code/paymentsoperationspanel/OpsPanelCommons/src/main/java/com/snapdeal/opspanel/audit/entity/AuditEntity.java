package com.snapdeal.opspanel.audit.entity;

public class AuditEntity {
	
	private String requestId;

	private String IP;

	private String emailId;

	private String methodName;

	private String timeStamp;

	private String request;

	private String response;

	private Boolean failure;

	private String exception;

	private String reason;

	private String searchId;

	private String context;
	
	private String startDate;
	
	private String endDate;
	
	
	private int pageSize;
	
	private int offset;
	
	private int viewable;

	

	public AuditEntity() {
		super();
	}

	public AuditEntity(String requestId, String iP, String emailId, String methodName, String timeStamp, String request,
			String response, Boolean failure, String exception, String reason, String searchId, String context,String startDate,String endDate,int pageSize,int offset,int viewable) {
		super();
		this.requestId = requestId;
		IP = iP;
		this.emailId = emailId;
		this.methodName = methodName;
		this.timeStamp = timeStamp;
		this.request = request;
		this.response = response;
		this.failure = failure;
		this.exception = exception;
		this.reason = reason;
		this.searchId = searchId;
		this.context = context;
		this.offset=offset;
		this.pageSize=pageSize;
		this.startDate=startDate;
		this.endDate=endDate;
		this.viewable=viewable;
	}

	public int getViewable() {
		return viewable;
	}

	public void setViewable(int viewable) {
		this.viewable = viewable;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Boolean getFailure() {
		return failure;
	}

	public void setFailure(Boolean failure) {
		this.failure = failure;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}