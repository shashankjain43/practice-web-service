package com.response;

import com.entity.ProfileDO;
import lombok.Getter;
import lombok.Setter;

public class GetProfileResponse extends BaseResponse {

    @Getter
    @Setter
    private ProfileDO profile;
}
