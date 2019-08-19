package com.snapdeal.ims.dbmapper;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.WhiteListAPI;

/**
 * @author himanshu
 *
 */
public interface IWhiteListAPIsMapper {

   /**
    * @param whiteListAPI
    */
   public void create(WhiteListAPI whiteListAPI);
   
   /**
    * This will return all entries of whitelisted from table
    * @return
    */
   public List<WhiteListAPI> getAllEntities();
   
   /**
    * To update flag of allowed for a existing clientId+apiUrl+apiHTTPMethod
    * @param whiteListAPI
    */
   public void update(WhiteListAPI whiteListAPI);
   
   /**
    * To remove record with id clientId+apiUrl+apiHTTPMethod
    * @param whiteListAPI
    */
   public void remove(WhiteListAPI whiteListAPI);
}
