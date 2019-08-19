package com.snapdeal.ums.services.others;

import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetCatalogResponse;
import com.snapdeal.catalog.base.model.GetPageURLForCatalogRequest;
import com.snapdeal.catalog.base.model.GetPageURLForCatalogResponse;

public interface ICatalogService {
    
    public GetCatalogResponse getCatalog(GetCatalogByIdRequest request);
    
    public GetPageURLForCatalogResponse getPageURLForCatalog(GetPageURLForCatalogRequest request);

}
