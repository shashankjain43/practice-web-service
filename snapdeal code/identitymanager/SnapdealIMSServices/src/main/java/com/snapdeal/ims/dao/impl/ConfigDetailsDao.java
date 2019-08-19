package com.snapdeal.ims.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.dao.IConfigDetailsDao;
import com.snapdeal.ims.dbmapper.IConfigDetailsMapper;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository
public class ConfigDetailsDao implements IConfigDetailsDao {

   @Autowired
   private IConfigDetailsMapper configDetailsMapper;

   @Override
   @Timed
   @Marked
   public List<ConfigDetails> getAllConfigs() {
      return configDetailsMapper.getAllConfigs();
   }

   @Override
   @Timed
   @Marked
   public ConfigDetails getConfig(String configKey, String configType) {
      return configDetailsMapper.getConfig(configKey, configType);
   }

   @Override
   @Timed
   @Marked
   public List<ConfigDetails> getConfigsByKey(String configKey) {
      return configDetailsMapper.getConfigsByKey(configKey);
   }

   @Override
   @Timed
   @Marked
   public List<ConfigDetails> getConfigsByType(String configType) {
      return configDetailsMapper.getConfigsByType(configType);
   }

}
