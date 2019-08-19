package com.snapdeal.opspanel.promotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.opspanel.promotion.model.FileMetaEntity;

public interface FileMetaDao {

	public List<FileMetaEntity> getAllFilesMeta();

	public List<FileMetaEntity> getAllFilesMetaByUser(String userId);

	public void insertFileMetaEntity(FileMetaEntity entity);

	public void updateFileMetaStatus(FileMetaEntity entity);

	public void updateFileMetaTotalSuccessMoney(FileMetaEntity entity);

	public void updateFileMetaSuccessRowsNum(FileMetaEntity entity);

	public void updateFileMetaEntity( FileMetaEntity entity );

	public List<FileMetaEntity> getFileMetaStatusUtil(FileMetaEntity entity);

	public List<FileMetaEntity> getFileMetaStatusForAnyUser(FileMetaEntity entity);

	public void updateFileMetaMessage(@Param("fileName") String fileName, @Param("message") String message);

	public String getUserIdForFile(String fileName);

	public FileMetaEntity getCampaignKey(String idempotencyKey);
}
