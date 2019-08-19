package com.snapdeal.ims.response;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllConfigsResponse {

	private List<ConfigDetails> configList;
}
