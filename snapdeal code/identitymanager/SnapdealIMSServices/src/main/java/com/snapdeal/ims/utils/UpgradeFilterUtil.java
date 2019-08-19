package com.snapdeal.ims.utils;

import java.util.ArrayList;
import java.util.List;

import com.snapdeal.ims.entity.FilterEntity;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;



public class UpgradeFilterUtil {

	public static FilterEntity getFilterEntity(
			ArrayList<String> upgradeChannelList,
			ArrayList<String> upgradeSourceList) {
		FilterEntity filterEntity = new FilterEntity();

		if (upgradeChannelList != null) {
			ArrayList<UpgradeChannel> channel = new ArrayList<UpgradeChannel>();
			for (String s : upgradeChannelList) {
				if (UpgradeChannel.forName(s) != null) {
					channel.add(UpgradeChannel.forName(s));
				}
			}
			filterEntity.setUpgradeChannel(channel);
		}
		if (upgradeSourceList != null) {
			ArrayList<UpgradeSource> source = new ArrayList<UpgradeSource>();
			for (String s : upgradeSourceList) {
				if (UpgradeSource.forName(s) != null) {
					source.add(UpgradeSource.forName(s));
				}
			}
			filterEntity.setUpgradeSource(source);
		}
		return filterEntity;
	}
}
