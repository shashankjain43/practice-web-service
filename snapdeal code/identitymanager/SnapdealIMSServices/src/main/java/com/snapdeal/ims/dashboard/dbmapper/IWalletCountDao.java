package com.snapdeal.ims.dashboard.dbmapper;

import java.util.List;

import com.snapdeal.ims.entity.FilterEntity;



public interface IWalletCountDao {
	public int retrieveWalletStatus(FilterEntity filterEntity);
	public List<String> retrieveEmailListForUpgradeFilter(FilterEntity filterEntity);
}

