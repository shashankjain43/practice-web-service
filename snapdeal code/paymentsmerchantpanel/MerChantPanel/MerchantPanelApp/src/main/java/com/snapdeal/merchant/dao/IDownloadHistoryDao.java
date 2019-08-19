package com.snapdeal.merchant.dao;

import java.util.List;

import com.snapdeal.merchant.dao.entity.DownloadEntity;

public interface IDownloadHistoryDao {
	
	public List<DownloadEntity> getUserDownloadInfoList(DownloadEntity entity);
	
	public DownloadEntity getUserDownloadInfo(DownloadEntity entity);
	
	public int insertDownloadInfoResult(DownloadEntity entity);
	
	public int updateDownloadInfoStatus(DownloadEntity entity);
	
	public int updateDownloadInfoVisited(DownloadEntity entity);

}
