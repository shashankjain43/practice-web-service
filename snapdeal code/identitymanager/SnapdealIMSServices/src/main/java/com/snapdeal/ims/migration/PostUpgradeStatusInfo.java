package com.snapdeal.ims.migration;

import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.migration.dto.UpgradeDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostUpgradeStatusInfo {
   
   private State currentState;
   private State initialState;
   private Upgrade upgradeStatus;
   private UpgradeDto upgradeDto;
   private CreateOrUpdate createOrUpdate;

   private ConfigurationConstants subjectKey;
   private ConfigurationConstants emailTemplate;

   enum CreateOrUpdate {
      CREATE, UPDATE, OC_CREATE;
   }
}