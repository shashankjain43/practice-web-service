package com.snapdeal.ums.services.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.catalog.base.model.GetPopularProductOfferGroupListByCategoryPageURLRequest;
import com.snapdeal.catalog.base.model.GetProductOfferByIdRequest;
import com.snapdeal.catalog.base.model.GetProductOfferGroupListResponse;
import com.snapdeal.catalog.base.model.GetProductOfferResponse;
import com.snapdeal.product.client.service.IProductClientService;

@Service("productCatalogService")
public class ProductCatalogServiceImpl implements IProductCatalogService {

    private static final Logger           LOG                        = LoggerFactory.getLogger(ProductCatalogServiceImpl.class);

    @Autowired
    private IProductClientService         productClientService;
    
    @Deprecated
    @Override
    public GetProductOfferGroupListResponse getPopularProductOfferGroupsByCategoryPageUrl(GetPopularProductOfferGroupListByCategoryPageURLRequest request) {
        GetProductOfferGroupListResponse response = new GetProductOfferGroupListResponse();
        response.setSuccessful(false);
        try {
            response = productClientService.getPopularProductOfferGroupListByCategoryPageUrl(request);
        } catch (TransportException e) {
            LOG.error("Unable to execute web service call getPopularProductOfferGroupListByCategoryPageUrl: ", e);
        }
        return response;
    }


    @Deprecated
    @Override
    public GetProductOfferResponse getProductOfferById(GetProductOfferByIdRequest request) {
        GetProductOfferResponse response = new GetProductOfferResponse();
        response.setSuccessful(false);
        try {
            response = productClientService.getProductOfferById(request);
        } catch (TransportException e) {
            LOG.error("Unable to execute web service call getProductOfferById: ", e);
        }
        return response;
    }

}
