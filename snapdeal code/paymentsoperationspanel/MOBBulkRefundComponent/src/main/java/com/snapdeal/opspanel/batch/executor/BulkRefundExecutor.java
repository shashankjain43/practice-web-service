package com.snapdeal.opspanel.batch.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.opspanel.Amazons3.request.DownloadRefundFileRequest;
import com.snapdeal.opspanel.Amazons3.service.BulkRefundService;
import com.snapdeal.opspanel.Amazons3.utils.OpsPanelCommonsUtils;
import com.snapdeal.opspanel.bulk.request.BulkRefundTaskRequest;
import com.snapdeal.opspanel.bulk.service.BulkService;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Component
@Slf4j
public class BulkRefundExecutor implements TaskExecutor<BulkRefundTaskRequest> {

	@Autowired
	BulkService bulkService;

	@Autowired
	BulkRefundService amazonService;

	@Autowired
	BulkRefundService bulkRefundService;

	@Value("${snapdeal.sdMoneyDashboard.amazon.localDownloadPath}")
	private String localDownloadPath;

	public ExecutorResponse execute(BulkRefundTaskRequest request) {

		ExecutorResponse response = null;
		final String stepIdentifier = "BULKREFUNDSTEP- ";
		try {

			DownloadRefundFileRequest downloadRefundReq = new DownloadRefundFileRequest();
			downloadRefundReq.setFileName(request.getFileName());
			downloadRefundReq.setInputFile(true);
			downloadRefundReq.setEmailId(request.getEmailId());

			amazonService.localdownloadFile(downloadRefundReq);
			if (log.isInfoEnabled()) {
				log.info("BULK REFUND CHECK : file downloaded from amazon\n" + "\n");
			}
			String outputFileName = OpsPanelCommonsUtils.getOutputFilePathForCSV(request.getFileName());
			bulkService.submitjob(localDownloadPath + request.getFileName(), localDownloadPath + outputFileName,
					request.getEmailId(), request.getFileName(), request.getRefundKey(), request.getMerchantId());
			if (log.isInfoEnabled()) {
				log.info("BULK REFUND CHECK : Job submitted to the service" + "\n");
			}
			response = new ExecutorResponse();

			response.setStatus(Status.SUCCESS);
		} catch (Exception e) {

			response.setStatus(Status.RETRY);
			if (log.isInfoEnabled()) {
				log.info(stepIdentifier + "Could not downlaod file from amazon s3 server " + e.getMessage() + "\n");
			}
		}
		return response;

	}

}
