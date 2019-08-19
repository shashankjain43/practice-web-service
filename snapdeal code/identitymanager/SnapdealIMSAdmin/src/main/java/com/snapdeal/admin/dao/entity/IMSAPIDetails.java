package com.snapdeal.admin.dao.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IMSAPIDetails implements Serializable{

   private static final long serialVersionUID = -2631353276854307847L;

   private long id;
   private String apiMethod;
   private String apiURI;
   private String alias;
   
}
