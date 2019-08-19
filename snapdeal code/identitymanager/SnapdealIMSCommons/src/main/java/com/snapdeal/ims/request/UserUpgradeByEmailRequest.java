package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.Token;
import com.snapdeal.ims.validator.annotation.UserUpgradeByEmailRequestValidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)
@UserUpgradeByEmailRequestValidation
public class UserUpgradeByEmailRequest extends AbstractRequest {

   private static final long serialVersionUID = 1L;

   @Email(mandatory = false)
   private String emailId;
   
   @Token
   @Size(max = 154 , message = IMSRequestExceptionConstants.GLOBAL_TOKEN_MAX_LENGTH)
   private String token;

   private UpgradeSource upgradeSource;

   private UpgradeChannel upgradeChannel;

}