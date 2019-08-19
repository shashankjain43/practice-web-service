/**
 * 
 */
package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author shachi
 *
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordResponse extends AbstractResponse {

	private static final long serialVersionUID = 9169437105382579679L;
	private boolean success;
}
