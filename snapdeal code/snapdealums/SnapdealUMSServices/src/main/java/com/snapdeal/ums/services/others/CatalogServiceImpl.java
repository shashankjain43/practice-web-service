package com.snapdeal.ums.services.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.model.common.CatalogType;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetCatalogResponse;
import com.snapdeal.catalog.base.model.GetCatalogTypeByCatalogIdRequest;
import com.snapdeal.catalog.base.model.GetPageURLForCatalogRequest;
import com.snapdeal.catalog.base.model.GetPageURLForCatalogResponse;
import com.snapdeal.catalog.base.model.GetServiceDealByIdRequest;
import com.snapdeal.catalog.base.sro.CitySRO;
import com.snapdeal.catalog.client.service.ICatalogClientService;
import com.snapdeal.core.sro.productoffer.ProductOfferSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;
import com.snapdeal.serviceDeal.client.service.IServiceDealClientService;
import com.snapdeal.ums.cache.CitiesCache;

@Service("umsCatalogService")
public class CatalogServiceImpl implements ICatalogService {


    private static final Logger   LOG                              = LoggerFactory.getLogger(CatalogServiceImpl.class);

    @Autowired
    private ICatalogClientService catalogClientService;

    @Autowired
    private IServiceDealClientService  dealClientService;

    
    @Override
    public GetCatalogResponse getCatalog(GetCatalogByIdRequest request) {
        GetCatalogResponse response = null;
        try {
            response = catalogClientService.getCatalogContentById(request);
        } catch (TransportException e) {
            LOG.error("Errro fetching catalog from catalog server");
        }
        return response;
    }


    @Override
    public GetPageURLForCatalogResponse getPageURLForCatalog(GetPageURLForCatalogRequest request) {
        // ID validation
        long id = request.getCatalogId();
        int cityId = request.getCityId();
        GetPageURLForCatalogResponse response = new GetPageURLForCatalogResponse();
        if (id>0) {
            response.setResponseCode(GetPageURLForCatalogResponse.RESPONSE_FAILURE);
            return response;
        }

        // Type Validation.
        String type = getCatalogTypeByCatalogId(id);
        if (StringUtils.isEmpty(type)) {
            LOG.error("Invalid catalog ID in the request");
            response.setResponseCode(GetCatalogResponse.RESPONSE_FAILURE);
            return response;
        }

        // type = product
        if (type.equalsIgnoreCase(CatalogType.PRODUCT.type())) {
            ProductOfferSRO productOfferDTO = (ProductOfferSRO) getCatalog(new GetCatalogByIdRequest(id)).getCatalogSRO();
            if (productOfferDTO != null) {
                response.setPageURL(productOfferDTO.getCalculatedPageUrl(null));
                response.setResponseCode(GetPageURLForCatalogResponse.RESPONSE_SUCCESS);
                return response;
            }
        }
        // type = service
        else if (type.equalsIgnoreCase(CatalogType.DEAL.type()) && cityId>0) {
            CitySRO city = CacheManager.getInstance().getCache(CitiesCache.class).getCityById(cityId);
            ServiceDealSRO deal = null;
            try {
                deal = dealClientService.getDealById(new GetServiceDealByIdRequest(id)).getServiceDealSRO();
            } catch (Exception ex) {
                LOG.error("Invalid catalog ID in the request");
            }
            if (deal != null & city != null) {
                response.setPageURL(deal.getCalculatedPageUrl(city.getPageUrl()));
                response.setResponseCode(GetPageURLForCatalogResponse.RESPONSE_SUCCESS);
                return response;
            }
        }

        // type = junk
        LOG.error("Incorrect catalog type or missing zone id. Type : {} and zoneId : {}", type, cityId);
        response.setResponseCode(GetPageURLForCatalogResponse.RESPONSE_FAILURE);
        return response;
    }


    private String getCatalogTypeByCatalogId(long id) {
        String type = "";
        try {
            GetCatalogTypeByCatalogIdRequest req = new GetCatalogTypeByCatalogIdRequest(id);
            type = catalogClientService.getCatalogTypeById(req).getCatalogTypeSRO().getCatalogType();
        } catch (TransportException e) {
            LOG.error("Error while getting catalog type", e);
            type = null;
        }
        return type;
    }

 }
