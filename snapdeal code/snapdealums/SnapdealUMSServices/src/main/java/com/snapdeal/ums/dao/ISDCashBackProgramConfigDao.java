package com.snapdeal.ums.dao;

import java.util.List;

import com.snapdeal.ums.core.entity.SDCashBackProgramConfig;

public interface ISDCashBackProgramConfigDao {
	
	public List<SDCashBackProgramConfig> getAllSDCashBackProgramConfig();
	
	public int addSDCashBackProgramConfig(SDCashBackProgramConfig config);
	
	public SDCashBackProgramConfig getSDCashBackProgramConfigById(int configId);
	
	public int deleteSDCashBackProgramConfigById(int configId);

	public void disableSDCashBackProgramConfigById(int configId);
	
	public void enableSDCashBackProgramConfigById(int configId);

	public SDCashBackProgramConfig updateConfig(SDCashBackProgramConfig config);

}
