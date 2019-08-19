package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import lombok.Data;

import com.snapdeal.payments.fps.entity.Device;

@Data
public class DeviceDetailsInfo {
	
	Device deviceInfo;
	
	String error;

}
