package com.snapdeal.merchant.exceptionResponse;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class MPanelExceptionResponse implements Serializable {

   private static final long serialVersionUID = -4842387500722107934L;
   private String errorCode;
   private String message;
}
