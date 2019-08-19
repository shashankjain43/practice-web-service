
package com.snapdeal.ums.admin.server.services;

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

public interface INewsletterService {


    public UpdateResponse update(UpdateRequest request);

    public PersistResponse persist(PersistRequest request);

    public PersistResponse2 persist(PersistRequest2 request);

    public GetNewsletterDetailsResponse getNewsletterDetails(GetNewsletterDetailsRequest request);

    public GetNewsletterDetailsResponse2 getNewsletterDetails(GetNewsletterDetailsRequest2 request);

    public GetNewsletterDetailsResponse3 getNewsletterDetails(GetNewsletterDetailsRequest3 request);

    public GetNewslettersResponse getNewsletters(GetNewslettersRequest request);

    public GetNewslettersResponse getNewsletters(GetNewslettersRequest2 request);

    public GetNewsletterByIdResponse getNewsletterById(GetNewsletterByIdRequest request);

    public GetNewsletterByMsgIdResponse getNewsletterByMsgId(GetNewsletterByMsgIdRequest request);

    public GetNewsletterESPMappingResponse getNewsletterESPMapping(GetNewsletterESPMappingRequest request);

    public GetNewsletterESPMappingForCityResponse getNewsletterESPMappingForCity(GetNewsletterESPMappingForCityRequest request);

    public SetNewsletterEspMappingFailedResponse setNewsletterEspMappingFailed(SetNewsletterEspMappingFailedRequest request);

    public GetFailedCitiesForNewsletterResponse getFailedCitiesForNewsletter(GetFailedCitiesForNewsletterRequest request);

    public GetAllESPResponse getAllESP(GetAllESPRequest request);

}
