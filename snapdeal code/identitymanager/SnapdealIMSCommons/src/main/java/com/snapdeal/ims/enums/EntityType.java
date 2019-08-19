package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EntityType {
   DOMAIN("DOMAIN"), EMAIL("EMAIL");

   private final String entityType;

   private EntityType(String entityType) {
      this.entityType = entityType;
   }

   @org.codehaus.jackson.annotate.JsonValue
   public final String getValue() {
      return this.entityType;
   }

   @JsonCreator
   public static EntityType forName(String entityType) {
      if (null != entityType) {
         for (EntityType eachEntityType : values()) {
            if (eachEntityType.getValue().equalsIgnoreCase(entityType)) {
               return eachEntityType;
            }
         }
      }
      return null;
   }

}
