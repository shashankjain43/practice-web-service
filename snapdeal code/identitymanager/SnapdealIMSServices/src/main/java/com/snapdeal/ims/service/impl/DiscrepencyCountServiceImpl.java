package com.snapdeal.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.dashboard.dbmapper.IDiscrepencyCountDao;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.GetDiscrepencyCountServiceRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesServiceRequest;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.service.IDiscrepencyCountService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
@Transactional("transactionManager")
public class DiscrepencyCountServiceImpl implements IDiscrepencyCountService {
	@Autowired
	IDiscrepencyCountDao discrepencyCountDao;

	@Override
	@Logged
	@Timed
	@Marked
	public GetDiscrepencyCountResponse getDiscrepencyCountForUsers(
			GetDiscrepencyCountServiceRequest request) {
		GetDiscrepencyCountResponse response = new GetDiscrepencyCountResponse();
		response.setSdNullCount(discrepencyCountDao
				.getSdDiscrepencyCount(request));
		response.setFcNullCount(discrepencyCountDao
				.getFcDiscrepencyCount(request));
		response.setSdFcNullcount(discrepencyCountDao
				.getSdFcDiscrepencyCount(request));
		return response;
	}

	@Override
	@Logged
	@Timed
	@Marked
	public DiscrepencyCasesEmailResponse getAllEmailForDiscrepencyCases(
			GetEmailForDiscrepencyCasesServiceRequest request) {
		DiscrepencyCasesEmailResponse response = new DiscrepencyCasesEmailResponse();
		List<String> emailIds = null;
		switch (request.getDCase()) {
		case FC_NULL_COUNTER:
			emailIds = discrepencyCountDao.getFcDiscrepencyEmailList(request);
			break;
		case SD_NULL_COUNTER:
			emailIds = discrepencyCountDao.getSdDiscrepencyEmailList(request);
			break;
		case SD_FC_NULL_COUNTER:
			emailIds = discrepencyCountDao.getSdFcDiscrepencyEmailList(request);
			break;
		default:
			throw new IMSServiceException(
					IMSRequestExceptionCodes.DCASE_IS_INVALID.errCode(),
					IMSRequestExceptionCodes.DCASE_IS_INVALID.errMsg());
		}
		response.setEmailIds(emailIds);
		return response;
	}
}
