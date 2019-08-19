package com.snapdeal.ims.service;

import java.util.List;

import com.snapdeal.ims.entity.FilterEntity;
import com.snapdeal.ims.response.WalletCountResponse;



public interface IWalletCountService {
	public WalletCountResponse retrieveTotalWalletCount(FilterEntity filterEntity);
	public List<String> getEmailListForUpgradeFilter(FilterEntity filterEntity);
}
