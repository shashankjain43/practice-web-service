package com.snapdeal.ims.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * Token details.
 */
@Data
public class TokenInformationDTO implements Serializable {

	private static final long serialVersionUID = -4574307093740873138L;
   private String token;
   private String tokenExpiry;
   private String globalToken;
   private String globalTokenExpiry;
}