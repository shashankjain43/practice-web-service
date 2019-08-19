package com.snapdeal.opspanel.userpanel.service;

import java.util.List;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchShopoTransactionRequest;
import com.snapdeal.opspanel.userpanel.response.ShopoTransactionDetails;

public interface ShopoService {

	List<ShopoTransactionDetails> searchShopoTransaction(SearchShopoTransactionRequest request) throws OpsPanelException;

}
