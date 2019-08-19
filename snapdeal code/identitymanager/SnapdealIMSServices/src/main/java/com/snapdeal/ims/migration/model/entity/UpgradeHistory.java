package com.snapdeal.ims.migration.model.entity;

import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpgradeHistory {
   
   private long upgradeId;
   
   private Upgrade upgradeStatus;
   
   private State currentState;
   
   private Timestamp createdDate;

}
