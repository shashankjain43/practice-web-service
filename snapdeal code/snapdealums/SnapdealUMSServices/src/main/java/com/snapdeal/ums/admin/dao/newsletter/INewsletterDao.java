/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 17, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao.newsletter;

import java.util.Date;
import java.util.List;

import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;


public interface INewsletterDao {
    public void persist(Newsletter newsletter);

    public void persist(NewsletterEspMapping newsletterESPMapping);

    public Newsletter update(Newsletter newsletter);

    public Newsletter getNewsletterDetails(String cityId, Date date);

    public List<Newsletter> getNewsletters(String cityId, Date date);
    
    public List<Newsletter> getNewsletters(String cityId,Date date, String state);

    public Newsletter getNewsletterById(Integer id);

    public List<Newsletter> getNewsletterDetails(Date date);
    
    public List<Newsletter> getNewsletterDetails(Date date, String state);

    public Newsletter getNewsletterByMsgId(String msgId);

    public List<NewsletterEspMapping> getNewsletterEspMapping(int newsletterId, int espId);

    public NewsletterEspMapping getNewsletterEspMappingForCity(int newsletterId, int espId, int cityId, String filterType);

    public List<Integer> getFailedCitiesForNewsletter(int newsletterId);

    public void setNewsletterEspMappingFailed(int newsletterId, int cityId);

    public NewsletterEspMapping update(NewsletterEspMapping newsletterEspMapping);

    public List<EmailServiceProvider> getAllESPs();
}
