/**
 * 
 */
package com.snapdeal.ims.request;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@GroupSequence(value={First.class,Second.class,VerifyUserRequest.class})
@ToString
public class VerifyUserRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	
	@NotBlank( message = IMSRequestExceptionConstants.CODE_IS_BLANK, groups = First.class)
	@Size( max = 64 ,message = IMSRequestExceptionConstants.CODE_MAX_LENGTH, groups = Second.class)
	private String code;
	
	@Override
	public String getHashGenerationString() {
		return super.getHashGenerationString(this);
	}

}
