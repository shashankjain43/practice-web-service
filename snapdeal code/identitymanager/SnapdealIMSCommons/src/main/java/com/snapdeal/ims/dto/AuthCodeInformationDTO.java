package com.snapdeal.ims.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * Token details.
 */
@Data
public class AuthCodeInformationDTO implements Serializable {

	private static final long serialVersionUID = -4574307093740873138L;
   private String authCode;
   private String globalToken;
   private String globalTokenExpiry;
}