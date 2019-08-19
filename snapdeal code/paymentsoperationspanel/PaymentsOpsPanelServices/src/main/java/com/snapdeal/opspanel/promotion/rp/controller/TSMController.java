package com.snapdeal.opspanel.promotion.rp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.tsm.client.impl.AbandonTxnServiceClientImpl;
import com.snapdeal.payments.tsm.client.impl.AdminServiceClientImpl;
import com.snapdeal.payments.tsm.client.impl.RedriveTxnServiceClientImpl;
import com.snapdeal.payments.tsm.constants.RestURIConstants;
import com.snapdeal.payments.tsm.exception.ClientRequestParameterException;
import com.snapdeal.payments.tsm.exception.HttpTransportException;
import com.snapdeal.payments.tsm.exception.ServiceException;
import com.snapdeal.payments.tsm.request.AddLockAcquiredNotificationRequest;
import com.snapdeal.payments.tsm.request.ClientSyncEndpointRequest;
import com.snapdeal.payments.tsm.request.CreateClientRequest;
import com.snapdeal.payments.tsm.request.CreateClientStateRequest;
import com.snapdeal.payments.tsm.request.CreateLockArnRequest;
import com.snapdeal.payments.tsm.request.CreateTxnTypeRequest;
import com.snapdeal.payments.tsm.request.GetAbandonTxnRequest;
import com.snapdeal.payments.tsm.request.GetAllClientRequest;
import com.snapdeal.payments.tsm.request.GetAllClientStateRequest;
import com.snapdeal.payments.tsm.request.GetAllTxnTypeTopicRequest;
import com.snapdeal.payments.tsm.request.GetRedriveJobStatusRequest;
import com.snapdeal.payments.tsm.request.RedriveByTxnIdAndTypeRequest;
import com.snapdeal.payments.tsm.request.ReinstateAbandonTxnRequest;
import com.snapdeal.payments.tsm.request.UpdateClientLockArnRequest;
import com.snapdeal.payments.tsm.request.UpdateClientStateMapRequest;
import com.snapdeal.payments.tsm.request.UpdateClientStatusRequest;
import com.snapdeal.payments.tsm.request.UpdateNotificationArnRequest;

@RestController
@RequestMapping(RestURIConstants.BASE_URI)
public class TSMController {

   @Autowired
   private AbandonTxnServiceClientImpl abandonService;
   
   @Autowired
   private AdminServiceClientImpl clientService;
   
