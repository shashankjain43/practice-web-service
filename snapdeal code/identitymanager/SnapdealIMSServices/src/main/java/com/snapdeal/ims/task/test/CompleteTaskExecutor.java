package com.snapdeal.ims.task.test;

import com.klickpay.fortknox.FortKnoxClient;
import com.klickpay.fortknox.FortKnoxException;
import com.klickpay.fortknox.MergeType;
import com.klickpay.fortknox.model.Response;
import com.snapdeal.ims.constants.FortKnoxExceptionConstant;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.wallet.request.UserMigrationStatusRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalErrorException;
import com.snapdeal.payments.sdmoney.exceptions.ValidationException;
import com.snapdeal.payments.sdmoney.service.model.CreateAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.UpdateUserMigrationStatusRequest;
import com.snapdeal.payments.sdmoney.service.model.type.UserMigrationStatus;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class is a worker class and it is responsible for :
 * 1. Wallet Creation
 * 2. Merge Cards using FortKnox
 * 3. Notify SD Money
 *
 */
@Slf4j
@Component("CompleteTask")
public class CompleteTaskExecutor implements TaskExecutor<TaskRequest> {

   @Autowired
   private IUserDao userDaoImpl;
   
   @Autowired
   private SDMoneyClient sdMoneyServiceClient;
   
   @Autowired
   private FortKnoxClient fortKnoxClient;

