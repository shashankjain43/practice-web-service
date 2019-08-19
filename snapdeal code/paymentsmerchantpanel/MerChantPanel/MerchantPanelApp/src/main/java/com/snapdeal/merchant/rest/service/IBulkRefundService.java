package com.snapdeal.merchant.rest.service;

import java.io.InputStream;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundRequest;
import com.snapdeal.merchant.response.MerchantBulkRefundResponse;

@Transactional(isolation = Isolation.REPEATABLE_READ , rollbackFor = MerchantException.class)
public interface IBulkRefundService {

	public MerchantBulkRefundResponse bulkRefund(MerchantBulkRefundRequest request, InputStream ioStream) throws MerchantException;

}
