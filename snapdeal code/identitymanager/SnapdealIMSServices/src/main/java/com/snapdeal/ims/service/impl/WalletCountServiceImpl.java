package com.snapdeal.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.constants.CreateWalletStatus;
import com.snapdeal.ims.dashboard.dbmapper.IWalletCountDao;
import com.snapdeal.ims.entity.FilterEntity;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.response.WalletCountResponse;
import com.snapdeal.ims.service.IWalletCountService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class WalletCountServiceImpl implements IWalletCountService {

	@Autowired
	IWalletCountDao walletCountDao;
	
	@Logged
	@Timed
	@Marked
	@Override
	public WalletCountResponse retrieveTotalWalletCount(FilterEntity filterEntity){
		WalletCountResponse countEntity = new WalletCountResponse();
		
		//Total Wallet Count
		countEntity.setUpgradeStatusTotal(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setStatus(CreateWalletStatus.CREATED);
		countEntity.setUpgradeStatusSuccessTotal(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setStatus(CreateWalletStatus.FAILED);
		countEntity.setUpgradeStatusFailedTotal(walletCountDao.retrieveWalletStatus(filterEntity));
		
		
		filterEntity.setOriginating_src(Merchant.SNAPDEAL);
		filterEntity.setStatus(null);
		countEntity.setUpgradeStatusSnapdealCount(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setOriginating_src(Merchant.FREECHARGE);
		countEntity.setUpgradeStatusFreechargeCount(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setOriginating_src(Merchant.ONECHECK);
		countEntity.setUpgradeStatusOneCheckCount(walletCountDao.retrieveWalletStatus(filterEntity));
		
		//SD Wallet Count
		filterEntity.setOriginating_src(Merchant.SNAPDEAL);
		filterEntity.setStatus(CreateWalletStatus.CREATED);
		countEntity.setUpgradeStatusSnapdealSuccessCount(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setStatus(CreateWalletStatus.FAILED);
		countEntity.setUpgradeStatusSnapdealFailedCount(walletCountDao.retrieveWalletStatus(filterEntity));
		
		//FC Wallet Count
		filterEntity.setOriginating_src(Merchant.FREECHARGE);
		filterEntity.setStatus(CreateWalletStatus.CREATED);
		countEntity.setUpgradeStatusFreechargeSuccessCount(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setStatus(CreateWalletStatus.FAILED);
		countEntity.setUpgradeStatusFreechargeFailedCount(walletCountDao.retrieveWalletStatus(filterEntity));
		
		//OC Wallet Count
		filterEntity.setOriginating_src(Merchant.ONECHECK);
		filterEntity.setStatus(CreateWalletStatus.CREATED);
		countEntity.setUpgradeStatusOneCheckSuccessCount(walletCountDao.retrieveWalletStatus(filterEntity));
		filterEntity.setStatus(CreateWalletStatus.FAILED);
		countEntity.setUpgradeStatusOneCheckFailedCount(walletCountDao.retrieveWalletStatus(filterEntity));
		return countEntity;
	}
	
	@Logged
	@Timed
	@Marked
	@Override
	public List<String> getEmailListForUpgradeFilter(FilterEntity filterEntity){
		
		List<String>  emailList = walletCountDao.retrieveEmailListForUpgradeFilter(filterEntity);
		return emailList;
	}
	
}
