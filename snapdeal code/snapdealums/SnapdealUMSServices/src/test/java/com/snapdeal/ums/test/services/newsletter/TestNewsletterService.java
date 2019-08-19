/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 07-may-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.test.services.newsletter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.admin.dao.newsletter.INewsletterDao;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersResponse;
import com.snapdeal.ums.admin.server.services.INewsletterService;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.Newsletter.State;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@ContextConfiguration(locations = { "/hsql-applicationContext-test.xml" })
public class TestNewsletterService extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private INewsletterService   umsNewsletterService;

    @Autowired
    private INewsletterDao       newsletterDao;

//    @Autowired
//    private IUMSConvertorService mockUMSConvertorService;

    private Newsletter           mockNewsletter1;

    private Newsletter           mockNewsletter2;

    @BeforeMethod
    public void setUp() {
        mockNewsletter1 = new Newsletter();
        mockNewsletter1.setCityIds("city1");
        mockNewsletter1.setContent("mockcontent1");
        mockNewsletter1.setCreated(DateUtils.getCurrentDate());
        mockNewsletter1.setEnabled(true);
        mockNewsletter1.setFilterTypes("mockfilter1");
        mockNewsletter1.setLyrisId("mocklyricsId");
        mockNewsletter1.setMessageId("mockmessageid");
        mockNewsletter1.setNumEmailSent(2);
        mockNewsletter1.setScheduleDate(DateUtils.getCurrentDate());
        mockNewsletter1.setState(State.CANCELLED.name());
        mockNewsletter1.setSubject("mocksubject");
        mockNewsletter1.setUpdated(DateUtils.getCurrentDate());
        newsletterDao.persist(mockNewsletter1);

        mockNewsletter2 = new Newsletter();
        mockNewsletter2.setCityIds("city2");
        mockNewsletter2.setContent("mockcontent2");
        mockNewsletter2.setCreated(DateUtils.getCurrentDate());
        mockNewsletter2.setEnabled(true);
        mockNewsletter2.setFilterTypes("mockfilter2");
        mockNewsletter2.setLyrisId("mocklyricsId");
        mockNewsletter2.setMessageId("mockmessageid2");
        mockNewsletter2.setNumEmailSent(3);
        mockNewsletter2.setScheduleDate(DateUtils.getCurrentDate());
        mockNewsletter2.setState(State.CANCELLED.name());
        mockNewsletter2.setSubject("mocksubject2");
        mockNewsletter2.setUpdated(DateUtils.getCurrentDate());
        newsletterDao.persist(mockNewsletter2);

    }

    @Test
    public void getNewsletterByMsgIdTest() {
        GetNewsletterByMsgIdRequest request = new GetNewsletterByMsgIdRequest();
        request.setMsgId("mockmessageid");
        GetNewsletterByMsgIdResponse response = umsNewsletterService.getNewsletterByMsgId(request);
        NewsletterSRO sro = response.getGetNewsletterByMsgId();
        AssertJUnit.assertNotNull(sro);
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sro.getMessageId());

        GetNewsletterByMsgIdRequest request1 = new GetNewsletterByMsgIdRequest();
        request1.setMsgId(null);
        GetNewsletterByMsgIdResponse response1 = umsNewsletterService.getNewsletterByMsgId(request1);
        NewsletterSRO sro1 = response1.getGetNewsletterByMsgId();
        AssertJUnit.assertNull(sro1);
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }

    @Test
    public void getNewsletterDetailsTest() {
        GetNewsletterDetailsRequest request = new GetNewsletterDetailsRequest();
        request.setCityId("city1");
        request.setDate(DateUtils.getCurrentDate());
        GetNewsletterDetailsResponse response = umsNewsletterService.getNewsletterDetails(request);
        NewsletterSRO sro = response.getGetNewsletterDetails();
        AssertJUnit.assertNotNull(sro);
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sro.getMessageId());

        GetNewsletterDetailsRequest nullRequest = new GetNewsletterDetailsRequest();
        nullRequest.setCityId(null);
        nullRequest.setDate(null);
        GetNewsletterDetailsResponse response1 = umsNewsletterService.getNewsletterDetails(nullRequest);
        NewsletterSRO sro1 = response1.getGetNewsletterDetails();
        AssertJUnit.assertNull(sro1);
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }

    @Test
    public void getNewsletterDetail2Test() {
        GetNewsletterDetailsRequest2 request = new GetNewsletterDetailsRequest2();
        request.setDate(DateUtils.getCurrentDate());
        GetNewsletterDetailsResponse2 response = umsNewsletterService.getNewsletterDetails(request);
        List<NewsletterSRO> sroList = response.getGetNewsletterDetails();
        AssertJUnit.assertEquals(2, sroList.size());
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sroList.get(0).getMessageId());

        GetNewsletterDetailsRequest2 nullRequest = new GetNewsletterDetailsRequest2();
        nullRequest.setDate(null);
        GetNewsletterDetailsResponse2 response1 = umsNewsletterService.getNewsletterDetails(nullRequest);
        AssertJUnit.assertEquals(0, response1.getGetNewsletterDetails().size());
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }

    @Test
    public void getNewsletterDetail3Test() {
        GetNewsletterDetailsRequest3 request = new GetNewsletterDetailsRequest3();
        request.setDate(DateUtils.getCurrentDate());
        request.setState(State.CANCELLED.name());
        GetNewsletterDetailsResponse3 response = umsNewsletterService.getNewsletterDetails(request);
        List<NewsletterSRO> sroList = response.getGetNewsletterDetails();
        AssertJUnit.assertEquals(2, sroList.size());
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sroList.get(0).getMessageId());

        GetNewsletterDetailsRequest3 nullRequest = new GetNewsletterDetailsRequest3();
        nullRequest.setDate(null);
        GetNewsletterDetailsResponse3 response1 = umsNewsletterService.getNewsletterDetails(nullRequest);
        AssertJUnit.assertEquals(0, response1.getGetNewsletterDetails().size());
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }

    @Test
    public void getNewslettersTest() {
        GetNewslettersRequest request = new GetNewslettersRequest();
        request.setCityId("city1");
        request.setDate(DateUtils.getCurrentDate());
        GetNewslettersResponse response = umsNewsletterService.getNewsletters(request);
        List<NewsletterSRO> sroList = response.getGetNewsletters();
        AssertJUnit.assertEquals(1, sroList.size());
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sroList.get(0).getMessageId());

        GetNewslettersRequest nullRequest = new GetNewslettersRequest();
        nullRequest.setCityId(null);
        nullRequest.setDate(null);
        GetNewslettersResponse response1 = umsNewsletterService.getNewsletters(nullRequest);
        AssertJUnit.assertEquals(0, response1.getGetNewsletters().size());
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }

    @Test
    public void getNewsletters1Test() {
        GetNewslettersRequest2 request = new GetNewslettersRequest2();
        request.setCityId("city1");
        request.setDate(DateUtils.getCurrentDate());
        request.setState(State.CANCELLED.name());
        GetNewslettersResponse response = umsNewsletterService.getNewsletters(request);
        List<NewsletterSRO> sroList = response.getGetNewsletters();
        AssertJUnit.assertEquals(1, sroList.size());
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sroList.get(0).getMessageId());

        GetNewslettersRequest2 nullRequest = new GetNewslettersRequest2();
        nullRequest.setCityId(null);
        nullRequest.setDate(null);
        GetNewslettersResponse response1 = umsNewsletterService.getNewsletters(nullRequest);
        AssertJUnit.assertEquals(0, response1.getGetNewsletters().size());
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }
    
    @Test
    public void getNewsletterByIdTest() {
        GetNewsletterByMsgIdRequest request0 = new GetNewsletterByMsgIdRequest();
        request0.setMsgId("mockmessageid");
        GetNewsletterByMsgIdResponse response0 = umsNewsletterService.getNewsletterByMsgId(request0);
        GetNewsletterByIdRequest request = new GetNewsletterByIdRequest();
        request.setId(response0.getGetNewsletterByMsgId().getId());
        
        GetNewsletterByIdResponse response = umsNewsletterService.getNewsletterById(request);
        NewsletterSRO sro = response.getGetNewsletterById();
        AssertJUnit.assertNotNull(sro);
        AssertJUnit.assertEquals(response.isSuccessful(), true);
        AssertJUnit.assertEquals("mockmessageid", sro.getMessageId());

        GetNewsletterByIdRequest nullRequest = new GetNewsletterByIdRequest();
        nullRequest.setId(0);
        GetNewsletterByIdResponse response1 = umsNewsletterService.getNewsletterById(nullRequest);
        AssertJUnit.assertNull(response1.getGetNewsletterById());
        AssertJUnit.assertEquals(response1.isSuccessful(), false);
    }
    
}