   @Autowired
   private RedriveTxnServiceClientImpl redriveService;
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.GET_ABANDON_TXN, method = RequestMethod.GET, produces = RestURIConstants.APPLICATION_JSON)
   @ResponseBody
   public GenericResponse getAbandonTxn(@PathVariable("clientId") String clientId,
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            HttpServletRequest httprequest) throws ServiceException, HttpTransportException,
            ClientRequestParameterException,Exception {
      
      GetAbandonTxnRequest request = new GetAbandonTxnRequest();
      
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String startDate = dateFormat.format(startTime);
      String endDate = dateFormat.format(endTime);
      return GenericControllerUtils
               .getGenericResponse(abandonService.getAbandonTxn(clientId, startDate, endDate, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.REINSTATE_ABANDON_TXN, method = RequestMethod.PUT, produces = RestURIConstants.APPLICATION_JSON)
   @ResponseBody
   public GenericResponse reinstateAbandonTxn(
            @PathVariable("clientId") String clientId,
            @RequestBody ReinstateAbandonTxnRequest request, HttpServletRequest httprequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,Exception {

      return GenericControllerUtils
               .getGenericResponse(abandonService.reinstateAbandonTxn(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.GET_ALL_CLIENT, method = RequestMethod.GET, produces = RestURIConstants.APPLICATION_JSON)
   @ResponseBody
   public GenericResponse getAllClient(HttpServletRequest httprequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      GetAllClientRequest request = new GetAllClientRequest();

      return GenericControllerUtils
               .getGenericResponse(clientService.getAllClient(request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.GET_ALL_CLIENT_STATE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
   @ResponseBody
   public GenericResponse getAllClientState(HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {


      return GenericControllerUtils
               .getGenericResponse(clientService.getAllClientState(new GetAllClientStateRequest()));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.GET_ALL_TXN_TYPE_TOPIC, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
   @ResponseBody
   public GenericResponse getAllTxnTypeTopic(HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.getAllTxnTypeTopic(new GetAllTxnTypeTopicRequest()));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT + RestURIConstants.CREATE_CLIENT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse createClient(@RequestBody CreateClientRequest request,
            HttpServletRequest httpRequest) throws ServiceException, HttpTransportException,
            ClientRequestParameterException, Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.createClient(request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT + RestURIConstants.UPDATE_NOTIFICATION_ARN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
   @ResponseBody
   public GenericResponse updateNotificationArn(
            @PathVariable(value = "clientId") String clientId,
            @RequestBody UpdateNotificationArnRequest request, HttpServletRequest httpRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.updateNotificationArn(clientId, request));

   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT + RestURIConstants.UPDATE_STATE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
   @ResponseBody
   public GenericResponse updateClientStateMap(
            @PathVariable(value = "clientId") String clientId,
            @RequestBody UpdateClientStateMapRequest request, HttpServletRequest httpRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(clientService.updateClientStateMap(clientId, request));

   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT + RestURIConstants.UPDATE_CLIENT_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
   @ResponseBody
   public GenericResponse updateStatusByClient(
            @PathVariable(value = "clientId") String clientId,
            @RequestBody UpdateClientStatusRequest request, HttpServletRequest httpRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.updateStatusByClient(clientId, request));

   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.CREATE_CLIENT_STATE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse createClientState(
            @RequestBody CreateClientStateRequest request,
            @PathVariable("clientId") String clientId, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.createClientState(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.CREATE_TXN_TYPE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse createTxnType(@RequestBody CreateTxnTypeRequest request,
            @PathVariable("clientId") String clientId, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {

      return GenericControllerUtils
               .getGenericResponse(clientService.createTxnType(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.CREATE_LOCK_ARN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse createLockArn(@RequestBody CreateLockArnRequest request,
            @PathVariable("clientId") String clientId, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(clientService.createLockArn(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.UPDATE_LOCK_ARN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
   @ResponseBody
   public GenericResponse updateLockArnByClient(
            @RequestBody UpdateClientLockArnRequest request,
            @PathVariable("clientId") String clientId, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(clientService.updateLockArn(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.ADD_LOCK_ACQUIRED_NOTIFICATION_ARN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse addLockAquiredNotificationArn(
            @RequestBody AddLockAcquiredNotificationRequest request,
            @PathVariable("clientId") String clientId, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(clientService.addLockAquiredNotificationArn(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.CLIENT+RestURIConstants.CREATE_CLIENT_SYNC_ENDPOINT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   @ResponseBody
   public GenericResponse createClientSyncEndpoint(
            @PathVariable("clientId") String clientId,
            @RequestBody ClientSyncEndpointRequest request, HttpServletRequest httpServletRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(clientService.createClientSyncEndpoint(clientId, request));
   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.REDRIVABILITY+RestURIConstants.REDRIVE_NOTIFICATION_BY_TXNID_TYPE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   public GenericResponse redriveByTxnIdAndType(
            @RequestBody RedriveByTxnIdAndTypeRequest request, HttpServletRequest httpRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(redriveService.redriveByTxnIdAndType(request));

   }
   
   @PreAuthorize("(hasPermission('OPS_TSM_VIEW') or hasPermission('OPS_TSM_ACTION'))")
   @RequestMapping(value = RestURIConstants.REDRIVABILITY+RestURIConstants.GET_REDRIVE_JOB_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
   public GenericResponse getRedriveJobStatus(HttpServletRequest httpRequest)
            throws ServiceException, HttpTransportException, ClientRequestParameterException,
            Exception {
      
      return GenericControllerUtils
               .getGenericResponse(redriveService.getRedriveJobStatus(new GetRedriveJobStatusRequest()));

   }
   
}
