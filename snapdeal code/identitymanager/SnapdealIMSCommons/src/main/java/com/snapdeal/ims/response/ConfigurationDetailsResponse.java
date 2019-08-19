package com.snapdeal.ims.response;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ConfigurationDetailsResponse extends AbstractResponse{

	private static final long serialVersionUID = -7638503547164120392L;
	
	private Map<String,String> configurationMap;
}
