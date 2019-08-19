package com.snapdeal.ims.dbmapper.entity;

import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.request.BlacklistEntityRequest;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlackList implements Serializable {

   private static final long serialVersionUID = -3486168073456955713L;
   
   private String entity;
   
   private EntityType entityType;

}
