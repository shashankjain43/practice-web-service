package com.snapdeal.ums.admin.sdwallet.server.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletSendEmailRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DeleteSDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DeleteSDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAllActivityTypeDataRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAllActivityTypeDataResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletHistoryForMobileRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletHistoryForMobileResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.IsActivityTypeCodeExistsRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.IsActivityTypeCodeExistsResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.ModifySDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.ModifySDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.NumberOfRecordsRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.NumberOfRecordsResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletHistoryRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletHistoryResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeInfoSRO;
import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeSRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistoryInfoSRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletInfoSRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryRequest;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashAtBegOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtBegOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashAtEndOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtEndOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashEarningOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashEarningOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashExpiredThisMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashExpiredThisMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashUsedThisMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashUsedThisMonthResponse;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;
import com.snapdeal.ums.server.services.sdwallet.convertor.ISDWalletConvertorService;
import com.snapdeal.ums.services.UMSCheckpointBreachWarningService;
import com.snapdeal.ums.utils.UMSStringUtils;

@Service("umsSDWalletService")
public class SDWalletServiceImpl implements ISDWalletService {

	@Autowired
	private ISDWalletServiceInternal sdWalletServiceInternal;

	@Autowired
	private IUserServiceInternal userService;

	@Autowired
	private IEmailServiceInternal emailService;
	
	@Autowired
    private IMSService imsService;
	
	@Autowired
	private ISDWalletConvertorService convertorService;

	@Autowired
	private UMSCheckpointBreachWarningService umsCheckpointBreachObservationService;

	private static final Logger LOG = LoggerFactory
			.getLogger(SDWalletServiceImpl.class);

