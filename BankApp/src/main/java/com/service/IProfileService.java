package com.service;

import com.exception.ServiceException;
import com.request.CreateProfileRequest;
import com.response.CreateProfileResponse;
import com.response.GetProfileResponse;

public interface IProfileService {

    public CreateProfileResponse createProfile(CreateProfileRequest request) throws ServiceException;
    public GetProfileResponse getProfile(int profileId) throws ServiceException;
}
