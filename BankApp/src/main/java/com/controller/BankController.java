package com.controller;


import com.constants.AppConstant;
import com.request.CreateProfileRequest;
import com.response.CreateProfileResponse;
import com.response.GetProfileResponse;
import com.response.ServiceResponse;
import com.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service/profiles")
public class BankController {

    @Autowired
    IProfileService service;

    @PostMapping(value = "/create", produces = AppConstant.JSON)
    public ServiceResponse<CreateProfileResponse> createAccountHolderProfile(@Valid @RequestBody CreateProfileRequest request){

        CreateProfileResponse res = service.createProfile(request);
        ServiceResponse<CreateProfileResponse> response = new ServiceResponse<CreateProfileResponse>();
        response.setResponse(res);
        return response;
    }

    @GetMapping(value = "/get/{profileId}", produces = AppConstant.JSON)
    public ServiceResponse<GetProfileResponse> createAccountHolderProfile(@PathVariable int profileId){

        GetProfileResponse res = service.getProfile(profileId);
        ServiceResponse<GetProfileResponse> response = new ServiceResponse<GetProfileResponse>();
        response.setResponse(res);
        return response;
    }
}
