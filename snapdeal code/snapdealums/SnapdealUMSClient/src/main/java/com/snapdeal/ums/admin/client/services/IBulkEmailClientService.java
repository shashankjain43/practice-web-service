
package com.snapdeal.ums.admin.client.services;

import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateResponse;

public interface IBulkEmailClientService {

  
    public UpdateResponse update(UpdateRequest request);

    public UpdateFilterCityMappingResponse updateFilterCityMapping(UpdateFilterCityMappingRequest request);

    public GetFiltersForCityResponse getFiltersForCity(GetFiltersForCityRequest request);

    public GetProfileFieldsForESPResponse getProfileFieldsForESP(GetProfileFieldsForESPRequest request);

    public GetAllEmailBulkEspCityMappingResponse getAllEmailBulkEspCityMapping(GetAllEmailBulkEspCityMappingRequest request);

    public GetBulkEspCityMappingForCityResponse getBulkEspCityMappingForCity(GetBulkEspCityMappingForCityRequest request);

    public GetResultsMauResponse getResultsMau(GetResultsMauRequest request);

    public GetResultsBounceResponse getResultsBounce(GetResultsBounceRequest request);

}
