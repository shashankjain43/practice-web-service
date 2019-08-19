/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 20, 2010
 *  @author rahul
 */
package com.snapdeal.ums.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.base.ds.TernarySearchTree;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.core.dto.CitySearchDTO;
import com.snapdeal.catalog.base.sro.CitySRO;

@Cache(name = "umsCitiesCache")
public class CitiesCache {

    private List<CitySRO>                    cities                = new ArrayList<CitySRO>();
    private Map<Integer, CitySRO>            idToCity              = new HashMap<Integer, CitySRO>();
    private Map<String, CitySRO>             nameToCity            = new HashMap<String, CitySRO>();
    private Map<String, CitySRO>             pageUrlToCity         = new HashMap<String, CitySRO>();
    private Map<CitySRO, String>             cityToDefaultLocality = new HashMap<CitySRO, String>();
    private TernarySearchTree<CitySearchDTO> citySearchTree        = new TernarySearchTree<CitySearchDTO>();

	public void addCity(CitySRO city) {
		if(city==null){
			return;
		}
		//Only if the city does not already exist, should it be added!
		if (!idToCity.containsKey(city.getId())) {
			cities.add(city);
			idToCity.put(city.getId(), city);
			nameToCity.put(city.getName(), city);
			pageUrlToCity.put(
					city.getPageUrl().toLowerCase().replaceAll("[^a-z~]", ""),
					city);
			cityToDefaultLocality.put(city, city.getDefaultLocation());
		}
	}

    public TernarySearchTree<CitySearchDTO> getCitySearchTree() {
        return citySearchTree;
    }

    public void setCitySearchTree(TernarySearchTree<CitySearchDTO> citySearchTree) {
        this.citySearchTree = citySearchTree;
    }

    public List<CitySRO> getCities() {
        return cities;
    }

    public CitySRO getCityById(Integer id) {
        return idToCity.get(id);
    }

    public CitySRO getCityByName(String name) {
        return nameToCity.get(name);
    }

    public CitySRO getCityByPageUrl(String pageUrl) {
        if (StringUtils.isEmpty(pageUrl)) {
            return null;
        }
        return pageUrlToCity.get(pageUrl.toLowerCase().replaceAll("[^a-z~]", ""));
    }

    public void freeze() {
        Collections.sort(cities, new Comparator<CitySRO>() {
            public int compare(CitySRO o1, CitySRO o2) {
                if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() == o2.getPriority()) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return -1;
                }
            }
        });
//        cities = Collections.unmodifiableList(cities);
    }

    public List<CitySearchDTO> searchCities(String searchString) {
        if (StringUtils.isEmpty(searchString)) {
            return new ArrayList<CitySearchDTO>();
        }
        return citySearchTree.matchPrefix(searchString.toLowerCase());
    }

}
