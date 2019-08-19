package com.snapdeal.ims.response;

import com.snapdeal.ims.enums.StatusEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author abhishek
 *
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ConfigureUserStateResponse extends AbstractResponse{

	private static final long serialVersionUID = 1L;
	private StatusEnum status;
}
