package com.snapdeal.ums.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
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
import com.snapdeal.ums.server.services.IBulkEmailService;

@Controller
@RequestMapping("/service/ums/admin/bulkemail/")
public class BulkEmailServiceController {

    @Autowired
    private IBulkEmailService bulkEmailService;

    @RequestMapping(value = "update", produces = "application/sd-service")
    @ResponseBody
    public UpdateResponse update(@RequestBody UpdateRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        UpdateResponse response = bulkEmailService.update(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateFilterCityMapping", produces = "application/sd-service")
    @ResponseBody
    public UpdateFilterCityMappingResponse updateFilterCityMapping(@RequestBody UpdateFilterCityMappingRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        UpdateFilterCityMappingResponse response = bulkEmailService.updateFilterCityMapping(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFiltersForCity", produces = "application/sd-service")
    @ResponseBody
    public GetFiltersForCityResponse getFiltersForCity(@RequestBody GetFiltersForCityRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetFiltersForCityResponse response = bulkEmailService.getFiltersForCity(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getProfileFieldsForESP", produces = "application/sd-service")
    @ResponseBody
    public GetProfileFieldsForESPResponse getProfileFieldsForESP(@RequestBody GetProfileFieldsForESPRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetProfileFieldsForESPResponse response = bulkEmailService.getProfileFieldsForESP(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAllEmailBulkEspCityMapping", produces = "application/sd-service")
    @ResponseBody
    public GetAllEmailBulkEspCityMappingResponse getAllEmailBulkEspCityMapping(@RequestBody GetAllEmailBulkEspCityMappingRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllEmailBulkEspCityMappingResponse response = bulkEmailService.getAllEmailBulkEspCityMapping(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getBulkEspCityMappingForCity", produces = "application/sd-service")
    @ResponseBody
    public GetBulkEspCityMappingForCityResponse getBulkEspCityMappingForCity(@RequestBody GetBulkEspCityMappingForCityRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetBulkEspCityMappingForCityResponse response = bulkEmailService.getBulkEspCityMappingForCity(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getResultsMau", produces = "application/sd-service")
    @ResponseBody
    public GetResultsMauResponse getResultsMau(@RequestBody GetResultsMauRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetResultsMauResponse response = bulkEmailService.getResultsMau(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getResultsBounce", produces = "application/sd-service")
    @ResponseBody
    public GetResultsBounceResponse getResultsBounce(@RequestBody GetResultsBounceRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetResultsBounceResponse response = bulkEmailService.getResultsBounce(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
