package com.snapdeal.merchant.persistence;

import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.dao.entity.DownloadEntity;
import com.snapdeal.merchant.dao.entity.FilterEntity;
import com.snapdeal.merchant.persistence.exception.PersistenceException;

@Transactional(rollbackFor=PersistenceException.class)
public interface IFileInfoManager {
	
	public Integer createFileInfo(FilterEntity fEntity,DownloadEntity dEntity) throws PersistenceException;
	
	public Integer createFileInfo(BulkRefundEntity rEntity) throws PersistenceException;

}
