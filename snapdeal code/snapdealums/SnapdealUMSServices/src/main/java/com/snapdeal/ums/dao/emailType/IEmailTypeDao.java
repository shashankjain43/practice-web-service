package com.snapdeal.ums.dao.emailType;

import java.util.List;

import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.EmailTypeDO;

public interface IEmailTypeDao {

	
	//public EmailTypeDO  getEmailType(Integer emailTypeId);



	public EmailTypeDO getEmailType(String emailType);
	
	public EmailTypeDO getEmailType(Integer emailTypeId);
	
	public EmailTypeDO save(EmailTypeDO emailTypeDO);
}
