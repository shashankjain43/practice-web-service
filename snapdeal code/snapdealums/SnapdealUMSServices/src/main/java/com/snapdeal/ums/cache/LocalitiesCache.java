/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jul 7, 2011
 *  @author rahul
 */
package com.snapdeal.ums.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.base.ds.TernarySearchTree;
import com.snapdeal.core.dto.LocalityDTO;
import com.snapdeal.ums.core.entity.Locality;

@Cache(name = "umsLocalitiesCache")
public class LocalitiesCache {

    private Map<Integer, TernarySearchTree<LocalityDTO>> localitySearchMap = new HashMap<Integer, TernarySearchTree<LocalityDTO>>();
    private Map<Integer, Locality>                       idToLocalityMap   = new HashMap<Integer, Locality>();
    private Map<String, Locality>                        nameToLocalityMap = new HashMap<String, Locality>();

    public void addLocality(Locality locality) {

        TernarySearchTree<LocalityDTO> searchMap = localitySearchMap.get(locality.getCityId());

        if (searchMap == null) {
            searchMap = new TernarySearchTree<LocalityDTO>();
            localitySearchMap.put(locality.getCityId(), searchMap);
        }
        searchMap.put(locality.getName().toLowerCase(), new LocalityDTO(locality.getId(), locality.getName()));
        idToLocalityMap.put(locality.getId(), locality);
        nameToLocalityMap.put(locality.getName(), locality);
    }

    public List<LocalityDTO> getLocalities(Integer cityId, String searchString) {
        TernarySearchTree<LocalityDTO> searchTree = localitySearchMap.get(cityId);
        if (searchTree == null) {
            return Collections.emptyList();
        }
        return searchTree.matchPrefix(searchString.toLowerCase());
    }

    public Locality getLocalityById(Integer localityId) {
        return idToLocalityMap.get(localityId);
    }

    public Locality getLocalityByName(String localityName) {
        return nameToLocalityMap.get(localityName);
    }
}
