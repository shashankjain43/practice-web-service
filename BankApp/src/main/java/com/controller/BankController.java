package com.controller;


import com.request.CreateProfileRequest;
import com.response.CreateProfileResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/library")
public class BankController {

    @PostMapping(produces = "application/JSON")
    public CreateProfileResponse createAccountHolderProfile(CreateProfileRequest request){

    }
}
