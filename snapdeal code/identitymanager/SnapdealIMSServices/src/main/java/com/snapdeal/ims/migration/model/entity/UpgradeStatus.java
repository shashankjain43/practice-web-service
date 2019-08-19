package com.snapdeal.ims.migration.model.entity;


import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;

@Data
public class UpgradeStatus {
   
   private long id;
   
   private String email;
   
   private State initialState;
   
   private State currentState;
   
   private Upgrade upgradeStatus;
   
   private String userId;
   
   private String fcId;
   
   private String sdId;
   
   private boolean dontUpgrade;
   
   private UpgradeSource upgradeSource;
   
   private UpgradeChannel upgradeChannel;

   private Date createdDate;
   
   private Timestamp updatedDate;
   
}
