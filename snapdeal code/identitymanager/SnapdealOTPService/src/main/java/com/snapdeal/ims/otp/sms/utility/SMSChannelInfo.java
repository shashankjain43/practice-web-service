package com.snapdeal.ims.otp.sms.utility;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@Component
@ToString(exclude={"password"})
@Deprecated
public class SMSChannelInfo implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -9062992739681479728L;

	@Value("${smschannels.valuefirst.channelId}")
	private Integer channelId;
	@Value("${smschannels.valuefirst.channelName}")
	private String channelName;
	@Value("${smschannels.valuefirst.senderClassName}")
	private String senderClassName;
	@Value("${smschannels.valuefirst.username}")
	private String username;
	@Value("${smschannels.valuefirst.password}")
	private String password;
	@Value("${smschannels.valuefirst.mask}")
	private String mask;

}
