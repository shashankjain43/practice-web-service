package com.snapdeal.ums.dao.user.sdwallet;

import java.util.List;

import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;

public interface ISDCashFileUploadHistoryDAO {
	public SDCashFileUploadHistoryDO save(SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO);
	public SDCashFileUploadHistoryDO findByFileNameAndHash(String fileName ,String hash);
	public List<SDCashFileUploadHistoryDO> getFileUploadHistoryByFileName(String fileName);
}
