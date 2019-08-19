package com.snapdeal.ims.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.dao.IWhiteListAPIsDao;
import com.snapdeal.ims.dbmapper.IWhiteListAPIsMapper;
import com.snapdeal.ims.dbmapper.entity.WhiteListAPI;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository("WhiteListAPIDaoImpl")
public class WhiteListAPIDaoImpl implements IWhiteListAPIsDao {

   @Autowired
   private IWhiteListAPIsMapper whiteListAPIMapper;
   
   @Override
   @Transactional("transactionManager")
   @Timed
   @Marked
   public void create(WhiteListAPI whiteListAPI) {
      whiteListAPIMapper.create(whiteListAPI);
   }

   @Override
   @Timed
   @Marked
   public List<WhiteListAPI> getAllEntities() {
      return whiteListAPIMapper.getAllEntities();
   }

   @Override
   @Transactional("transactionManager")
   @Timed
   @Marked
   public void update(WhiteListAPI whiteListAPI) {
      whiteListAPIMapper.update(whiteListAPI);
   }

   @Override
   @Timed
   @Marked
   public void remove(WhiteListAPI whiteListAPI) {
      whiteListAPIMapper.remove(whiteListAPI);
   }

}
