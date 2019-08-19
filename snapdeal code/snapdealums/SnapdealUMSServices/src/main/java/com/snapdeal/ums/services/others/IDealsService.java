package com.snapdeal.ums.services.others;

import com.snapdeal.catalog.base.model.GetServiceDealByIdRequest;
import com.snapdeal.catalog.base.model.GetServiceDealResponse;

public interface IDealsService {

    GetServiceDealResponse getDealById(GetServiceDealByIdRequest request);
}
