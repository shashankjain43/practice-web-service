package com.snapdeal.ims.dbmapper.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.enums.SocialSource;

@Data
public class SocialUser implements Serializable {

   private static final long serialVersionUID = 7881143161581209140L;

   private String userId;

   private String socialId;

   private SocialSource socialSrc;

   private String socialToken;

   private String secret;

   private Timestamp expiry;

   private String photoURL;

   private String aboutMe;

   private Timestamp createdTime;

   private Timestamp updatedTime;

   private User user;
   
   private Merchant merchant;
   
   private Platform platform;
   
   private String resource;
   
   

}
