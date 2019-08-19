package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.EmailTypeDO;
import com.snapdeal.ums.dao.emailType.IEmailTypeDao;
/**
 * Service to map email type with list of email template name
 * @author lovey
 *
 */

@Service
public class EmailTypeTemplateMappingService {

	@Autowired
	private IEmailTypeDao emailTypeDao;

	/**
	 * 
	 * @param emailType
	 * @return list of template name
	 */
    @Transactional
	public List<String> getListOfEmailTemplateName(String emailType) {

		EmailTypeDO emailTypeDO=emailTypeDao.getEmailType(emailType);
		List<EmailTemplate> emailTemplateList=emailTypeDO.getListOfEmailTemplate();
		List<String> listOfTemplateName=new ArrayList<String>();
		for (EmailTemplate e : emailTemplateList) {
			listOfTemplateName.add(e.getName());
		}
		
		return listOfTemplateName;

	}

	
}
