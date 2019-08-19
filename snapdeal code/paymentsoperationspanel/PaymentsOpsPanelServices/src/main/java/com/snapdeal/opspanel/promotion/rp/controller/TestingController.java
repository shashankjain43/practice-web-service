package com.snapdeal.opspanel.promotion.rp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.promotion.Response.GenericResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("test/")
public class TestingController {

	public @ResponseBody GenericResponse test(){
		
		System.out.println("checking");
		return new GenericResponse();
	}
	
}