	@AuditableMethod
	@EnableMonitoring
	@Override
	public CreditSDWalletResponse creditSDWallet(CreditSDWalletRequest request) {
		CreditSDWalletResponse response = new CreditSDWalletResponse();
		Integer userId = request.getUserId();
		Integer amount = request.getAmount();
		Integer activityTypeId = request.getActivityTypeId();
		String orderCode = request.getOrderCode();
		Integer transactionId = request.getTransactionId();
		String source = request.getSource();
		if ((userId != null) && (amount != null) && (activityTypeId != null)
				&& (userId > 0) && (amount > 0) && (activityTypeId > 0)
				&& ((StringUtils.isEmpty(source)) != true)) {
			LOG.info("Initiating credit SDWallet for userId: " + userId
					+ " amount: " + amount + " activityTypeId: "
					+ activityTypeId + " orderCode: " + orderCode
					+ " transactionId: " + transactionId + " source: " + source);

			Integer txnIdCredit = null;
			txnIdCredit = sdWalletServiceInternal.creditSDWallet(
					request.getUserId(), request.getAmount(),
					request.getActivityTypeId(), request.getOrderCode(),
					request.getTransactionId(), request.getSource(),
					request.getExpiryDays(), request.getRequestedBy(),
					request.getIdempotentId(), request.getContextSRO(),response);
			//this is in case of duplicate request
			if(!response.isSuccessful()){
				response.setSuccessful(true);
				return response;
			}
			if (txnIdCredit != null) {
				LOG.info("Credited SDWallet SUCCESSFULLY for userId: " + userId
						+ " amount: " + amount + " activityTypeId: "
						+ activityTypeId + " orderCode: " + orderCode
						+ " transactionId: " + transactionId + " source: "
						+ source);
				response.setTransactionId(txnIdCredit);
				response.setSuccessful(true);
			} else {
				response.setSuccessful(false);
				response.setMessage("Credit of SDWallet is UNSUCCESSFUL for userId: "
						+ userId
						+ " amount: "
						+ amount
						+ " activityTypeId: "
						+ activityTypeId
						+ " orderCode: "
						+ orderCode
						+ " transactionId: "
						+ transactionId
						+ " source: "
						+ source);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
			LOG.info("Credit of SDWallet is UNSUCCESSFUL due to invalid request for userId: "
					+ userId
					+ " amount: "
					+ amount
					+ " activityTypeId: "
					+ activityTypeId
					+ " orderCode: "
					+ orderCode
					+ " transactionId: " + transactionId + " source: " + source);
		}
		return response;
	}

	@AuditableMethod
	@EnableMonitoring
	@Override
	public DebitSDWalletResponse debitSDWallet(DebitSDWalletRequest request) {
		DebitSDWalletResponse response = new DebitSDWalletResponse();
		Integer userId = request.getUserId();
		Integer amount = request.getAmount();
		Integer activityTypeId = request.getActivityTypeId();
		String orderCode = request.getOrderCode();
		String source = request.getSource();
		try {
			if ((userId != null) && (amount != null)
					&& (activityTypeId != null) && (userId > 0) && (amount > 0)
					&& (activityTypeId > 0)
					&& ((StringUtils.isEmpty(source)) != true)) {
				LOG.info("Initiating Debit SDWallet for userId: " + userId
						+ " amount: " + amount + " activityTypeId: "
						+ activityTypeId + " orderCode: " + orderCode
						+ " source: " + source);
				
				Integer txnIdDebit = null;
				txnIdDebit = sdWalletServiceInternal.debitSDWallet(userId,
						amount, activityTypeId, orderCode, source,
						request.getRequestedBy(), request.getIdempotentId(),
						request.getContextSRO(), response);
				//this is in case of duplicate request
				if(!response.isSuccessful()){
					response.setSuccessful(true);
					return response;
				}
				if (txnIdDebit != null) {
					response.setTransactionId(txnIdDebit);
					response.setSuccessful(true);
					LOG.info("Debited SDWallet SUCCESSFULLY for userId: "
							+ userId + " amount: " + amount
							+ " activityTypeId: " + activityTypeId
							+ " orderCode: " + orderCode + " source: " + source);
				} else {
					response.setSuccessful(false);
					response.setMessage("Insuffcient SDWalletCash to deduct");
					LOG.info("Debit of SDWallet UNSUCCESSFUL due to insufficient SDWalletCash for userId: "
							+ userId
							+ " amount: "
							+ amount
							+ " activityTypeId: "
							+ activityTypeId
							+ " orderCode: " + orderCode + " source: " + source);
				}
			} else {
				response.setSuccessful(false);
				response.setMessage("Invalid request");
				LOG.info("Debit of SDWallet UNSUCCESSFUL due to invalid request for userId: "
						+ userId
						+ " amount: "
						+ amount
						+ " activityTypeId: "
						+ activityTypeId
						+ " orderCode: "
						+ orderCode
						+ " source: " + source);
			}
		} catch (org.hibernate.StaleObjectStateException e) {
			response.setSuccessful(false);
			response.setMessage("Possible concurrent request found. Fraud scenario for userId  "
					+ userId);
			LOG.info("Debit of SDWallet UNSUCCESSFUL due to Concurrent request for userId: "
					+ userId
					+ " amount: "
					+ amount
					+ " activityTypeId: "
					+ activityTypeId
					+ " orderCode: "
					+ orderCode
					+ " source: "
					+ source);
		}
		return response;
	}

	@Override
	public GetSDWalletByUserIdResponse getSDWalletByUserId(
			GetSDWalletByUserIdRequest request) {
		GetSDWalletByUserIdResponse response = new GetSDWalletByUserIdResponse();
		Integer userId = request.getUserId();
		if ((userId != null) && (userId > 0)) {
			int sdWalletHistFetchLimit = CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)
					.getSDWalletHIstoryFetchLimit();
			List<SDWallet> sdWallets = sdWalletServiceInternal
					.getSDWalletByUserId(userId, sdWalletHistFetchLimit);
			if (sdWallets.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWallet data found for this userId");
			} else {
				List<SDWalletSRO> sdWalletSROs = new ArrayList<SDWalletSRO>();
				StringBuilder identifier = new StringBuilder(userId.toString());
				identifier.append(" called getSDWalletByUserId");
				// check for any misbehave
				umsCheckpointBreachObservationService
						.checkIfListSizeExceedsLimit(
								UMSCheckpointBreachWarningService.CheckpointBreachContext.SD_CASH_HISTORY,
								sdWallets, sdWalletHistFetchLimit - 1,
								identifier.toString());
				for (SDWallet wallet : sdWallets)
					sdWalletSROs.add(convertorService
							.getSDWalletSROfromEntity(wallet));
				SDWalletInfoSRO sdWalletInfoSRO = new SDWalletInfoSRO();
				sdWalletInfoSRO.setSdWalletSRO(sdWalletSROs);
				response.setSdWalletInfoSRO(sdWalletInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public SDWalletResponse getExpiredSDWallet(SDWalletRequest request) {
		SDWalletResponse response = new SDWalletResponse();
		if ((request.getUserId() != null) && (request.getUserId() > 0)) {
			List<SDWallet> sdWalletList = sdWalletServiceInternal
					.getExpiredSDWallet(request.getUserId());
			if (sdWalletList.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWallet data found for this userId");
			} else {
				List<SDWalletSRO> sdWalletSROs = new ArrayList<SDWalletSRO>();
				for (SDWallet sdWallet : sdWalletList)
					sdWalletSROs.add(convertorService
							.getSDWalletSROfromEntity(sdWallet));
				SDWalletInfoSRO sdWalletInfoSRO = new SDWalletInfoSRO();
				sdWalletInfoSRO.setSdWalletSRO(sdWalletSROs);
				response.setSdWalletInfoSRO(sdWalletInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public SDWalletResponse getSDWalletOfAvailableCredit(SDWalletRequest request) {
		SDWalletResponse response = new SDWalletResponse();
		if ((request.getUserId() != null) && (request.getUserId() > 0)) {
			List<SDWallet> sdWalletList = sdWalletServiceInternal
					.getSDWalletOfAvailableCredit(request.getUserId());
			if (sdWalletList.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWallet data found for this userId");
			} else {
				List<SDWalletSRO> sdWalletSROs = new ArrayList<SDWalletSRO>();
				for (SDWallet sdWallet : sdWalletList)
					sdWalletSROs.add(convertorService
							.getSDWalletSROfromEntity(sdWallet));
				SDWalletInfoSRO sdWalletInfoSRO = new SDWalletInfoSRO();
				sdWalletInfoSRO.setSdWalletSRO(sdWalletSROs);
				response.setSdWalletInfoSRO(sdWalletInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Deprecated
	@Override
	public SDWalletHistoryResponse getSDWalletHistoryOfDebit(
			SDWalletHistoryRequest request) {
		SDWalletHistoryResponse response = new SDWalletHistoryResponse();
		if ((request.getUserId() != null) && (request.getUserId() > 0)) {
			List<SDWalletHistory> sdWalletHistories = sdWalletServiceInternal
					.getSDWalletHistoryOfDebit(request.getUserId());
			if (sdWalletHistories.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWalletHistory data found for this userId");
			} else {
				List<SDWalletHistorySRO> historySROs = new ArrayList<SDWalletHistorySRO>();
				for (SDWalletHistory sdWalletHistory : sdWalletHistories)
					historySROs.add(convertorService
							.getSDWalletHistorySROfromEntity(sdWalletHistory));
				SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO = new SDWalletHistoryInfoSRO();
				sdWalletHistoryInfoSRO.setSdWalletHistorySRO(historySROs);
				response.setSdWalletHistoryInfoSRO(sdWalletHistoryInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public SDWalletHistoryResponse getDebitSDWalletHistoryByOrderId(
			SDWalletHistoryRequest request) {
		SDWalletHistoryResponse response = new SDWalletHistoryResponse();
		if ((StringUtils.isEmpty(request.getOrderId())) != true) {
			List<SDWalletHistory> sdWalletHistories = sdWalletServiceInternal
					.getDebitSDWalletHistoryByOrderId(request.getOrderId());
			if (sdWalletHistories.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWalletHistory data found for this orderId");
			} else {
				List<SDWalletHistorySRO> historySROs = new ArrayList<SDWalletHistorySRO>();
				for (SDWalletHistory sdWalletHistory : sdWalletHistories)
					historySROs.add(convertorService
							.getSDWalletHistorySROfromEntity(sdWalletHistory));
				SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO = new SDWalletHistoryInfoSRO();
				sdWalletHistoryInfoSRO.setSdWalletHistorySRO(historySROs);
				response.setSdWalletHistoryInfoSRO(sdWalletHistoryInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public GetCompleteSDWalletHistoryByUserIdResponse getCompleteSDWalletHistoryByUserId(
			GetCompleteSDWalletHistoryByUserIdRequest request) {
		GetCompleteSDWalletHistoryByUserIdResponse response = new GetCompleteSDWalletHistoryByUserIdResponse();
		Integer userId = request.getUserId();
		if ((userId != null) && (userId > 0)) {
			int sdWalletHistFetchLimit = CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)
					.getSDWalletHIstoryFetchLimit();
			List<SDWalletHistory> sdWalletHistories = sdWalletServiceInternal
					.getCompleteSDWalletHistoryByUserId(userId,
							sdWalletHistFetchLimit);

			if (sdWalletHistories.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWalletHistory data found for this userId");
			} else {
				// check for any misbehave
				umsCheckpointBreachObservationService
						.checkIfListSizeExceedsLimit(
								UMSCheckpointBreachWarningService.CheckpointBreachContext.SD_CASH_HISTORY,
								sdWalletHistories, sdWalletHistFetchLimit - 1,
								userId.toString());
				List<SDWalletHistorySRO> historySROs = new ArrayList<SDWalletHistorySRO>();
				for (SDWalletHistory sdWalletHistory : sdWalletHistories)
					historySROs.add(convertorService
							.getSDWalletHistorySROfromEntity(sdWalletHistory));
				SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO = new SDWalletHistoryInfoSRO();
				sdWalletHistoryInfoSRO.setSdWalletHistorySRO(historySROs);
				response.setSdWalletHistoryInfoSRO(sdWalletHistoryInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public GetSDWalletHistoryForMobileResponse getSDWalletHistoryForMobile(
			GetSDWalletHistoryForMobileRequest request) {
		GetSDWalletHistoryForMobileResponse response = new GetSDWalletHistoryForMobileResponse();
		Integer userId = request.getUserId();
		String mode = request.getMode();
		Integer start = request.getStart();
		Integer pageSize = request.getPageSize();
		if ((userId != null) && (userId > 0)
				&& ((StringUtils.isEmpty(mode)) != true)
				&& ((mode.equals("cr")) || (mode.equals("db")))
				&& (start != null) && (start >= 0) && (pageSize != null)
				&& (pageSize > 0)) {
			int sdWalletHistFetchLimit = CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)
					.getSDWalletHIstoryFetchLimit();

			List<SDWalletHistory> sdHistories = sdWalletServiceInternal
					.getSDWalletHistoryForMobile(userId, mode, start, pageSize,
							sdWalletHistFetchLimit);
			if (sdHistories.isEmpty()) {
				response.setSuccessful(false);
				response.setMessage("No SDWalletHistory data found for this userId");
			} else {
				StringBuilder identifier = new StringBuilder(userId.toString());
				identifier.append(" called getSDWalletHistoryForMobile");
				// check for any misbehave
				umsCheckpointBreachObservationService
						.checkIfListSizeExceedsLimit(
								UMSCheckpointBreachWarningService.CheckpointBreachContext.SD_CASH_HISTORY,
								sdHistories, sdWalletHistFetchLimit - 1,
								identifier.toString());
				List<SDWalletHistorySRO> historiesSRO = new ArrayList<SDWalletHistorySRO>();

				for (SDWalletHistory sdWalletHistory : sdHistories)
					historiesSRO.add(convertorService
							.getSDWalletHistorySROfromEntity(sdWalletHistory));
				SDWalletHistoryInfoSRO sdWalletHistoryInfoSRO = new SDWalletHistoryInfoSRO();
				sdWalletHistoryInfoSRO.setSdWalletHistorySRO(historiesSRO);
				response.setSdWalletHistoryInfoSRO(sdWalletHistoryInfoSRO);
				response.setSuccessful(true);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public NumberOfRecordsResponse getNumberOfRecordsInSDWalletHistory(
			NumberOfRecordsRequest request) {
		NumberOfRecordsResponse response = new NumberOfRecordsResponse();
		if ((request.getUserId() != null)
				&& (request.getUserId() > 0)
				&& ((StringUtils.isEmpty(request.getMode())) != true)
				&& ((request.getMode().equals("cr")) || (request.getMode()
						.equals("db")))) {
			Integer number = sdWalletServiceInternal
					.getNumberOfRecordsInSDWalletHistory(request.getUserId(),
							request.getMode());
			if (number != null) {
				response.setNumberOfRecords(number);
				response.setSuccessful(true);
			} else {
				response.setSuccessful(false);
				response.setMessage("No data exists in SDWallet for this userId");
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public GetAvailableBalanceInSDWalletByUserIdResponse getAvailableBalanceInSDWalletByUserId(
			GetAvailableBalanceInSDWalletByUserIdRequest request) {
		GetAvailableBalanceInSDWalletByUserIdResponse response = new GetAvailableBalanceInSDWalletByUserIdResponse();
		Integer userId = request.getUserId();
		if ((userId != null) && (userId > 0)) {
			Integer amount = sdWalletServiceInternal
					.getAvailableBalanceInSDWalletByUserId(userId);
			if (amount != null) {
				// proactive check for sdCash scrutiny
				int sdCashBreachLimit = CacheManager.getInstance()
						.getCache(UMSPropertiesCache.class)
						.getSDCashProactiveLimit();
				umsCheckpointBreachObservationService
						.checkIfValueExceedsLimit(
								UMSCheckpointBreachWarningService.CheckpointBreachContext.SD_CASH,
								amount, sdCashBreachLimit, userId.toString());
				response.setAvailableAmount(amount);
				response.setSuccessful(true);
			} else {
				response.setSuccessful(false);
				response.setMessage("No data exists in SDWallet for this userId");
				response.setAvailableAmount(0);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@AuditableMethod
	@Override
	public RefundSDWalletAgainstOrderIdResponse refundSDWalletAgainstOrderId(
			RefundSDWalletAgainstOrderIdRequest request) {
		RefundSDWalletAgainstOrderIdResponse response = new RefundSDWalletAgainstOrderIdResponse();
		Integer userId = request.getUserId();
		Integer amount = request.getAmount();
		Integer activityTypeId = request.getActivityTypeId();
		String orderCode = request.getOrderCode();
		String source = request.getSource();
		if ((userId != null) && (amount != null) && (activityTypeId != null)
				&& (userId > 0) && (amount > 0) && (activityTypeId > 0)
				&& ((StringUtils.isEmpty(source)) != true)
				&& ((StringUtils.isEmpty(orderCode)) != true)) {
			LOG.info("Initiating Refund SDWallet for userId: " + userId
					+ " amount: " + amount + " activityTypeId: "
					+ activityTypeId + " orderCode: " + orderCode + " source: "+source);
			
			Integer txnIdRefund = null;
			txnIdRefund = sdWalletServiceInternal
					.refundSDWalletAgainstOrderId(userId, amount,
							activityTypeId, orderCode, source,
							request.getRequestedBy(), request.getIdempotentId(),
							request.getContextSRO(), response);
			//this is in case of duplicate request
			if(!response.isSuccessful()){
				response.setSuccessful(true);
				return response;
			}
			if (txnIdRefund != null) {
				response.setRefundSDWalletAgainstOrderId(txnIdRefund);
				response.setSuccessful(true);
				LOG.info("Refunded SDWallet SUCCESSFULLY for userId: " + userId
						+ " amount: " + amount + " activityTypeId: "
						+ activityTypeId + " orderCode: " + orderCode
						+ " source: " + source);
			} else {
				response.setSuccessful(false);
				response.setMessage("No orderCode data found against this refund");
				LOG.info("Refund SDWallet UNSUCCESSFUL as no orderCode data found against this refund for userId: "
						+ userId
						+ " amount: "
						+ amount
						+ " activityTypeId: "
						+ activityTypeId
						+ " orderCode: "
						+ orderCode
						+ " source: " + source);
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
			LOG.info("Refund SDWallet UNSUCCESSFUL because of invalid request for userId: "
					+ userId
					+ " amount: "
					+ amount
					+ " activityTypeId: "
					+ activityTypeId
					+ " orderCode: "
					+ orderCode
					+ " source: "
					+ source);
		}
		return response;
	}

	@Override
	public GetAllActivityTypeDataResponse getAllActivityTypeData(
			GetAllActivityTypeDataRequest request) {
		GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
		List<SDWalletActivityType> list = sdWalletServiceInternal
				.getAllActivityTypeData();
		if (list.isEmpty()) {
			response.setSuccessful(false);
			response.setMessage("No data found in Acitivity Type table");
		} else {
			List<ActivityTypeSRO> activityTypeSROs = new ArrayList<ActivityTypeSRO>();
			for (SDWalletActivityType activityType : list)
				activityTypeSROs.add(convertorService
						.getActivityTypeSROfromEntity(activityType));
			ActivityTypeInfoSRO activityTypeInfoSRO = new ActivityTypeInfoSRO();
			activityTypeInfoSRO.setActivityTypeSRO(activityTypeSROs);
			response.setActivityTypeInfoSRO(activityTypeInfoSRO);
			response.setSuccessful(true);
		}
		return response;
	}

	@Override
	public AddSDWalletActivityTypeResponse addSDWalletActivityType(
			AddSDWalletActivityTypeRequest request) {
		AddSDWalletActivityTypeResponse response = new AddSDWalletActivityTypeResponse();
		String name = request.getName();
		String code = request.getCode();
		String sdCash = request.getSdCash();
		Integer expiryDays = request.getExpiryDays();
		boolean async = request.isAsync();
		boolean enabled = request.isEnabled();
		if (((StringUtils.isEmpty(name)) != true)
				&& ((StringUtils.isEmpty(code)) != true) && (expiryDays >= 1)) {
			sdWalletServiceInternal.addSDWalletActivityType(name, code, sdCash,
					expiryDays, async, enabled);
			response.setAddActivityType(true);
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public DeleteSDWalletActivityTypeResponse deleteSDWalletActivityType(
			DeleteSDWalletActivityTypeRequest request) {
		DeleteSDWalletActivityTypeResponse response = new DeleteSDWalletActivityTypeResponse();
		Integer id = request.getId();
		String code = request.getCode();
		if ((id >= 1) || ((StringUtils.isEmpty(code)) != true)) {
			sdWalletServiceInternal.deleteSDWalletActivityType(id, code);
			response.setDeleteActivityType(true);
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public ModifySDWalletActivityTypeResponse modifySDWalletActivityType(
			ModifySDWalletActivityTypeRequest request) {
		ModifySDWalletActivityTypeResponse response = new ModifySDWalletActivityTypeResponse();
		ActivityTypeSRO activityTypeSRO = request.getActivityTypeSRO();
		if (((StringUtils.isEmpty(activityTypeSRO.getCode())) != true)
				&& (activityTypeSRO.getExpiryDays() >= 1)) {
			SDWalletActivityType sdWalletActivityType = convertorService
					.getActivityTypeEntityfromSRO(activityTypeSRO);
			sdWalletServiceInternal
					.modifySDWalletActivityType(sdWalletActivityType);
			response.setModifySDWalletActivityType(true);
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public IsActivityTypeCodeExistsResponse isActivityTypeCodeExists(
			IsActivityTypeCodeExistsRequest request) {
		IsActivityTypeCodeExistsResponse response = new IsActivityTypeCodeExistsResponse();
		String code = request.getCode();
		if ((StringUtils.isEmpty(code)) != true) {
			boolean isActivityTypeExists = sdWalletServiceInternal
					.isActivityTypeCodeExists(code);
			response.setActivityTypeCodeExists(isActivityTypeExists);
		}
		return response;
	}

	@Override
	public GetAllActivityTypeDataResponse getActivityTypeById(
			GetAllActivityTypeDataRequest request) {
		GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
		if (request.getActivityId() != null) {
			SDWalletActivityType sdWalletActivityType = sdWalletServiceInternal
					.getActivityTypeById(request.getActivityId());
			if (sdWalletActivityType != null) {
				ActivityTypeSRO activityTypeSRO = convertorService
						.getActivityTypeSROfromEntity(sdWalletActivityType);
				response.setActivityTypeSRO(activityTypeSRO);
				response.setSuccessful(true);
			} else {
				response.setSuccessful(false);
				response.setMessage("No activity exists corresponding to this Activity Code");
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public GetAllActivityTypeDataResponse getActivityTypeByCode(
			GetAllActivityTypeDataRequest request) {
		GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
		if (((StringUtils.isEmpty(request.getActivityCode())) != true)) {
			SDWalletActivityType sdWalletActivityType = sdWalletServiceInternal
					.getActivityTypeByCode(request.getActivityCode());
			if (sdWalletActivityType != null) {
				ActivityTypeSRO activityTypeSRO = convertorService
						.getActivityTypeSROfromEntity(sdWalletActivityType);
				response.setActivityTypeSRO(activityTypeSRO);
				response.setSuccessful(true);
			} else {
				response.setSuccessful(false);
				response.setMessage("No activity exists corresponding to this Activity Code");
			}
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request");
		}
		return response;
	}

	@Override
	public GetUserSDCashUsedThisMonthResponse getUserSDWalletUsedThisMonth(
			GetUserSDCashUsedThisMonthRequest request) {
		GetUserSDCashUsedThisMonthResponse response = new GetUserSDCashUsedThisMonthResponse();

		DateRange range = request.getRange();
		Integer userId = request.getUserId();
		if ((range != null) && (userId > 0)) {
			response.setUserSDCashUsedThisMonth(sdWalletServiceInternal
					.getUserSDCashUsedThisMonth(range, userId));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request : range =" + range
					+ " userId =" + userId);
		}
		return response;
	}

	@Override
	public GetUserSDCashEarningOfMonthResponse getUserSDWalletEarningOfMonth(
			GetUserSDCashEarningOfMonthRequest request) {
		GetUserSDCashEarningOfMonthResponse response = new GetUserSDCashEarningOfMonthResponse();
		DateRange range = request.getRange();
		Integer userId = request.getUserId();
		if ((range != null) && (userId > 0)) {
			response.setGetUserSDCashEarningOfMonth(sdWalletServiceInternal
					.getUserSDCashEarningOfMonth(range, userId));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request : range =" + range
					+ " userId =" + userId);
		}
		return response;
	}

	@Override
	public GetUserSDCashAtBegOfMonthResponse getUserSDWalletAtBegOfMonth(
			GetUserSDCashAtBegOfMonthRequest request) {
		GetUserSDCashAtBegOfMonthResponse response = new GetUserSDCashAtBegOfMonthResponse();
		DateRange range = request.getRange();
		Integer userId = request.getUserId();
		if ((range != null) && (userId > 0)) {
			response.setUserSDCashAtBegOfMonth(sdWalletServiceInternal
					.getUserSDCashOnDate(range.getStart(), userId));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request : range =" + range
					+ " userId =" + userId);
		}
		return response;
	}

	@Override
	public GetUserSDCashAtEndOfMonthResponse getUserSDWalletAtEndOfMonth(
			GetUserSDCashAtEndOfMonthRequest request) {
		GetUserSDCashAtEndOfMonthResponse response = new GetUserSDCashAtEndOfMonthResponse();
		DateRange currentMonthRange = request.getCurrentMonthRange();
		DateRange lastMonthRange = request.getLastMonthRange();
		int userId = request.getUserId();
		if ((lastMonthRange != null) && (currentMonthRange != null)
				&& (userId > 0)) {

			response.setGetUserSDCashAtEndOfMonth(sdWalletServiceInternal
					.getUserSDCashOnDate(lastMonthRange.getEnd(), userId));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request :  currentMonthRange ="
					+ currentMonthRange + " lastMonthRange=" + lastMonthRange
					+ " userId =" + userId);
		}
		return response;
	}

	@Override
	public GetAllUsersFromSDWalletHistoryResponse getAllUsersFromSDCashHistory(
			GetAllUsersFromSDWalletHistoryRequest request) {
		GetAllUsersFromSDWalletHistoryResponse response = new GetAllUsersFromSDWalletHistoryResponse();
		int firstResult = request.getFirstResult();
		int maxResults = request.getMaxResults();
		Date startDate = request.getStartDate();
		if (startDate != null) {
			response.setAllUsersFromSDCashHistory(sdWalletServiceInternal
					.getAllUsersFromSDCashHistory(startDate, firstResult,
							maxResults));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request :  startDate =" + startDate);
		}
		return response;
	}

	@Override
	public GetUserSDCashExpiredThisMonthResponse getUserSDWalletExpiredThisMonth(
			GetUserSDCashExpiredThisMonthRequest request) {
		GetUserSDCashExpiredThisMonthResponse response = new GetUserSDCashExpiredThisMonthResponse();

		DateRange range = request.getRange();
		Integer userId = request.getUserId();
		if ((range != null) && (userId > 0)) {
			response.setGetUserSDCashExpiryOfMonth(sdWalletServiceInternal
					.getUserSDWalletExpiredThisMonth(range, userId));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("Invalid request : range =" + range
					+ " userId =" + userId);
		}
		return response;
	}

	/**
	 * This service checks the flag if trigger email is set and calls send email
	 * service to the user credited SDCash if trigger email is set.
	 * 
	 * @param CreditSDWalletSendEmailRequest
	 *            containing triggerEmail flag.
	 * 
	 * @return CreditSDWalletResponse
	 * 
	 */
	@Override
	public CreditSDWalletResponse creditSDWalletAndSendEmail(
			CreditSDWalletSendEmailRequest request) {
		CreditSDWalletRequest newCreditSDWalletRequest = new CreditSDWalletRequest();
		CreditSDWalletResponse response = new CreditSDWalletResponse();
		newCreditSDWalletRequest.setUserId(request.getUserId());
		newCreditSDWalletRequest.setActivityTypeId(request.getActivityTypeId());
		newCreditSDWalletRequest.setAmount(request.getAmount());
		newCreditSDWalletRequest.setOrderCode(request.getOrderCode());
		newCreditSDWalletRequest.setExpiryDays(request.getExpiryDays());
		newCreditSDWalletRequest.setRequestedBy(request.getRequestedBy());
		newCreditSDWalletRequest.setSource(request.getSource());
		newCreditSDWalletRequest.setTransactionId(request.getTransactionId());
		newCreditSDWalletRequest.setIdempotentId(request.getIdempotentId());
		newCreditSDWalletRequest.setContextSRO(request.getContextSRO());
		response = creditSDWallet(newCreditSDWalletRequest);
		boolean sendEmail = request.isTriggerEmail();
		/**
		 * check if trigger mail is set
		 */
		//removing it to avoid fetching user details
		/*if (sendEmail == true && response.isSuccessful()) {
			emailService.sendSDWalletEmail(fetchUserEmailById(request.getUserId()),
					request.getAmount(), request.getExpiryDays());
		}*/
		return response;

	}
	
	/*private String fetchUserEmailById(int userId) {

		String email = null;
		UserOwner owner = imsService.getUserOwnerByUserId(userId);
		switch (owner) {
		case UMS:
			email = userService.getUserEmailById(userId);
			break;
		case IMS:
			UserSRO user = imsService.getUserFromIMSById(userId);
			if (user != null) {
				email = user.getEmail();
			}
			break;
		}
		return email;
	}*/
}
