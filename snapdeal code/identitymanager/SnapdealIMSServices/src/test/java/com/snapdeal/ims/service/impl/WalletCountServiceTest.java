package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.dashboard.dbmapper.IWalletCountDao;
import com.snapdeal.ims.entity.FilterEntity;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.response.WalletCountResponse;



@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class WalletCountServiceTest {

	@Autowired
	WalletCountServiceImpl walletCountService;
	
	@Autowired
	IWalletCountDao walletCountDao;
	
	@Test
	public void getCount(){
		FilterEntity filter = new FilterEntity();
		ArrayList<UpgradeChannel> upgradeChannel = new ArrayList<UpgradeChannel>();
		ArrayList<UpgradeSource> upgradeSource = new ArrayList<UpgradeSource>();
		filter.setUpgradeChannel(upgradeChannel);
		filter.setUpgradeSource(upgradeSource);
		WalletCountResponse count = walletCountService.retrieveTotalWalletCount(filter);
		Assert.assertTrue(count.getUpgradeStatusTotal()==11);
		Assert.assertTrue(count.getUpgradeStatusSnapdealCount()==5);
		Assert.assertTrue(count.getUpgradeStatusFreechargeCount()==3);
		Assert.assertTrue(count.getUpgradeStatusOneCheckCount()==3);
		Assert.assertTrue(count.getUpgradeStatusSuccessTotal()==5);
		Assert.assertTrue(count.getUpgradeStatusFailedTotal()==6);
		Assert.assertTrue(count.getUpgradeStatusSnapdealSuccessCount()==3);
		Assert.assertTrue(count.getUpgradeStatusSnapdealFailedCount()==2);
		Assert.assertTrue(count.getUpgradeStatusFreechargeSuccessCount()==2);
		Assert.assertTrue(count.getUpgradeStatusFreechargeFailedCount()==1);
		Assert.assertTrue(count.getUpgradeStatusOneCheckSuccessCount()==0);
		Assert.assertTrue(count.getUpgradeStatusOneCheckFailedCount()==3);
		
	}
	
	@Test
	public void getEmailList(){
		FilterEntity filter = new FilterEntity();
		ArrayList<UpgradeChannel> upgradeChannel = new ArrayList<UpgradeChannel>();
		ArrayList<UpgradeSource> upgradeSource = new ArrayList<UpgradeSource>();
		filter.setUpgradeChannel(upgradeChannel);
		filter.setUpgradeSource(upgradeSource);
		List<String> emailList = walletCountService.getEmailListForUpgradeFilter(filter);
		Assert.assertTrue(emailList.size()==6);
	}
	
}
