package com.snapdeal.opspanel.userpanel.service.impl;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.userpanel.bulk.BulkCSVRow;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountRequest;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.AsyncBulkService;

import lombok.extern.slf4j.Slf4j;

@Service("AsyncBulkServiceImpl")
@Slf4j
public class AsyncBulkServiceImpl implements AsyncBulkService {

	@Autowired
	ActionService actionService;

	@Override
	@Async("actionBulkExecutor")
	public Future<BulkCSVRow> executeRow(int rowNumber, BulkCSVRow csvRow, ActionBulkRequest actionBulkRequest) {
		log.info("Performing " + actionBulkRequest.getAction() + " On " + csvRow.getId());
		switch (actionBulkRequest.getAction()) {
		case ENABLE_USER:
		case DISABLE_USER:

			EnableDisableUserRequest enableDisableUserRequest = new EnableDisableUserRequest();
			enableDisableUserRequest.setAction(actionBulkRequest.getAction());
			enableDisableUserRequest.setUserId(csvRow.getId());
			enableDisableUserRequest.setReason(actionBulkRequest.getReason());
			enableDisableUserRequest.setOtherReason(actionBulkRequest.getOtherReason());
			enableDisableUserRequest.setActionPerformer( actionBulkRequest.getActionPerformer() );
			enableDisableUserRequest.setRequestId( actionBulkRequest.getRequestId() );
			enableDisableUserRequest.setTypeOfFraud(actionBulkRequest.getTypeOfFraud());

			try {
				EnableDisableUserResponse enableDisableResponse = actionService
						.enableDisableUser(enableDisableUserRequest);
				csvRow.setStatus(enableDisableResponse.getMessage());
			} catch (InfoPanelException ipe) {
				log.info("Exception while enableDisableUser: CSV Row number " + rowNumber + " Exception: "
						+ ipe.getStackTrace());
				csvRow.setStatus(ipe.getErrMessage());
			}
			break;
		case SUSPEND_WALLET:
			SuspendWalletRequest suspendWalletRequest = new SuspendWalletRequest();
			suspendWalletRequest.setUserId(csvRow.getId());
			suspendWalletRequest.setUserIdType(actionBulkRequest.getIdType());
			suspendWalletRequest.setReason(actionBulkRequest.getReason());
			suspendWalletRequest.setOtherReason(actionBulkRequest.getOtherReason());
			suspendWalletRequest.setActionPerformer( actionBulkRequest.getActionPerformer() );
			suspendWalletRequest.setRequestId( actionBulkRequest.getRequestId() );
			suspendWalletRequest.setTypeOfFraud(actionBulkRequest.getTypeOfFraud());
			try {
				actionService.suspendWallet(suspendWalletRequest);
				csvRow.setStatus("wallet suspended successfuly");
			} catch (InfoPanelException ipe) {
				log.info("Exception while suspendWallet: CSV Row number " + rowNumber + " Exception: "
						+ ipe.getStackTrace());
				csvRow.setStatus(ipe.getErrMessage());
			}
			break;
		case BLACK_LIST_USER:
		case WHITE_LIST_USER:
			BlackListWhiteListUserRequest blackListWhiteListUserRequest = new BlackListWhiteListUserRequest();
			blackListWhiteListUserRequest.setAction(actionBulkRequest.getAction());
			blackListWhiteListUserRequest.setEmailId(csvRow.getId());
			blackListWhiteListUserRequest.setOtherReason(actionBulkRequest.getOtherReason());
			blackListWhiteListUserRequest.setReason(actionBulkRequest.getReason());
			blackListWhiteListUserRequest.setActionPerformer( actionBulkRequest.getActionPerformer() );
			blackListWhiteListUserRequest.setRequestId( actionBulkRequest.getRequestId() );
			blackListWhiteListUserRequest.setTypeOfFraud(actionBulkRequest.getTypeOfFraud());
			try {
				actionService.blackListWhiteListUser(blackListWhiteListUserRequest);
				csvRow.setStatus("user blacklisted successfully");
			} catch (InfoPanelException ipe) {
				log.info("Exception while blacklisting user " + ipe.getStackTrace());
				csvRow.setStatus(ipe.getErrMessage());
			}
			break;
		case ENABLE_WALLET:
			EnableWalletRequest enableWalletRequest = new EnableWalletRequest();
			enableWalletRequest.setUserId(csvRow.getId());
			enableWalletRequest.setUserIdType(actionBulkRequest.getIdType());
			enableWalletRequest.setReason(actionBulkRequest.getReason());
			enableWalletRequest.setOtherReason(actionBulkRequest.getOtherReason());
			enableWalletRequest.setActionPerformer( actionBulkRequest.getActionPerformer() );
			enableWalletRequest.setRequestId( actionBulkRequest.getRequestId() );
			enableWalletRequest.setTypeOfFraud(actionBulkRequest.getTypeOfFraud());
			try {
				actionService.enableWallet(enableWalletRequest);
				csvRow.setStatus("wallet enabled successfuly");
			} catch (InfoPanelException ipe) {
				log.info("Exception while enableWallet: CSV Row number " + rowNumber + " Exception: "
						+ ipe.getStackTrace());
				csvRow.setStatus(ipe.getErrMessage());
			}
			break;
		case CLOSE_USER_ACCOUNT:
			CloseUserAccountRequest closeUserAccountRequest = new CloseUserAccountRequest();
			closeUserAccountRequest.setUserId(csvRow.getId());
			closeUserAccountRequest.setReason(actionBulkRequest.getReason());
			closeUserAccountRequest.setOtherReason(actionBulkRequest.getOtherReason());
			closeUserAccountRequest.setActionPerformer( actionBulkRequest.getActionPerformer() );
			closeUserAccountRequest.setRequestId( actionBulkRequest.getRequestId() );
			closeUserAccountRequest.setTypeOfFraud(actionBulkRequest.getTypeOfFraud());
			try {
				actionService.closeUserAccount(closeUserAccountRequest);
				csvRow.setStatus("Account closed successfully");
			} catch (InfoPanelException ipe) {
				log.info("Exception while closeUserAccount: CSV Row number " + rowNumber + " Exception: "
						+ ipe.getStackTrace());
				csvRow.setStatus(ipe.getErrMessage());
			}
			break;
		}
		return new AsyncResult<BulkCSVRow>( csvRow );
	}
}
