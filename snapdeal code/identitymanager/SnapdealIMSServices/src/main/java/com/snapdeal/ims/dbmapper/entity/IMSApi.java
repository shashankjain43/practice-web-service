package com.snapdeal.ims.dbmapper.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IMSApi implements Serializable{

   private static final long serialVersionUID = 4767007552625200483L;

   private long id;
   private String apiUri;
   private String apiMethod;
   private String alias;
   
}
