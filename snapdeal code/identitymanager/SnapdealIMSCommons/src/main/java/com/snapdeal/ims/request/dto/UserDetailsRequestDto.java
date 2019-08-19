package com.snapdeal.ims.request.dto;

import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Language;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.DOB;
import com.snapdeal.ims.validator.annotation.Name;

import javax.validation.constraints.Size;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDetailsRequestDto implements Serializable {

	private static final long serialVersionUID = -1357493362672574030L;
	
	@Name
	private String firstName;
	@Name(type = Name.NameType.MIDDLE)
	private String middleName;
	@Name(type = Name.NameType.LAST)
	private String lastName;
	@Name(type = Name.NameType.DISPLAY)
	private String displayName;
	private Gender gender;
	@Size(max = 10, message = IMSRequestExceptionConstants.INVALID_DOB)
	@DOB
	private String dob;

	private Language languagePref;
}
