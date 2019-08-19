package com.snapdeal.opspanel.bulk.processors;

import java.math.BigDecimal;

import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.opspanel.batch.model.BulkRefundInputCSVModel;
import com.snapdeal.opspanel.batch.model.BulkRefundOutputCSVModel;
import com.snapdeal.payments.aggregator.api.IPaymentAggregatorService;
import com.snapdeal.payments.aggregator.exception.client.ServiceException;
import com.snapdeal.payments.aggregator.request.RefundRequest;
import com.snapdeal.payments.aggregator.response.RefundResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Data
@Slf4j
public class BulkRefundBatchProcessor implements ItemProcessor<BulkRefundInputCSVModel, BulkRefundOutputCSVModel> {

	@Autowired
	private IPaymentAggregatorService aggregatorService;

	private String refundKey;

	private String merchantId;

	public BulkRefundOutputCSVModel process(BulkRefundInputCSVModel input) throws Exception {

		BulkRefundOutputCSVModel output = new BulkRefundOutputCSVModel();
		RefundRequest refundRequest = new RefundRequest();
		refundRequest.setMerchantTxnId(input.getId().trim());
		refundRequest.setRefundAmount(new BigDecimal(input.getAmount().trim()));
		refundRequest.setComments(input.getComments().trim());
		refundRequest.setReferenceId(merchantId+refundKey.trim() + input.getId().trim());
		refundRequest.setMerchantId(merchantId);
		refundRequest.setSourceSystem("OPS panel");

		String feeReversalCode = input.getFeeReversalCode();
		if(feeReversalCode!=null && feeReversalCode.length()!=0){
			refundRequest.setFeeReversalCode(feeReversalCode);
		}
		
		String finalPlatformField=input.getPlatformId();
		if(finalPlatformField!=null && finalPlatformField.length()!=0){
			refundRequest.setPlatformId(finalPlatformField);
		}
		
		RefundResponse refundResponse = null;
		if (log.isInfoEnabled()) {
			log.info("BULK REFUND STEP: Going to hit refund api for reference id  :" + merchantId+refundKey.trim()
					+ input.getId().trim() + "\n with Transaction id" + refundRequest.getTxnId()
					+ "\n with merchant id : " + refundRequest.getMerchantId() + "\n with merchant transation id : "
					+ refundRequest.getMerchantTxnId() + "\n with Comments :" + refundRequest.getComments()+"\n platformId: "+refundRequest.getPlatformId());
		}
		try {
			refundResponse = aggregatorService.refundPayment(refundRequest);
			output.setStatus(refundResponse.getStatus());
			output.setMessage(refundResponse.getRefundTxnId());
			if (log.isInfoEnabled()) {
				log.info("BULK REFUND STEP: API For refund has been executed successfully for reference id "
						+ refundKey.trim() + input.getId().trim() + "\n and merchant transaction id "
						+ refundResponse.getMerchantTxnId() + "\n with Refund Transaction id :"
						+ refundResponse.getRefundTxnId());
			}
		} catch (ServiceException e) {
			output.setStatus("FAILED");
			output.setMessage(e.getErrorMessage().replaceAll("\\r|\\n", ""));
			if (log.isInfoEnabled()) {
				log.info("BULK REFUND STEP: REFUND API RESPONSE FAILURE  FOR REERENCE ID :" + refundKey.trim()
						+ input.getId().trim() + "\n with  ");
				log.info("BULK REFUND STEP: EXCEPTION CAUGHT IN REFUND API \n" + e.getErrorMessage());
			}
		} catch (Exception e) {
			output.setStatus("FAILED");
			output.setMessage(e.getMessage().replaceAll("\\r|\\n", ""));
			if (log.isInfoEnabled()) {
				log.info("BULK REFUND STEP: REFUND API RESPONSE FAILURRE  FOR REERENCE ID :" + refundKey.trim()
						+ input.getId().trim() + "\n");
				log.info("BULK REFUND STEP: EXCEPTION CAUGHT IN REFUND API \n" + e.getMessage() + "\n"
						+ ExceptionUtils.getFullStackTrace(e));
			}
		}
		output.setComments(input.getComments());
		output.setAmount(input.getAmount());
		output.setId(input.getId());
		output.setFeeReversalCode(input.getFeeReversalCode());
		return output;
	}

	public BulkRefundBatchProcessor(String refundKey, String merchantId) {
		super();
		this.refundKey = refundKey;
		this.merchantId = merchantId;
	}

}
