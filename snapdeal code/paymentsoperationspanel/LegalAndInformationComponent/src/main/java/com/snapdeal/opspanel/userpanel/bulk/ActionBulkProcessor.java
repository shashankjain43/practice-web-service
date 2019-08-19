package com.snapdeal.opspanel.userpanel.bulk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountRequest;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ActionBulkProcessor implements BulkProcessor {

   @Autowired
   ActionService actionService;

   @Override
   public List<BulkCSVRow> executeOperation(List<BulkCSVRow> bulkCSVRowList, ActionBulkRequest actionBulkRequest ) {
      EnableDisableUserRequest enableDisableUserRequest = null;
      int rowNumber = 0;
      for( BulkCSVRow csvRow : bulkCSVRowList ) {
         rowNumber ++;
         log.info( "Performing " + actionBulkRequest.getAction() + " On " + csvRow.getId() );
         switch( actionBulkRequest.getAction() ) {
            case ENABLE_USER :
            case DISABLE_USER :
   
               enableDisableUserRequest = new EnableDisableUserRequest();
               enableDisableUserRequest.setAction( actionBulkRequest.getAction() );
               enableDisableUserRequest.setUserId( csvRow.getId() );
               enableDisableUserRequest.setReason(actionBulkRequest.getReason());
               enableDisableUserRequest.setOtherReason(actionBulkRequest.getOtherReason());
   
               try {
                  EnableDisableUserResponse enableDisableResponse = actionService.enableDisableUser(enableDisableUserRequest);
                  csvRow.setStatus(enableDisableResponse.getMessage());
               } catch( InfoPanelException ipe ) {
                  log.info("Exception while enableDisableUser: CSV Row number " + rowNumber + " Exception: " + ipe.getStackTrace() );
                  csvRow.setStatus(ipe.getErrMessage());
               }
               break;
            case SUSPEND_WALLET :
               SuspendWalletRequest suspendWalletRequest = new SuspendWalletRequest();
               suspendWalletRequest.setUserId(csvRow.getId());
               suspendWalletRequest.setUserIdType(actionBulkRequest.getIdType());
               suspendWalletRequest.setReason(actionBulkRequest.getReason());
               suspendWalletRequest.setOtherReason(actionBulkRequest.getOtherReason());
               try {
                  actionService.suspendWallet(suspendWalletRequest);
                  csvRow.setStatus("wallet suspended successfuly" );
               } catch( InfoPanelException ipe ) {
                  log.info("Exception while suspendWallet: CSV Row number " + rowNumber + " Exception: " + ipe.getStackTrace() );
                  csvRow.setStatus( ipe.getErrMessage() );
               }
               break;
            case BLACK_LIST_USER:
            case WHITE_LIST_USER:
            	BlackListWhiteListUserRequest blackListWhiteListUserRequest = new BlackListWhiteListUserRequest();
            	blackListWhiteListUserRequest.setAction( actionBulkRequest.getAction() );
            	blackListWhiteListUserRequest.setEmailId( csvRow.getId() );
            	blackListWhiteListUserRequest.setOtherReason(actionBulkRequest.getOtherReason() );
            	blackListWhiteListUserRequest.setReason(actionBulkRequest.getReason());
            	try {
            		actionService.blackListWhiteListUser(blackListWhiteListUserRequest);
            		csvRow.setStatus("user blacklisted successfully");
            	} catch( InfoPanelException ipe ) {
            		log.info( "Exception while blacklisting user " + ipe.getStackTrace() );
            		csvRow.setStatus( ipe.getErrMessage() );
            	}
            	break;
            case ENABLE_WALLET:
            	EnableWalletRequest enableWalletRequest = new EnableWalletRequest();
            	enableWalletRequest.setUserId( csvRow.getId() );
            	enableWalletRequest.setUserIdType( actionBulkRequest.getIdType() );
            	enableWalletRequest.setReason( actionBulkRequest.getReason() );
            	enableWalletRequest.setOtherReason( actionBulkRequest.getOtherReason() );
                try {
                   actionService.enableWallet(enableWalletRequest);
                   csvRow.setStatus("wallet enabled successfuly" );
                } catch( InfoPanelException ipe ) {
                   log.info("Exception while enableWallet: CSV Row number " + rowNumber + " Exception: " + ipe.getStackTrace() );
                   csvRow.setStatus( ipe.getErrMessage() );
                }
                break;
            case CLOSE_USER_ACCOUNT:
            	CloseUserAccountRequest closeUserAccountRequest = new CloseUserAccountRequest();
            	closeUserAccountRequest.setUserId( csvRow.getId() );
            	closeUserAccountRequest.setReason( actionBulkRequest.getReason() );
            	closeUserAccountRequest.setOtherReason( actionBulkRequest.getOtherReason() );
                try {
                   actionService.closeUserAccount(closeUserAccountRequest);
                   csvRow.setStatus("Account closed successfully" );
                } catch( InfoPanelException ipe ) {
                   log.info("Exception while closeUserAccount: CSV Row number " + rowNumber + " Exception: " + ipe.getStackTrace() );
                   csvRow.setStatus( ipe.getErrMessage() );
                }
                break;
         }
      }
      return bulkCSVRowList;
   }
}
