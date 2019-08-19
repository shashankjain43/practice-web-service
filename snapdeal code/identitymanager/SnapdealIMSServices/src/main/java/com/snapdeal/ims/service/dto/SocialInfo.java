package com.snapdeal.ims.service.dto;

import com.snapdeal.ims.enums.SocialSource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SocialInfo {

   private SocialSource socialSrc;

   private String socialId;

   private String socialToken;

   private String socialSecret;

   private String socialExpiry;

   private String aboutMe;

   private String photoURL;

}
