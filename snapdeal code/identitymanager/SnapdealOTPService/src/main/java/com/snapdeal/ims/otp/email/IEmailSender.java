package com.snapdeal.ims.otp.email;

import org.springframework.stereotype.Service;

import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.otp.entity.UserOTPEntity;


@Service
public interface IEmailSender {

	public void send(UserOTPEntity otp,Merchant merchant,String name);
}
