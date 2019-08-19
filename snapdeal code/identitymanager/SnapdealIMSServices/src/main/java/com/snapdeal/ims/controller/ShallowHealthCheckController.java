package com.snapdeal.ims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.constants.ServiceCommonConstants;

@RestController
public class ShallowHealthCheckController {

	@RequestMapping(value = "/health", method = {RequestMethod.HEAD,
			RequestMethod.GET})
	public String doShallowPing() {
		return ServiceCommonConstants.RESPONSE200;
	}

}
