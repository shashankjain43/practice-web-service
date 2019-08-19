package com.snapdeal.ims.dao;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.WhiteListAPI;

/**
 * @author himanshu
 *
 */
public interface IWhiteListAPIsDao {

   /**
    * Creates new entry in whitelist_apis
    * @param whiteListAPI
    */
   public void create(WhiteListAPI whiteListAPI);
   
   /**
    * Return all whitelist_apis records from database
    * @return
    */
   public List<WhiteListAPI> getAllEntities();
   
   /**
    * Updates flag of whitelist_api having composite key (clientId, apiUri, apiMethod)
    * @param whiteListAPI
    */
   public void update(WhiteListAPI whiteListAPI);
   
   /**
    * removes record from whitelist_api having composite key (clientId, apiUri, apiMethod)
    * @param whiteListAPI
    */
   public void remove(WhiteListAPI whiteListAPI);
}
