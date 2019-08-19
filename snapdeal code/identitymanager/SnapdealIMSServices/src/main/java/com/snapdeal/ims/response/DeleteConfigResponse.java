package com.snapdeal.ims.response;

import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteConfigResponse {
	private ConfigDetails configDetails;
}
