package com.snapdeal.ums.server.services.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.core.dto.S3FileDTO;
import com.snapdeal.ums.core.entity.SDCashBackProgramConfig;
import com.snapdeal.ums.core.utils.AmazonS3Utils;
import com.snapdeal.ums.dao.ISDCashBackProgramConfigDao;
import com.snapdeal.ums.services.SubsidiaryCashBulkUpload.SubsidiaryCashBulkCreditUploadRequest;
import com.snapdeal.ums.services.SubsidiaryCashBulkUpload.SubsidiarySDCashBulkCreditService;
import com.sun.xml.bind.v2.TODO;

/**
 * 
 * @version 1.0, 15-Apr-2015
 * @author jain.shashank@snapdeal.com
 * 
 */

@Service
@Transactional
public class SubsidierySDCashBackService {

	private static final Logger LOG = LoggerFactory
			.getLogger(SubsidierySDCashBackService.class);

	@Autowired
	private ISDCashBackProgramConfigDao subPromotionDao;

	@Autowired
	private SubsidiarySDCashBulkCreditService subsidiarySDCashBulkCreditService;

	@Autowired
	private AmazonS3Utils s3Utils;

	public void processFiles() {

		// Fetch sdCash back program configurations
		List<SDCashBackProgramConfig> configRecords = subPromotionDao
				.getAllSDCashBackProgramConfig();
		if(configRecords == null || configRecords.isEmpty()){
			LOG.info("No config fetched from db...Skipping this iteration");
			return;
		}

		for (SDCashBackProgramConfig config : configRecords) {
			if(config != null){
				if (config.isEnabled()) {
					try {
						processAllFilesOfConfig(config);
					} catch (Exception e) {
						LOG.error(
								"Something went wrong in processing this configuration...moving to next config: ",
								e);
					}
				} else {
					LOG.info("This config is disabled hence skipping...");
				}
			}
			
		}
	}

	private void processAllFilesOfConfig(SDCashBackProgramConfig config) {
		String accessId = config.getS3AccessId();
		String secretKey = config.getS3SecretKey();
		String sourceBucket = config.getS3BucketName();
		String sourceDir = config.getS3SourceDirName();
		S3FileDTO fileDTO = s3Utils.downloadFirstFileFromS3BucketDir(accessId,
				secretKey, sourceBucket, sourceDir);
		if (fileDTO == null) {
			LOG.info("No more file left in this config...moving to next config");
			config.setLastProcessed(DateUtils.getCurrentTime());
			subPromotionDao.updateConfig(config);
			return;
		} else {
			// coming here means we have a valid file for processing
			byte[] fileContent = fileDTO.getContent();
			if (fileContent != null) {
				LOG.info("Input file is Non-Empty...sending for processing");
				SubsidiaryCashBulkCreditUploadRequest request = new SubsidiaryCashBulkCreditUploadRequest();
				request.setFileName(fileDTO.getFileName());
				request.setFileContent(fileContent);
				request.setActivityType(config.getActivityId());
				request.setDefaultAmount(config.getDefaultSDcash());
				request.setFileContent(fileContent);
				request.setCreditSDCashEmailTemplateName(config.getSdcashEmailTemplate());
				request.setAccountCreationEmailTemplateName(config.getCreateUserEmailTemplate());
				request.setSource(config.getSubsidiaryName());
				SDCashBulkCreditUploadResponse response = null;
				try {
					response = subsidiarySDCashBulkCreditService
							.processSDCashUploadRequest(request,
									config.isUseFileAmount());
					if (response.isSuccessful()) {
						// move file to 'processed' folder as successfully
						// processed
						s3Utils.copyFile(accessId, secretKey, sourceBucket,
								sourceDir + fileDTO.getFileName(),
								sourceBucket,
								config.getS3SuccessDirName() + fileDTO.getFileName());
						s3Utils.deleteFile(accessId, secretKey, sourceBucket,
								sourceDir + fileDTO.getFileName());
					} else {
						// move file to 'failed' folder as failed during
						// processing
						s3Utils.copyFile(accessId, secretKey, sourceBucket,
								sourceDir + fileDTO.getFileName(),
								sourceBucket, config.getS3FailDirName() + fileDTO.getFileName());
						s3Utils.deleteFile(accessId, secretKey, sourceBucket,
								sourceDir + fileDTO.getFileName());
					}
				} catch (Exception e) {
					// move file to 'failed' folder as failed during processing
					s3Utils.copyFile(accessId, secretKey, sourceBucket,
							sourceDir + fileDTO.getFileName(), sourceBucket,
							config.getS3FailDirName()+"exception/" + fileDTO.getFileName());
					s3Utils.deleteFile(accessId, secretKey, sourceBucket,
							sourceDir + fileDTO.getFileName());
				}
			} else {
				// file content is empty...move file to 'processed' folder
				s3Utils.copyFile(accessId, secretKey, sourceBucket, sourceDir
						+ fileDTO.getFileName(), sourceBucket, config.getS3SuccessDirName()
						+ fileDTO.getFileName());
				s3Utils.deleteFile(accessId, secretKey, sourceBucket, sourceDir
						+ fileDTO.getFileName());
			}
			// recursive call to process more file until we are left with no
			// file
			// from S3
			processAllFilesOfConfig(config);
		}
	}

}
