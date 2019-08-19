package com.snapdeal.ims.response;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetConfigByKeyResponse {

	private List<ConfigDetails> configList;
}
