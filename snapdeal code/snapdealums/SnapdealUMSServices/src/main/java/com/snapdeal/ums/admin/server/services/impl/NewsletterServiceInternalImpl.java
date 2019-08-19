/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 17, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.server.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.admin.dao.newsletter.INewsletterDao;
import com.snapdeal.ums.admin.server.services.INewsletterServiceInternal;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;

@Transactional
@Service("newsletterServiceInternal")
public class NewsletterServiceInternalImpl implements INewsletterServiceInternal {
    private INewsletterDao newsletterDao;

    @Autowired
    public void setNewsletterDao(INewsletterDao newsletterDao) {
        this.newsletterDao = newsletterDao;
    }

    @Override
    public void persist(Newsletter newsletter) {
        this.newsletterDao.persist(newsletter);
    }
    
    @Override
    public void persist(NewsletterEspMapping newsletterESPMapping)
    {
    	this.newsletterDao.persist(newsletterESPMapping);
    }

    @Override
    public Newsletter update(Newsletter newsletter) {
        return this.newsletterDao.update(newsletter);
    }

    @Override
    public Newsletter getNewsletterDetails(String cityId, Date date) {
        return this.newsletterDao.getNewsletterDetails(cityId,date);
    }

    @Override
    public List<Newsletter> getNewsletters(String cityId, Date date) {
        return this.newsletterDao.getNewsletters(cityId,date);
    }
    
    @Override
    public List<Newsletter> getNewsletters(String cityId, Date date, String state) {
        return this.newsletterDao.getNewsletters(cityId,date,state);
    }

    @Override
    public Newsletter getNewsletterById(Integer id) {
        return this.newsletterDao.getNewsletterById(id);
    }

    @Override
    public List<Newsletter> getNewsletterDetails(Date date) {
       return this.newsletterDao.getNewsletterDetails(date);
    }
    
    @Override
    public List<Newsletter> getNewsletterDetails(Date date, String state) {
       return this.newsletterDao.getNewsletterDetails(date, state);
    }

    @Override
    public Newsletter getNewsletterByMsgId(String msgId) {
        return this.newsletterDao.getNewsletterByMsgId(msgId);
    }

	@Override
	public List<NewsletterEspMapping> getNewsletterESPMapping(
			int newsletterId, int espId) 
    {
		return this.newsletterDao.getNewsletterEspMapping(newsletterId, espId);
	}

	
    @Override
    public List<Integer> getFailedCitiesForNewsletter(Integer newsletterId) {
        return this.newsletterDao.getFailedCitiesForNewsletter(newsletterId);
    }

    @Override
    public NewsletterEspMapping getNewsletterESPMappingForCity(int newsletterId, int espId, int cityId, String filterType) {
        return this.newsletterDao.getNewsletterEspMappingForCity(newsletterId, espId, cityId, filterType);
    }

    @Override
    public void setNewsletterEspMappingFailed(Integer newsletterId, Integer cityId) {
        this.newsletterDao.setNewsletterEspMappingFailed(newsletterId,cityId);
    }

    @Override
    public List<EmailServiceProvider> getAllESP() {
        return newsletterDao.getAllESPs();
    }

}
