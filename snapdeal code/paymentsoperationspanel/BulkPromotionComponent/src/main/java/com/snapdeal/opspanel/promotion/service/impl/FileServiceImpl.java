/**
 * 
 */
package com.snapdeal.opspanel.promotion.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.dao.FileMetaDao;
import com.snapdeal.opspanel.promotion.enums.FileState;
import com.snapdeal.opspanel.promotion.model.CampaignKeyResponse;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.service.FileService;
import com.snapdeal.opspanel.promotion.utils.AmazonS3Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author saheb
 *
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

	@Autowired
	AmazonS3Utils amazonUtils;

	@Autowired
	FileMetaDao fileMetaDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapdeal.opspanel.promotion.service.FileService#getFileList(java.lang
	 * .String)
	 */
	@Override
	public List<FileMetaEntity> getFileList(String emailId) throws OpsPanelException {
		try {
			List<String> fileList = amazonUtils.listFiles(emailId);
			List<FileMetaEntity> fileMetaList = new ArrayList<FileMetaEntity>();
			for (String file : fileList) {
				// fileMetaList.add( fileMetaDao.getAllFileMeta( file ) );
				FileMetaEntity entity = new FileMetaEntity();
				// entity.setFilename(file);
				fileMetaList.add(entity);
			}
			return fileMetaList;
		} catch (Exception e) {
			log.info(" Exception while getting file meta entity list " + e);
			throw new OpsPanelException("MT-5010", e.getMessage(), "AmazonS3");
		}
	}

	@Override
	public String getPresignDownloadLink(String fileName, String emailId) throws Exception {
		URL url = amazonUtils.getDownloadUrl(emailId, fileName);
		return url.toString();
	}

	@Override
	public CampaignKeyResponse getCampaignKeyDetails(String campaignKey) throws Exception {

		CampaignKeyResponse campaignKeyResponse = new CampaignKeyResponse();

		FileMetaEntity fileMetaEntity = fileMetaDao.getCampaignKey(campaignKey);

		if (fileMetaEntity == null) {
			campaignKeyResponse.setStatus(FileState.GO_GREEN_FOR_CAMPAIGN_KEY.toString());
		} else {
			campaignKeyResponse.setCorporateAccount(fileMetaEntity.getCorporateAccountName());
			campaignKeyResponse.setFileName(fileMetaEntity.getFileName());
			campaignKeyResponse.setUploadTime(fileMetaEntity.getUploadTime());
			campaignKeyResponse.setStatus(FileState.CAMPAIGN_KEY_ALREADY_USED.toString());

		}

		return campaignKeyResponse;

	}

}
