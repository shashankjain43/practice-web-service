
package com.snapdeal.ums.server.services;

import java.util.List;
import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;
import com.snapdeal.ums.core.entity.EmailServiceProvider;

public interface IBulkEmailServiceInternal {

    void updateFilterCityMapping(ESPFilterCityMapping filterCityMapping);

    List<ESPFilterCityMapping> getFiltersForCity(int cityId, int espId);
    
    ESPFilterCityMapping getESPFilerCityMappingById(int id);
    
    List<ESPProfileField> getProfileFieldsForESP(int espId);

    public List<EmailBulkEspCityMapping> getAllEmailBulkEspCityMapping();

    public EmailBulkEspCityMapping getBulkEspCityMappingForCity(Integer city);

    public EmailBulkEspCityMapping update(EmailBulkEspCityMapping emailBulkEspCityMapping);
    
    List<Object[]> getResultsMau(int start, int number, String city);

    List<Object> getResultsBounce(int start, int number, String city);

    public List<EmailServiceProvider> getAllESPs();
}
