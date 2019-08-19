package com.snapdeal.merchant.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.IDownloadHistoryDao;
import com.snapdeal.merchant.dao.IFilterDao;
import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.dao.entity.DownloadEntity;
import com.snapdeal.merchant.dao.entity.FilterEntity;
import com.snapdeal.merchant.persistence.IFileInfoManager;
import com.snapdeal.merchant.persistence.exception.PersistenceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DBFileInfoManagerImpl implements IFileInfoManager {
	
	@Autowired
	private IDownloadHistoryDao historyDao;

	@Autowired
	private IFilterDao filterDao;
	
	@Autowired
	private IBulkRefundDao refundDao;
	
	@Override
	public Integer createFileInfo(FilterEntity fEntity,DownloadEntity dEntity) throws PersistenceException {


		try {
			
			int count = filterDao.insertFilterInfo(fEntity);
			
			dEntity.setFilterId(fEntity.getId());

			historyDao.insertDownloadInfoResult(dEntity);
			return dEntity.getId();
			
		}catch(Exception jpe) {
			log.error("could not persist info in db {}",jpe.getMessage(),jpe);
			throw new PersistenceException(jpe.getMessage(), jpe);
		}
			
	}

	@Override
	public Integer createFileInfo(BulkRefundEntity rEntity) throws PersistenceException {
		
try {
			
			int refundId = refundDao.insertBulkRefundInfo(rEntity);
			return  rEntity.getId();
		}catch(Exception jpe) {
			log.error("could not persist info in db {}",jpe.getMessage(),jpe);
			throw new PersistenceException(jpe.getMessage(), jpe);
		}

			
	}

}
