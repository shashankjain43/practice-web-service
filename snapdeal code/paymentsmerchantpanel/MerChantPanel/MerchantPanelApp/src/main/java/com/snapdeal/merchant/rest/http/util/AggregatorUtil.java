package com.snapdeal.merchant.rest.http.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.aggregator.api.IPaymentAggregatorService;
import com.snapdeal.payments.aggregator.exception.client.HttpTransportException;
import com.snapdeal.payments.aggregator.exception.client.ServiceException;
import com.snapdeal.payments.aggregator.request.RefundRequest;
import com.snapdeal.payments.aggregator.response.RefundResponse;
import com.snapdeal.payments.metrics.annotations.Logged;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AggregatorUtil {

	@Autowired
	private IPaymentAggregatorService aggregatorService;

	@Autowired
	private MpanelConfig config;

	@Logged
	public RefundResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException {
		RefundResponse aggResponse = null;

		RefundRequest aggRequest = new RefundRequest();
		aggRequest.setMerchantId(request.getMerchantId());
		aggRequest.setReferenceId(request.getRefId());
		aggRequest.setSourceSystem(AppConstants.refundSourceSystem);
		aggRequest.setRefundAmount(request.getAmount());
		aggRequest.setMerchantTxnId(request.getOrderId());
		aggRequest.setComments(request.getComments());
		int i = -1;
		int maxRetry = config.getApiRetryCount();
		while (i < maxRetry) {
			try {
				
				log.info("initiating refund For Aggregator Request: {}",aggRequest);
				aggResponse = aggregatorService.refundPayment(aggRequest);
				log.info("Aggregator Response For Refund: {}",aggResponse);
				return aggResponse;
			} catch (HttpTransportException he) {
				log.error("Client exception {} pass {}", he, i + 2);
				if (i + 1 == maxRetry)
					throw new MerchantException(ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_CODE,
							ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_MSG);
			} catch (ServiceException se) {
				log.error("Error while refunding transaction {} : {}  error {}", request.getOrderId(),
						se.getErrorMessage(), se);
				throw new MerchantException(ErrorConstants.GENERIC_ERROR_CODE_FOR_AGGREGATOR_EXCEPTION,se.getErrors().get(0).getMessage());
			}

			i++;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				log.info("suppressed thread interruption. will continue to retry");
			}
		}

		return aggResponse;
	}

	public void setAggregatorService(IPaymentAggregatorService aggregatorService) {
		this.aggregatorService = aggregatorService;
	}

	public void setConfig(MpanelConfig config) {
		this.config = config;
	}

}
