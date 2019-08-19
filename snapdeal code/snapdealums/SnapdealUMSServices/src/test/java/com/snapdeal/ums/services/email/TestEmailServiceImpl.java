/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Nov-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.services.email;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetCatalogResponse;
import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.catalog.client.service.ICatalogClientService;
import com.snapdeal.core.sro.productoffer.ProductOfferSRO;
import com.snapdeal.locality.client.service.ILocalityClientService;
import com.snapdeal.oms.base.model.GetSuborderByIdRequest;
import com.snapdeal.oms.base.model.GetSuborderResponse;
import com.snapdeal.oms.base.sro.order.SuborderSRO;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.serviceDeal.client.service.IServiceDealClientService;
import com.snapdeal.ums.email.ext.email.SendFeedbackMailRequest;
import com.snapdeal.ums.email.ext.email.SendFeedbackMailResponse;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.impl.EmailServiceImpl;

public class TestEmailServiceImpl {

    @Tested
    private EmailServiceImpl          umsEmailService;

    @Injectable
    private ILocalityClientService    localityClientService;

    @Injectable
    private ICatalogClientService     catalogClientService;

    @Injectable
    private IServiceDealClientService serviceDealClientService;

    @Injectable
    IOrderClientService               orderClientService;

    @Injectable
    private IEmailServiceInternal     emailServiceInternal;

    @BeforeMethod
    public void setUp() {

    }

    @SuppressWarnings("serial")
    @Test
    public void sendFeedbackMailTest() throws TransportException {

        new NonStrictExpectations() {
            {
                GetCatalogResponse catalogResponse = new GetCatalogResponse();
                GetSuborderResponse suborderResponse = new GetSuborderResponse();
                catalogResponse.setCatalogSRO(new ProductOfferSRO());
                suborderResponse.setSuborderSRO(new SuborderSRO() {
                });

                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = suborderResponse;

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = catalogResponse;

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        SendFeedbackMailRequest request = new SendFeedbackMailRequest(1, 1, "testcontentpath", "testcontextpath", false);
        SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertTrue(response.isSuccessful());

    }

    @Test
    public void sendFeedbackMailNullSROsTest() throws TransportException {

        new NonStrictExpectations() {
            {
                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = new GetSuborderResponse();

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = new GetCatalogResponse();

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        SendFeedbackMailRequest request = new SendFeedbackMailRequest(1, 1, "testcontentpath", "testcontextpath", false);
        SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertFalse(response.isSuccessful());

    }

    @Test
    public void sendFeedbackMailNullResponseTest() throws TransportException {

        new NonStrictExpectations() {
            {
                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = null;

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = null;

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        SendFeedbackMailRequest request = new SendFeedbackMailRequest(1, 1, "testcontentpath", "testcontextpath", false);
        SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertFalse(response.isSuccessful());

    }

    @SuppressWarnings("serial")
    @Test
    public void sendFeedbackMailV1Test() throws TransportException {

        new NonStrictExpectations() {
            {
                GetCatalogResponse catalogResponse = new GetCatalogResponse();
                GetSuborderResponse suborderResponse = new GetSuborderResponse();
                catalogResponse.setCatalogSRO(new ProductOfferSRO());
                suborderResponse.setSuborderSRO(new SuborderSRO() {
                });

                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = suborderResponse;

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = catalogResponse;

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest(1, 12345678901234L,
                "testcontentpath", "testcontextpath", false);
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertTrue(response.isSuccessful());

    }

    @Test
    public void sendFeedbackMailV1NullResponseTest() throws TransportException {

        new NonStrictExpectations() {
            {
                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = null;

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = null;

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest(1, 12345678901234L,
                "testcontentpath", "testcontextpath", false);
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertFalse(response.isSuccessful());

    }

    @Test
    public void sendFeedbackMailV1NullSROsTest() throws TransportException {

        new NonStrictExpectations() {
            {
                orderClientService.getSuborderById((GetSuborderByIdRequest) any);
                result = new GetSuborderResponse();

                catalogClientService.getCatalogContentById((GetCatalogByIdRequest) any);
                result = new GetCatalogResponse();

                emailServiceInternal.sendFeedbackMail((SuborderSRO) any, (CatalogSRO) any, anyString, anyString, anyBoolean);
                result = null;
            }
        };

        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest(1, 12345678901234L,
                "testcontentpath", "testcontextpath", false);
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = umsEmailService.sendFeedbackMail(request);
        AssertJUnit.assertFalse(response.isSuccessful());

    }
    
}
