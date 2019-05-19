package com.shashank.noon.exception;

public class LibraryServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	   private String errCode;
	   private String errMsg;

	   public LibraryServiceException(String errMsg) {
	      super(errMsg);
	      this.errMsg = errMsg;
	   }
	   public LibraryServiceException(String errCode, String errMsg) {
		      super(errMsg);
		      this.errCode = errCode;
		      this.errMsg = errMsg;
		   }

	   public LibraryServiceException(String errCode, String message, Throwable cause) {
	      super(message, cause);
	      this.errCode = errCode;
	      this.errMsg = message;
	   }

}
