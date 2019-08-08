package com.service;

import com.entity.ProfileDO;
import com.request.CreateProfileRequest;
import com.response.CreateProfileResponse;
import com.response.GetProfileResponse;

public class ProfileServiceImpl implements IProfileService {

    @Override
    public CreateProfileResponse createProfile(CreateProfileRequest request) {

        ProfileDO entity = new ProfileDO();
        return null;
    }

    @Override
    public GetProfileResponse getProfile(int profileId) {
        return null;
    }
}
