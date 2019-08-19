package com.snapdeal.ums.loyalty.services.impl;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusRequest;
import com.snapdeal.ums.loyalty.LoyaltyUserStatusResponse;
import com.snapdeal.ums.loyalty.SnapBoxActivationRequest;
import com.snapdeal.ums.loyalty.SnapBoxActivationResponse;
import com.snapdeal.ums.loyalty.SnapBoxVerificationActivationRequest;
import com.snapdeal.ums.loyalty.services.ILoyaltyClientService;

@Service("LoyaltyClientService")
public class LoyaltyClientService implements ILoyaltyClientService
{

    // should be something like - http://HOST:PORT
    private String baseURL;

    private static final String SERVICE_URL = "/service/ums/loyalty";

    @Autowired
    private ITransportService transportService;

    @Autowired
    private IUMSClientService umsClientService;

    @Autowired
    private final static Logger LOG = (org.slf4j.LoggerFactory.getLogger(LoyaltyClientService.class));

    @PostConstruct
    public void init()
    {

        transportService.registerService("/service/ums/loyalty/",
            "loyaltyService.");
    }

    @Override
    public void setWebServiceBaseURL(String webServiceBaseURL)
    {

        this.baseURL = webServiceBaseURL;
    }

    private String getWebServiceURL() throws TransportException
    {

        if (baseURL == null) {
            // If baseURL has not been set explicitly, load it from the
            // umsClientService.
            String umsBaseURL = umsClientService.getWebServiceBaseURL();
            baseURL = umsBaseURL;
            if (baseURL == null) {
                // Oh! God! Base URL is not here also! NO OPTION but to log and
                // raise a BASE_URL_MISSING_EXCEPTION
                LOG.error("CANT SEND REQUEST TO UMS SERVER! Neither a base URL has been set at UMS client service, nor has it been set for  "
                    + LoyaltyClientService.class);

                throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
            }
        }
        LOG.info("Will send service request to {}", baseURL);
        return baseURL + SERVICE_URL;
    }

    /**
     * Activates SNAPBOX if the email in the request is eligible. All the fields
     * of the request need to be furnished for proper processing of the request.
     * 
     * @param request
     * @return
     */
    @Override
    public SnapBoxActivationResponse activateSnapBox(
        SnapBoxActivationRequest request)
    {

        SnapBoxActivationResponse response = new SnapBoxActivationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/activateLoyalty";
            response = (SnapBoxActivationResponse) transportService.executeRequest(url, request, null,
                SnapBoxActivationResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));

        }
        return response;
    }

    /**
     * 
     * Gets the status of the user based on his emailID and the loyalty program
     * in the request
     * 
     * @param request
     * @return
     */
    @Override
    public LoyaltyUserStatusResponse getLoyaltyStatus(
        LoyaltyUserStatusRequest request)
    {

        LoyaltyUserStatusResponse response = new LoyaltyUserStatusResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getLoyaltyStatus";
            response = (LoyaltyUserStatusResponse) transportService.executeRequest(url, request, null,
                LoyaltyUserStatusResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));
        }
        return response;

    }

    /**
     * 
     * Gets the status of the user based on his emailID and the loyalty program
     * in the request
     * 
     * @param request
     * @return
     */

    @Override
    public SnapBoxActivationResponse verifyAndActivateSnapBox(
        SnapBoxVerificationActivationRequest request)
    {

        SnapBoxActivationResponse response = new SnapBoxActivationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/verifyActivateSnapBox";
            response = (SnapBoxActivationResponse) transportService.executeRequest(url, request, null,
                SnapBoxActivationResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));

        }
        return response;
    }

    public static void main(String[] args)
    {

        // SnapBoxVerificationActivationRequest verificationActivationRequest =
        // new SnapBoxVerificationActivationRequest(requestedFromEmailID,
        // paramString)

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] { "applicationContext.xml" });
        LoyaltyClientService client = context.getBean(LoyaltyClientService.class);
        client.setWebServiceBaseURL("http://localhost:8094");
        try {

            LoyaltyUserStatusResponse response = client.getLoyaltyStatus(new LoyaltyUserStatusRequest(
                "ashish.saxena@snapdeal.com", LoyaltyProgram.SNAPBOX));
            System.out.println(response.isSuccessful());
            System.out.println(response.getUserEmailID() + " " + response.getLoyaltyProgram() + " "
                + response.getLoyaltyStatus());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}