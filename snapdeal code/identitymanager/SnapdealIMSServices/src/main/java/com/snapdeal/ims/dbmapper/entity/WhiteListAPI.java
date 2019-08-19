package com.snapdeal.ims.dbmapper.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WhiteListAPI implements Serializable {

   private static final long serialVersionUID = -4692106483113761278L;

   private String clientId;
   
   private long imsApiId;
   
   private boolean allowed;
   
}
