package com.snapdeal.ims.migration.dto;

import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostUpgradeStatusInfo {
   
   private State currentState;
   private State initialState;
   private Upgrade upgradeStatus;

}
