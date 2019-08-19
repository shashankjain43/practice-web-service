package com.snapdeal.merchant.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class HealthCheckController {
	
	@RequestMapping(method=RequestMethod.GET,value="/v1/healthcheck")
	public @ResponseBody String ping() {
		return "HIT ME!!";
	}

}
