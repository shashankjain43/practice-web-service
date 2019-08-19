package com.snapdeal.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.admin.commons.WhitelistApiSorting;
import com.snapdeal.admin.dao.IClientDetailsDao;
import com.snapdeal.admin.dao.IConfigurationDetailsDao;
import com.snapdeal.admin.dao.IImsApisDao;
import com.snapdeal.admin.dao.IWhitelistApisDao;
import com.snapdeal.admin.dao.entity.Client;
import com.snapdeal.admin.dao.entity.ConfigurationEntity;
import com.snapdeal.admin.dao.entity.IMSAPIDetails;
import com.snapdeal.admin.dao.entity.WhitelistAPIDetails;
import com.snapdeal.admin.request.GetWhiteListAPIsStatusRequest;
import com.snapdeal.admin.request.WhitelistApiUpdateStatusRequest;
import com.snapdeal.admin.response.ClientWhiteListAPIsResponse;
import com.snapdeal.admin.response.ClientWhiteListAPIsUpdateResponse;
import com.snapdeal.admin.response.WhiteListAPI;
import com.snapdeal.admin.service.IWhitelistAPIsService;
import com.snapdeal.admin.service.utils.Constants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.exception.ValidationException;

@Service
@Slf4j
public class WhitelistAPIsServiceImpl implements IWhitelistAPIsService {

   @Autowired
   private IImsApisDao imsApisDao;

   @Autowired
   private IWhitelistApisDao whitelistApisDao;

   @Autowired
   private IClientDetailsDao clientDetailsDao;

   @Autowired
   private IConfigurationDetailsDao configurationDetailsDao;

   @Override
   public ClientWhiteListAPIsResponse getWhiteListAPIsStatus(GetWhiteListAPIsStatusRequest request, HttpServletRequest servletRequest)
            throws ValidationException {

      ClientWhiteListAPIsResponse response = new ClientWhiteListAPIsResponse();
      List<WhiteListAPI> whitelistAPIList = new ArrayList<WhiteListAPI>();
      
      String clientId = request.getClientId();
      boolean isAllowedDefaultValue = getDefaultValue(clientId);
      
      // Fetch all IMS APIs
      List<IMSAPIDetails> imsApis = imsApisDao.getAllApis();

      //Fetch Whitelist API record for every api of this client
      for (IMSAPIDetails imsApi : imsApis) {
         long imsApiId = imsApi.getId();

         WhitelistAPIDetails whitelistApiDetails = whitelistApisDao.getWhitelistApiByClientIdAndImsApiId(
                  clientId, imsApiId);

         WhiteListAPI whitelistApi = new WhiteListAPI();
         whitelistApi.setApiDetails(imsApi);
         whitelistApi.setClientId(clientId);
         
         if (whitelistApiDetails == null) {
            whitelistApi.setAllowed(isAllowedDefaultValue);
         }else{
            whitelistApi.setAllowed(whitelistApiDetails.isAllowed());
         }
         
         // adding to response list
         whitelistAPIList.add(whitelistApi);
      }
      
      response.setTotalResult(whitelistAPIList.size());
      //Sort and paginate list
      whitelistAPIList = sortAndPaginateWhitelistAPIList(servletRequest, whitelistAPIList);
      response.setWhitelistApis(whitelistAPIList);
      response.setResult("OK");
      
      return response;
   }
   
   private List<WhiteListAPI> sortAndPaginateWhitelistAPIList(HttpServletRequest request, List<WhiteListAPI> list){
      int startPageIndex = Integer.parseInt(request
               .getParameter("jtStartIndex"));
         int numRecordsPerPage = Integer.parseInt(request
               .getParameter("jtPageSize"));
         String sortingParam = request.getParameter("jtSorting");

         //sort
         Comparator<WhiteListAPI> comparator = WhitelistApiSorting.getComparator(sortingParam);
         if (comparator == null) {
            Collections.sort(list, WhitelistApiSorting.getComparator(WhitelistApiSorting.API_URI_ASC));
         } else {
            Collections.sort(list, comparator);
         }
         
         //paginate
         int size = list.size();
         if (size >= (startPageIndex + numRecordsPerPage)) {
            return list.subList(startPageIndex,
                  startPageIndex + numRecordsPerPage);
         } else if (size >= startPageIndex
               && size < (startPageIndex + numRecordsPerPage)) {
            return list.subList(startPageIndex, size);
         }

         return list;
         
   }

   private boolean getDefaultValue(String clientId) {

      Merchant merchant = getMerchant(clientId);
      // first check for merchant specific config
      List<ConfigurationEntity> configsList = configurationDetailsDao.getConfig(
               Constants.CONFIG_KEY_APIS_WHITELIST_DEFAULT_VALUE, merchant.getMerchantName());
      if (configsList == null || configsList.isEmpty()) {
         // check for global config
         configsList = configurationDetailsDao
                  .getConfig(Constants.CONFIG_KEY_APIS_WHITELIST_DEFAULT_VALUE, "global");

         if (configsList == null || configsList.isEmpty()) {
            throw new InternalServerException(
                     IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errCode(),
                     IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errMsg());
         }

         boolean globalValue = Boolean.valueOf(configsList.get(0).getConfigValue());
         return globalValue;
      }
      
      boolean merchantValue = Boolean.valueOf(configsList.get(0).getConfigValue());
      return merchantValue;
   }

   private Merchant getMerchant(String clientId) {
      Client client = clientDetailsDao.getClientById(clientId);
      return client.getMerchant();
   }

   @Override
   public ClientWhiteListAPIsUpdateResponse allowAPI(WhitelistApiUpdateStatusRequest request)
            throws ValidationException {
      //check if record exists in whitelist_apis table
      WhitelistAPIDetails existingRecord = whitelistApisDao.getWhitelistApiByClientIdAndImsApiId(request.getClientId(), request.getImsApiId());
      if(null==existingRecord){
         WhitelistAPIDetails record = new WhitelistAPIDetails();
         record.setAllowed(true);
         record.setImsApiId(request.getImsApiId());
         record.setClientId(request.getClientId());
         
         whitelistApisDao.insert(record);
      }else{
         existingRecord.setAllowed(true);
         
         whitelistApisDao.updateStatus(existingRecord);
      }
      ClientWhiteListAPIsUpdateResponse response = new ClientWhiteListAPIsUpdateResponse();
      response.setResult("OK");
      return response;
   }

   @Override
   public ClientWhiteListAPIsUpdateResponse restrictAPI(WhitelistApiUpdateStatusRequest request)
            throws ValidationException {
    //check if record exists in whitelist_apis table
      WhitelistAPIDetails existingRecord = whitelistApisDao.getWhitelistApiByClientIdAndImsApiId(request.getClientId(), request.getImsApiId());
      if(null==existingRecord){
         WhitelistAPIDetails record = new WhitelistAPIDetails();
         record.setAllowed(false);
         record.setImsApiId(request.getImsApiId());
         record.setClientId(request.getClientId());
         
         whitelistApisDao.insert(record);
      }else{
         existingRecord.setAllowed(false);
         
         whitelistApisDao.updateStatus(existingRecord);
      }
      ClientWhiteListAPIsUpdateResponse response = new ClientWhiteListAPIsUpdateResponse();
      response.setResult("OK");
      return response;
   }

}
