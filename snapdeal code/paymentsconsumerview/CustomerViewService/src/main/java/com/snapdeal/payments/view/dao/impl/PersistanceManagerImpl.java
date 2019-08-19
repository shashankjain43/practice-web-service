package com.snapdeal.payments.view.dao.impl;


import lombok.experimental.Delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.dao.IActionDetailsDao;
import com.snapdeal.payments.view.dao.ILoadCashDao;
import com.snapdeal.payments.view.dao.IMerchantTransactionDetailsDao;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.dao.IRequestViewDao;

@Service("persistanceManager")
public class PersistanceManagerImpl implements IPersistanceManager{

	@Delegate
	@Autowired
	private IMerchantTransactionDetailsDao merchantDetailsDao ;
	
	@Delegate
	@Autowired
	private IRequestViewDao requestViewDao;
	
	@Delegate
	@Autowired
	private IActionDetailsDao actionDetailsDao;
	
	@Delegate
	@Autowired
	private ILoadCashDao laodViewDao;
	
}
