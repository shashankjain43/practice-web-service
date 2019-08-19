package com.snapdeal.ims.dto;

import java.io.Serializable;

import com.snapdeal.ims.enums.SocialSource;

import lombok.Data;

/**
 * This dto is used for common information that is shared from social network.
 */
@Data
public class UserSocialDetailsDTO implements Serializable {

   private static final long serialVersionUID = 2334927384659278318L;
   private String socialId;
   private String aboutMe;
   private String photoURL;
   private SocialSource socialSource;
}