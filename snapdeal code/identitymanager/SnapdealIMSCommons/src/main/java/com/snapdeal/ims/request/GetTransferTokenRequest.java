/**
 * 
 */
package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)
@GroupSequence(value = { First.class, Second.class, GetTransferTokenRequest.class })
public class GetTransferTokenRequest extends AbstractRequest {

   private static final long serialVersionUID = -5652063282134254034L;

   @Token
   @NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
   @Size(max = 154, message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
   private String token;
   
   @NotBlank(message = IMSRequestExceptionConstants.TARGET_IMS_CONSUMER_BLANK, groups = Second.class)
   private String targetImsConsumer;
}