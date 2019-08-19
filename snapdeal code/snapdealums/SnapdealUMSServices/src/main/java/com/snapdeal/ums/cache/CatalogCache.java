/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 24-Jul-2011
 *  @author rahul
 */
package com.snapdeal.ums.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.ums.core.utils.Constants;
/**
 * Unused
 * @author naveen
 *
 */
@Cache(name = "umsCatalogCache")
public class CatalogCache {

    private Map<Integer, String> catalogIdToTypeMap = new HashMap<Integer, String>(Constants.INITIAL_CAPACITY_CATALOG_MAP);
    private Date                 lastUpdated        = null;

    public void addCatalog(Object[] catalog) {
        catalogIdToTypeMap.put((Integer) catalog[0], (String) catalog[1]);
    }

    public String getCatalogTypeById(Integer catalogId) {
        return catalogIdToTypeMap.get(catalogId);
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
