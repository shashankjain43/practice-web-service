package com.snapdeal.payments.view.cache.impl;

import java.util.List;
import java.util.ListIterator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.annotations.Cache;
import com.snapdeal.payments.view.cache.AbstractCache;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.request.Client;
import com.snapdeal.payments.view.dao.IClientDetailsDao;

@Slf4j
@Cache(name = PaymentsViewConstants.CLIENT_CACHE)
@Component
public class ClientCache extends AbstractCache<String, Client> {
	
	@Autowired
	private IClientDetailsDao clientDao;

	public String getCacheName() {
		return PaymentsViewConstants.CLIENT_CACHE;
	}

	@Override
	public void loadCache() {
		final List<Client> clients = clientDao.getAllClients();
		log.info("loading Client Cache");
		for (ListIterator<Client> it = clients.listIterator(); it.hasNext();) {
			Client client = (Client) it.next();
			if (client != null) {
				this.put(client.getClientName(), client);
			}
		}
	}

}
