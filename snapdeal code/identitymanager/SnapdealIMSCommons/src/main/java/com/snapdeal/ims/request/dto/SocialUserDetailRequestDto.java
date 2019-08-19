package com.snapdeal.ims.request.dto;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import javax.validation.constraints.Size;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(exclude={"socialSecret"})
public class SocialUserDetailRequestDto extends UserDetailsRequestDto 
                                         implements Serializable {
   
   private static final long serialVersionUID = 1L;

   private String socialSrc;
   @Size(max = 127, message = IMSRequestExceptionConstants.SOCIAL_ID_MAX_LENGTH)
   private String socialId;
   @Size(max = 512, message = IMSRequestExceptionConstants.SOCIAL_TOKEN_MAX_LENGTH)
   private String socialToken;
   @Size(max = 127, message = IMSRequestExceptionConstants.SOCIAL_SECRET_MAX_LENGTH)
   private String socialSecret;
   @Size(max = 20, message = IMSRequestExceptionConstants.SOCIAL_EXPIRY_MAX_LENGTH)
   private String socialExpiry;  
   @Size(max = 1024, message = IMSRequestExceptionConstants.ABOUT_ME_MAX_LENGTH)
   private String aboutMe; 
   @Size(max = 1024, message = IMSRequestExceptionConstants.PHOTO_URL_MAX_LENGTH)
   private String photoURL;   
   
}
