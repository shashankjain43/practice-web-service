/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 21, 2010
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
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.core.dto.ZoneDTO;

@Cache(name = "umsZonesCache")
public class ZonesCache {

    private Map<String, ZoneSRO>        pageUrlToZone       = new HashMap<String, ZoneSRO>();
    private Map<Integer, ZoneSRO>       idToZone            = new HashMap<Integer, ZoneSRO>();
    private Map<String, ZoneSRO>        nameToZone          = new HashMap<String, ZoneSRO>();
    private List<ZoneSRO>               zones               = new ArrayList<ZoneSRO>();
    private Map<Integer, List<ZoneSRO>> cityIdToZoneList    = new HashMap<Integer, List<ZoneSRO>>();
    private List<ZoneSRO>               defaultCityZones    = new ArrayList<ZoneSRO>();
    private List<ZoneDTO>               defaultCityZonesDTO = new ArrayList<ZoneDTO>();
    private Map<String, String>         cityPageUrlForZone  = new HashMap<String, String>();

    public List<ZoneDTO> getDefaultCityZonesDTO() {
        return defaultCityZonesDTO;
    }

	public void addZone(ZoneSRO zone) {
		if (zone == null) {
			return;
		}
		//Only if the zone does not already exist, should it be added!
		if (!idToZone.containsKey(zone.getId())) {
			pageUrlToZone.put(
					zone.getPageUrl().toLowerCase().replaceAll("[^a-z]", ""),
					zone);
			idToZone.put(zone.getId(), zone);
			nameToZone.put(zone.getName(), zone);
			cityPageUrlForZone.put(zone.getPageUrl(), zone.getCity()
					.getPageUrl());
			zones.add(zone);
		}
	}

    public ZoneSRO getZoneById(int zoneId) {
        return idToZone.get(zoneId);
    }

    public ZoneSRO getZoneByPageUrl(String pageUrl) {
        if (StringUtils.isEmpty(pageUrl)) {
            return null;
        }
        return pageUrlToZone.get(pageUrl.toLowerCase().replaceAll("[^a-z]", ""));
    }

    public String getCityPageUrlForZone(String zonePageUrl) {
        if (StringUtils.isEmpty(zonePageUrl)) {
            return null;
        }
        return cityPageUrlForZone.get(zonePageUrl.toLowerCase());
    }

    public List<ZoneSRO> getZones() {
        return zones;
    }

    public List<ZoneSRO> getDefaultCityZones() {
        return defaultCityZones;
    }

    public List<ZoneSRO> getZonesByCityId(Integer id) {
        return cityIdToZoneList.get(id);
    }

    public List<Integer> getZoneIdsByCityId(Integer id) {
        List<ZoneSRO> zones = cityIdToZoneList.get(id);
        if (zones == null) {
            return Collections.emptyList();
        } else {
            List<Integer> zoneIds = new ArrayList<Integer>();
            for (ZoneSRO zone : zones) {
                zoneIds.add(zone.getId());
            }
            return zoneIds;
        }
    }

    public void freeze() {
        Collections.sort(zones, new Comparator<ZoneSRO>() {
            public int compare(ZoneSRO o1, ZoneSRO o2) {
                if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() == o2.getPriority()) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return -1;
                }
            }
        });
//        zones = Collections.unmodifiableList(zones);
        for (ZoneSRO zone : zones) {

            //map city id to the zone list
            if (cityIdToZoneList.containsKey(zone.getCity().getId())) {
                cityIdToZoneList.get(zone.getCity().getId()).add(zone);
            } else {
                List<ZoneSRO> zoneList = new ArrayList<ZoneSRO>();
                zoneList.add(zone);
                cityIdToZoneList.put(zone.getCity().getId(), zoneList);

                ZoneSRO cityZone = new ZoneSRO();
                cityZone.setId(zone.getId());
                cityZone.setName(zone.getCity().getName());
                cityZone.setPageUrl(zone.getCity().getPageUrl());
                cityZone.setPriority(zone.getCity().getPriority());
                //add this first zone to default city zones
                defaultCityZones.add(cityZone);

                ZoneDTO zoneDTO = new ZoneDTO();
                if (zone.getName() != null && zone.getId() != null && zone.getPageUrl() != null) {
                    zoneDTO.setName(zone.getCity().getName());
                    zoneDTO.setZoneId(zone.getId());
                    zoneDTO.setPageUrl(zone.getCity().getPageUrl());
                    zoneDTO.setVisible(zone.getCity().getVisible());
                }
                defaultCityZonesDTO.add(zoneDTO);
            }

            // map city url to the zone 
            String cityPageUrl = zone.getCity().getPageUrl().toLowerCase().replaceAll("[^a-z]", "");
            if (!pageUrlToZone.containsKey(cityPageUrl)) {
                pageUrlToZone.put(cityPageUrl, zone);
            }
        }
        Collections.sort(defaultCityZones, new Comparator<ZoneSRO>() {
            public int compare(ZoneSRO o1, ZoneSRO o2) {
                if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() == o2.getPriority()) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return -1;
                }
            }
        });
    }

}
