
package com.snapdeal.opspanel.promotion.service;

import java.util.List;

import com.snapdeal.opspanel.promotion.model.FileMetaEntity;

public interface FileMetaService {
	
	public  List<FileMetaEntity> getAllFilesMeta() ;
	
	public List<FileMetaEntity> getAllFilesMetaByUser(String userId) ;
	
	public void insertFileMetaEntity(FileMetaEntity entity) ;
	
	public void updateFileMetaStatus(FileMetaEntity entity) ;

	public void updateFileMetaMessage(String fileName, String message);

	public void updateFileMetaTotalSuccessMoney(FileMetaEntity entity);

	public void updateFileMetaSuccessRowsNum(FileMetaEntity entity);

	public void updateFileMetaEntity( FileMetaEntity entity );

	public List<FileMetaEntity> getFileMetaStatus(String userId, String fileName);

	public String getUserIdForFile( String fileName );
	
	public List<FileMetaEntity> getFileMetaStatusForAnyUser(String userId, String fileName);

}
