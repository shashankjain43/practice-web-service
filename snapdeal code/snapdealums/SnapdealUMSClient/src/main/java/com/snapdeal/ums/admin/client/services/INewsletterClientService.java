
package com.snapdeal.ums.admin.client.services;

import com.snapdeal.ums.admin.ext.newsletter.GetAllESPRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetAllESPResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest2;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse2;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedRequest;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedResponse;
import com.snapdeal.ums.admin.ext.newsletter.UpdateRequest;
import com.snapdeal.ums.admin.ext.newsletter.UpdateResponse;

public interface INewsletterClientService {


    public UpdateResponse update(UpdateRequest request);

    public PersistResponse persist(PersistRequest request);

    public PersistResponse2 persist(PersistRequest2 request);

    @Deprecated
    public GetNewsletterDetailsResponse getNewsletterDetails(GetNewsletterDetailsRequest request);
    @Deprecated
    public GetNewsletterDetailsResponse2 getNewsletterDetails(GetNewsletterDetailsRequest2 request);
    @Deprecated
    public GetNewsletterDetailsResponse3 getNewsletterDetails(GetNewsletterDetailsRequest3 request);
    @Deprecated
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest request);
    @Deprecated
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest2 request);
    @Deprecated
    public GetNewsletterByIdResponse getNewsletterById(GetNewsletterByIdRequest request);

    public GetNewsletterByMsgIdResponse getNewsletterByMsgId(GetNewsletterByMsgIdRequest request);
    @Deprecated
    public GetNewsletterESPMappingResponse getNewsletterESPMapping(GetNewsletterESPMappingRequest request);
    @Deprecated
    public GetNewsletterESPMappingForCityResponse getNewsletterESPMappingForCity(GetNewsletterESPMappingForCityRequest request);
    @Deprecated
    public GetFailedCitiesForNewsletterResponse getFailedCitiesForNewsletter(GetFailedCitiesForNewsletterRequest request);
    @Deprecated
    public SetNewsletterEspMappingFailedResponse setNewsletterEspMappingFailed(SetNewsletterEspMappingFailedRequest request);

    public GetAllESPResponse getAllESP(GetAllESPRequest request);


}
