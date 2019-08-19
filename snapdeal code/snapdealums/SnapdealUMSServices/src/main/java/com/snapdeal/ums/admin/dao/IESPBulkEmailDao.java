/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 15, 2010
 *  @author rahul
 */
package com.snapdeal.ums.admin.dao;

import java.util.List;

import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailServiceProvider;

public interface IESPBulkEmailDao {

    public void updateFilterCityMapping(ESPFilterCityMapping filterCityMapping);

    public List<ESPFilterCityMapping> getFiltersForCity(int cityId, int espId);

    public List<ESPProfileField> getProfileFieldsForESP(int espId);

    public List<Object[]> getResultsMau(int start, int number, String city);

    public List<Object> getResultsBounce(int start, int number, String city);

    public List<EmailServiceProvider> getAllESPs();

    public ESPFilterCityMapping getESPFilerCityMappingById(int id);

}