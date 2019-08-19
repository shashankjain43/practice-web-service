package com.snapdeal.ums.admin.server.services;

import java.util.Date;
import java.util.List;

import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;


public interface INewsletterServiceInternal {

    public void persist(Newsletter newsletter);

    public void persist(NewsletterEspMapping newsletterESPMapping);

    public Newsletter update(Newsletter newsletter);

    public Newsletter getNewsletterDetails(String cityId, Date date);

    public List<Newsletter> getNewsletters(String cityId, Date date);

    public List<Newsletter> getNewsletters(String cityId, Date date, String state);

    public Newsletter getNewsletterById(Integer id);

    public List<Newsletter> getNewsletterDetails(Date date);

    public List<Newsletter> getNewsletterDetails(Date date, String state);

    public Newsletter getNewsletterByMsgId(String msgId);

    public List<NewsletterEspMapping> getNewsletterESPMapping(int newsletterId, int espId);

    public NewsletterEspMapping getNewsletterESPMappingForCity(int newsletterId, int espId, int cityId, String filterType);

    List<Integer> getFailedCitiesForNewsletter(Integer newsletterId);

    void setNewsletterEspMappingFailed(Integer newsletterId, Integer cityId);

    public List<EmailServiceProvider> getAllESP();

}
