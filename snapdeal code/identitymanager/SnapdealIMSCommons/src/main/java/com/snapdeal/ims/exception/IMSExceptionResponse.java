package com.snapdeal.ims.exception;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class IMSExceptionResponse implements Serializable {
	private String errorCode;
	private String message;
	private static final long serialVersionUID = 1L;
}
