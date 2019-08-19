package com.snapdeal.admin.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.admin.commons.IMSApisSorting;
import com.snapdeal.admin.dao.IImsApisDao;
import com.snapdeal.admin.dao.entity.IMSAPIDetails;
import com.snapdeal.admin.request.CreateIMSApiRequest;
import com.snapdeal.admin.request.UpdateIMSApiRequest;
import com.snapdeal.admin.response.GetAllIMSApisResponse;
import com.snapdeal.admin.response.createIMSApiResponse;
import com.snapdeal.admin.response.updateIMSApiResponse;
import com.snapdeal.admin.service.IIMSApisService;
import com.snapdeal.ims.exception.ValidationException;

/**
 * @author himanshu
 *
 */
@Service
@Slf4j
public class IMSApisServiceImpl implements IIMSApisService {
   
   @Autowired
   private IImsApisDao imsApisDao;
   
   @Override
   public GetAllIMSApisResponse getAllApis(HttpServletRequest request) throws ValidationException {
      
      List<IMSAPIDetails> apisList = imsApisDao.getAllApis();
      GetAllIMSApisResponse response = new GetAllIMSApisResponse();

      response.setTotalResult(apisList.size());
      apisList = sortAndPaginateIMSAPIsList(request, apisList);
      response.setApisList(apisList);
      response.setResult("OK");
      
      return response;
   }
   
   private List<IMSAPIDetails> sortAndPaginateIMSAPIsList(HttpServletRequest request, List<IMSAPIDetails> list){
      int startPageIndex = Integer.parseInt(request
               .getParameter("jtStartIndex"));
         int numRecordsPerPage = Integer.parseInt(request
               .getParameter("jtPageSize"));
         String sortingParam = request.getParameter("jtSorting");

         //sort
         Comparator<IMSAPIDetails> comparator = IMSApisSorting.getComparator(sortingParam);
         if (comparator == null) {
            Collections.sort(list, IMSApisSorting.getComparator(IMSApisSorting.API_URI_ASC));
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

   @Override
   public createIMSApiResponse createIMSApi(CreateIMSApiRequest request) throws ValidationException {
      IMSAPIDetails imsApiDetails = new IMSAPIDetails();
      imsApiDetails.setAlias(request.getAlias());
      imsApiDetails.setApiMethod(request.getApiMethod());
      imsApiDetails.setApiURI(request.getApiURI());
      imsApisDao.insert(imsApiDetails);
      
      createIMSApiResponse response = new createIMSApiResponse();
      response.setImsApiDetails(imsApiDetails);
      response.setResult("OK");
      return  response;
   }

   @Override
   public updateIMSApiResponse updateIMSApi(UpdateIMSApiRequest request) throws ValidationException {
      IMSAPIDetails imsApiDetails = new IMSAPIDetails();
      imsApiDetails.setAlias(request.getAlias());
      imsApiDetails.setApiMethod(request.getApiMethod());
      imsApiDetails.setApiURI(request.getApiURI());
      imsApiDetails.setId(request.getId());
      imsApisDao.update(imsApiDetails);
      
      updateIMSApiResponse response = new updateIMSApiResponse();
      response.setResult("OK");   
      
      return response;
   }

}
