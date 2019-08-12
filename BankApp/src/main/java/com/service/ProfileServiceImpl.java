package com.service;

import com.dao.ProfileDao;
import com.entity.ProfileDO;
import com.exception.ServiceException;
import com.request.CreateProfileRequest;
import com.response.CreateProfileResponse;
import com.response.GetProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public CreateProfileResponse createProfile(CreateProfileRequest request) {
        boolean existsByMobile = profileDao.existsByMobile(request.getMobile());
        boolean existsByEmail = profileDao.existsByEmail(request.getEmail());
        if(existsByMobile || existsByEmail){
            throw new ServiceException("ER-1001", "User already exists!");
        }
        ProfileDO entity = new ProfileDO();
        entity.setName(request.getName());
        entity.setMobile(request.getMobile());
        entity.setEmail(request.getEmail());
        profileDao.save(entity);

        CreateProfileResponse response = new CreateProfileResponse();
        response.setSuccess(true);
        return response;
    }

    @Override
    public GetProfileResponse getProfile(int profileId) {
        GetProfileResponse response = new GetProfileResponse();
        response.setProfile(profileDao.findById(profileId).orElseGet(null));
        return response;
    }
}
