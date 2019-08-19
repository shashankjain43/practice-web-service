package com.snapdeal.ims.wallet.task;

import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.wallet.request.WalletRequest;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalErrorException;
import com.snapdeal.payments.sdmoney.exceptions.ValidationException;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;
import com.snapdeal.payments.sdmoney.service.model.CreateAccountRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This Class is a worker class and it is responsible for wallet 
 * creation on post mobile verification step. Execute logic will 
 * be called by task scheduler once task is submitted for execution. 
 * It has retry logic in case it fails to create wallet for given user. 
 */
@Slf4j
@Component("WalletTask")
public class WalletTaskExecutor implements TaskExecutor<TaskRequest> {

   @Autowired
   private IUserDao userDaoImpl;
   
   @Autowired
   IUserCacheService userCacheService;
   
   @Autowired
   private SDMoneyClient sdMoneyServiceClient;

   @Override
   public ExecutorResponse execute(TaskRequest request) {

      final ExecutorResponse execResponse = new ExecutorResponse();
      final WalletRequest walletRequest = (WalletRequest) request;

      CreateWalletStatus createWalletStatus = CreateWalletStatus.FAILED;
      try {

         //Step0: Validate the request
         walletRequest.validate();

         /*
          * Step1: Create wallet
          * Even if Wallet Already Exists for given user,
          * wallet service de-dupe the request and gives 200
          * response. So, we don't require any extra check 
          * here to take care idempotency
          * */
         createWallet(walletRequest);

         execResponse.setCompletionLog(walletRequest.getUserId());
         execResponse.setStatus(Status.SUCCESS);
         createWalletStatus = CreateWalletStatus.CREATED;
      } catch (InternalErrorException e) {

         log.error("InternalServerException while creating wallet, metadata: " + 
                  walletRequest + "...retrying", e);
         
         RetryInfo retryInfo = new RetryInfo();
         retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
         retryInfo.setExpBase(ServiceCommonConstants.WALLET_EXPONENTIAL_BASE);
         retryInfo.setWaitTime(
                  ServiceCommonConstants.DEFAULT_ON_FAILURE_TASK_REEXECUTION_WAIT_TIME);
         execResponse.setAction(retryInfo);
         execResponse.setStatus(Status.RETRY);
      } catch (ValidationException e) {

         log.error("ValidationException while creating wallet metadata: " + 
                    walletRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      } catch (Exception e) {

         log.error("Exception occured while creating wallet, metadata: " + 
                   walletRequest, e);
         execResponse.setCompletionLog(e.getMessage());
         execResponse.setStatus(Status.FAILURE);
      }
    
      /*
       * Step2: Update Wallet Creation Status in UserTable
       * Even if it fails in this step then we will retry at some point of time
       * in future for sake of marking the status as SUCCESS.
       */
      updateWalletCreationStatus(walletRequest.getUserId(), createWalletStatus);
      return execResponse;
   }

   private void updateWalletCreationStatus(String userId, 
                                           CreateWalletStatus newStatus) {
	   userDaoImpl.updateCreateWalletStatus(userId, newStatus);
	   userCacheService.invalidateUserById(userId);
	   
   }

   /**
    * This method is mainly responsible for wallet creation
    * @param walletRequest
    */
   private void createWallet(WalletRequest walletRequest) {

      final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
      
      createAccountRequest.setBusinessEntity(walletRequest.getBusinessId());
      createAccountRequest.setSdIdentity(walletRequest.getUserId());
      
      log.debug("Calling CreateAccount[Wallet] Request: " + createAccountRequest);
      
      sdMoneyServiceClient.createAccount(createAccountRequest);
      
      log.debug("Wallet created successfully for request: " + createAccountRequest);
   }
}
