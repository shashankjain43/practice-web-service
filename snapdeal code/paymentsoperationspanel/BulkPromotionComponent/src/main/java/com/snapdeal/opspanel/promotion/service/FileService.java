package com.snapdeal.opspanel.promotion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.model.CampaignKeyResponse;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;

@Service
public interface FileService {

   public List<FileMetaEntity> getFileList( String emailId ) throws OpsPanelException;

   public String getPresignDownloadLink(String fileName,String emailId)throws Exception;

   public CampaignKeyResponse getCampaignKeyDetails(String campaignKey) throws Exception;

}