   @Override
   public ExecutorResponse execute(TaskRequest request) {

      final ExecutorResponse execResponse = new ExecutorResponse();
      final CompleteTaskRequest taskRequest = (CompleteTaskRequest) request;

      CreateWalletStatus createWalletStatus = CreateWalletStatus.FAILED;
      try {

         //Step0: Validate the request
         taskRequest.validate();

         /*
          * Step1: Create wallet
          */
         createWallet(taskRequest);
         createWalletStatus = CreateWalletStatus.CREATED;

         //Step 2 : Merge cards for fortKnox
         if(null!=taskRequest.getMergeType()){
            Response doOneCheckMergeResponse = mergeFortKnox(taskRequest);
            if (doOneCheckMergeResponse != null && doOneCheckMergeResponse.getErrorCode().equalsIgnoreCase(
                     (ServiceCommonConstants.FORTKNOX_SUCCESS_STATUS))) {
               log.info("doOneCheckMerge has been done successfully for request: "
                        + taskRequest);
            }else {
               log.error(
                        "Error while consuming doOneCheckMerge api, metadata: "
                                 + doOneCheckMergeResponse.getErrorMessage());
            }
         }
         //Step 3: Notify SD money
         notifyWalletforUserMigrationStatusChange(taskRequest);
         
         execResponse.setCompletionLog(taskRequest.getUserId());
         execResponse.setStatus(Status.SUCCESS);
      }catch (FortKnoxException e) {

         if (e.getErrorCode().equalsIgnoreCase(
               FortKnoxExceptionConstant.NETWORK_ERROR)
               || e.getErrorCode().equalsIgnoreCase(
                     FortKnoxExceptionConstant.INVALID_RESPONSE_CODE)
               || e.getErrorCode().equalsIgnoreCase(
                     FortKnoxExceptionConstant.INTERNAL_CLIENT_ERROR)) {
            log.error(
                  "InternalServerException while consuming doOneCheckMerge api, metadata: "
                        + taskRequest + "...retrying", e);
            log.error(e.getMessage());

            RetryInfo retryInfo = new RetryInfo();
            retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
            retryInfo.setExpBase(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_EXPONENTIAL_BASE);
            retryInfo
                  .setWaitTime(ServiceCommonConstants.FORTKNOX_FAILURE_TASK_REEXECUTION_WAIT_TIME);
            execResponse.setAction(retryInfo);
            execResponse.setStatus(Status.RETRY);
            
         } else {
            log.error(
                  "Exception occured while consuming doOneCheckMerge api, metadata: "
                        + taskRequest, e);
            execResponse.setCompletionLog(e.getMessage());
            execResponse.setStatus(Status.FAILURE);
         }
      }catch (InternalErrorException e) {

         log.error("InternalServerException while creating wallet, metadata: " + 
                  taskRequest + "...retrying", e);
         
         RetryInfo retryInfo = new RetryInfo();
         retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
         retryInfo.setExpBase(ServiceCommonConstants.WALLET_EXPONENTIAL_BASE);
         retryInfo.setWaitTime(
                  ServiceCommonConstants.DEFAULT_ON_FAILURE_TASK_REEXECUTION_WAIT_TIME);
         execResponse.setAction(retryInfo);
         execResponse.setStatus(Status.RETRY);
      } catch (ValidationException e) {

         log.error("ValidationException while creating wallet metadata: " + 
                  taskRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      } catch (Exception e) {

         log.error("Exception occured while creating wallet, metadata: " + 
                  taskRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      }
    
      /*
       * Step2: Update Wallet Creation Status in UserTable
       * Even if it fails in this step then we will retry at some point of time
       * in future for sake of marking the status as SUCCESS.
       */
      updateWalletCreationStatus(taskRequest.getUserId(), createWalletStatus);
      return execResponse;
   }

   private void updateWalletCreationStatus(String userId, 
                                           CreateWalletStatus newStatus) {
      userDaoImpl.updateCreateWalletStatus(userId, newStatus);
   }

   /**
    * This method is mainly responsible for wallet creation
    * @param walletRequest
    */
   private void createWallet(CompleteTaskRequest walletRequest) {

      final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
      
      createAccountRequest.setBusinessEntity(walletRequest.getBusinessId());
      createAccountRequest.setSdIdentity(walletRequest.getUserId());
      
      log.debug("Calling CreateAccount[Wallet] Request: " + createAccountRequest);
      
      sdMoneyServiceClient.createAccount(createAccountRequest);
      
      log.debug("Wallet created successfully for request: " + createAccountRequest);
   }
   
   
   /**
    * This method is mainly responsible for fortknox task creation
    * 
    * @param fortKnoxRequest
    * @throws FortKnoxException
    */
   private Response mergeFortKnox(CompleteTaskRequest fortKnoxRequest)
         throws FortKnoxException {

      log.debug("Calling doOneCheckMerge FortKnox Request: "
            + fortKnoxRequest);

      if (fortKnoxRequest.getMergeType() == null) {
         return fortKnoxClient
               .doOneCheckMerge(fortKnoxRequest.getUserId(),
                     fortKnoxRequest.getSdUserId(),
                     fortKnoxRequest.getFcUserId(),
                     fortKnoxRequest.getEmailId());
      } else if (fortKnoxRequest.getMergeType() == MergeType.SDOC) {
         return fortKnoxClient.doPartialOneCheckMerge(
               fortKnoxRequest.getUserId(), fortKnoxRequest.getSdUserId(),
               fortKnoxRequest.getEmailId(), MergeType.SDOC);
      } else if (fortKnoxRequest.getMergeType() == MergeType.FCOC) {
         return fortKnoxClient.doPartialOneCheckMerge(
               fortKnoxRequest.getUserId(), fortKnoxRequest.getFcUserId(),
               fortKnoxRequest.getEmailId(), MergeType.FCOC);
      }
      return null;
   }
   
   /**
    * this method is responsible for notifying wallet that user migration
    * status has been changed.
    * 
    * @param CompleteTaskRequest
    */
   private void notifyWalletforUserMigrationStatusChange(
         CompleteTaskRequest userMigrationStatusRequest) {

      final UpdateUserMigrationStatusRequest updateUserMigrationStatusRequest = new UpdateUserMigrationStatusRequest();

      updateUserMigrationStatusRequest.setEmailId(userMigrationStatusRequest
            .getEmailId());
      updateUserMigrationStatusRequest
            .setSdIdentity(userMigrationStatusRequest.getUserId());

      WalletUserMigrationStatus status = userMigrationStatusRequest
            .getUserMigrationStatus();
      if (status != null)
         updateUserMigrationStatusRequest
               .setUserMigrationStatus(UserMigrationStatus.valueOf(status
                     .toString()));

      log.debug("Calling notifyWallet for user Migration status change Request: "
            + updateUserMigrationStatusRequest);

      sdMoneyServiceClient
            .updateUserMigrationStatus(updateUserMigrationStatusRequest);

      log.debug("notified SdMoney for user mIgration Status "
            + updateUserMigrationStatusRequest);

   }
}
