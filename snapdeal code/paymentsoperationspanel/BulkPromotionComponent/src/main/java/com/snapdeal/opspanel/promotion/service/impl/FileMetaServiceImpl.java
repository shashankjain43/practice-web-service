package com.snapdeal.opspanel.promotion.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.promotion.dao.FileMetaDao;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.service.FileMetaService;

@Service("fileMetaService")
public class FileMetaServiceImpl implements FileMetaService{

	@Autowired
	FileMetaDao fileMetaDao;

	public  List<FileMetaEntity> getAllFilesMeta() {
		return fileMetaDao.getAllFilesMeta();
	}

	public List<FileMetaEntity> getAllFilesMetaByUser(String userId) {
		return fileMetaDao.getAllFilesMetaByUser(userId);
	}

	public void insertFileMetaEntity(FileMetaEntity entity) {
		fileMetaDao.insertFileMetaEntity(entity);
	}

	public void updateFileMetaStatus(FileMetaEntity entity) {
		fileMetaDao.updateFileMetaStatus(entity);
	}

	public void updateFileMetaTotalSuccessMoney( FileMetaEntity entity) {
		fileMetaDao.updateFileMetaTotalSuccessMoney( entity );
	}

	public void updateFileMetaSuccessRowsNum( FileMetaEntity entity ) {
		fileMetaDao.updateFileMetaSuccessRowsNum( entity );
	}

	@Override
	public void updateFileMetaMessage(String fileName, String message) {
		fileMetaDao.updateFileMetaMessage(fileName, message);
	}

	public List<FileMetaEntity> getFileMetaStatus(String userId, String fileName) {

		FileMetaEntity entity = new FileMetaEntity();
		entity.setUserId(userId);
		entity.setFileName(fileName);

		return fileMetaDao.getFileMetaStatusUtil(entity);
	}

	@Override
	public void updateFileMetaEntity(FileMetaEntity entity) {
		fileMetaDao.updateFileMetaEntity( entity );
	}

	public List<FileMetaEntity> getFileMetaStatusForAnyUser(String userId, String fileName) {

		FileMetaEntity entity = new FileMetaEntity();
		entity.setUserId(userId);
		entity.setFileName(fileName);

		return fileMetaDao.getFileMetaStatusForAnyUser(entity);
	}
	
	public String getUserIdForFile( String fileName ) {
		return fileMetaDao.getUserIdForFile(fileName);
	}
}
