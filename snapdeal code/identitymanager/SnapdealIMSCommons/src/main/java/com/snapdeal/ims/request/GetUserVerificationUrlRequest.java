package com.snapdeal.ims.request;

import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.VerificationPurposeGenericValidation;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonPropertyOrder(alphabetic = true)
@GroupSequence(value = { First.class, GetUserVerificationUrlRequest.class })
public class GetUserVerificationUrlRequest extends AbstractRequest {

   private static final long serialVersionUID = -2505077408790848142L;

   @Email(groups = First.class)
   private String email;

   @VerificationPurposeGenericValidation
   private VerificationPurpose purpose;
   
   @NotNull
   private Merchant merchant;
}
