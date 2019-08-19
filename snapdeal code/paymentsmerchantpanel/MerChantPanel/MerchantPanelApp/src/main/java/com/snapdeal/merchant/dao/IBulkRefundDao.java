package com.snapdeal.merchant.dao;

import java.util.List;

import com.snapdeal.merchant.dao.entity.BulkRefundEntity;

public interface IBulkRefundDao {

	
    public List<BulkRefundEntity> getBulkRefundInfoList(BulkRefundEntity entity);
	
	public BulkRefundEntity getBulkRefundInfo(BulkRefundEntity entity);
	
	public int insertBulkRefundInfo(BulkRefundEntity entity);
	
	public void updateBulkRefundInfoStatus(BulkRefundEntity updateEntity);
	
	public int updateBulkRefundInfoVisited(BulkRefundEntity entity);
	
}