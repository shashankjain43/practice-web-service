package com.snapdeal.ums.userNEFTDetails.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.userNEFTDetails.services.IUserNEFTDetailsClientService;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.AddUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.DeactivateUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.GetActiveUserNEFTDetailsResponse;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsRequest;
import com.snapdeal.ums.userNeftDetails.VerifyUserNEFTDetailsResponse;

/**
 * Client to avail user NEFT details related services.
 * 
 * @author ashish saxena
 * 
 */
@Service
public class UserNEFTDetailsClientService implements IUserNEFTDetailsClientService
{

    // should be something like - http://HOST:PORT
    private String baseURL;

    private static final String SERVICE_URL = "/service/ums/user/neftDetails";

    @Autowired
    private ITransportService transportService;

    @Autowired
    private IUMSClientService umsClientService;

    @Autowired
    private final static Logger LOG = (org.slf4j.LoggerFactory.getLogger(UserNEFTDetailsClientService.class));

    @PostConstruct
    public void init()
    {

        transportService.registerService("/service/ums/user/neftDetails/",
            "neftDetailsService.");
    }

    @Override
    public void setWebServiceBaseURL(String webServiceBaseURL)
    {

        this.baseURL = webServiceBaseURL;
        LOG.info("UserNEFTDetailsClientService's web service base URL has been set to:" + webServiceBaseURL);
    }

    private String getWebServiceURL() throws TransportException
    {

        if (baseURL == null) {
            // If baseURL has not been set explicitly, load it from the
            // umsClientService.
            String umsBaseURL = umsClientService.getWebServiceBaseURL();
            baseURL = umsBaseURL;
            LOG.info("UserNEFTDetailsClientService's web service base URL has not been explicitly set! Therefore, automatically setting it to:" + umsBaseURL);

            if (baseURL == null) {
                // Oh! God! Base URL is not here also! NO OPTION but to log and
                // raise a BASE_URL_MISSING_EXCEPTION
                LOG.error("CANT SEND REQUEST TO UMS SERVER! Neither a base URL has been set at UMS client service, nor has it been set for  "
                    + UserNEFTDetailsClientService.class);

                throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
            }
        }
        LOG.info("Will send service request to {}", baseURL);
        return baseURL + SERVICE_URL;
    }

    /**
     * Adds/updates (if all the details already exists!) and verifies user neft
     * details in the request.
     */
    @Override
    public AddUserNEFTDetailsResponse addVerifyActivateUserNEFTDetails(AddUserNEFTDetailsRequest request)
    {

        AddUserNEFTDetailsResponse response = new AddUserNEFTDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addVerifyActivateUserNEFTDetails";
            response = (AddUserNEFTDetailsResponse) transportService.executeRequest(url, request, null,
                AddUserNEFTDetailsResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error(
                "Error caught in UserNEFTDetailsClientService#addVerifyActivateUserNEFTDetails(). Message: {}, error {}",
                e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));

        }
        return response;

    }

    /**
     * Verifies the user NEFT details record as represented by the ID in the
     * request.
     */
    @Override
    public VerifyUserNEFTDetailsResponse verifyActivateExistingUserNEFTDetails(VerifyUserNEFTDetailsRequest request)
    {

        VerifyUserNEFTDetailsResponse response = new VerifyUserNEFTDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/verifyActivateExistingUserNEFTDetails";
            response = (VerifyUserNEFTDetailsResponse) transportService.executeRequest(url, request, null,
                VerifyUserNEFTDetailsResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error(
                "Error caught in UserNEFTDetailsClientService#verifyActivateExistingUserNEFTDetails(). Message: {}, error {}",
                e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));

        }
        return response;
    }

    /**
     * Returns active user NEFT details, if available for the email ID in the
     * request.
     */
    @Override
    public GetActiveUserNEFTDetailsResponse getActiveUserNEFTDetails(GetActiveUserNEFTDetailsRequest request)
    {

        GetActiveUserNEFTDetailsResponse response = new GetActiveUserNEFTDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActiveUserNEFTDetails";
            response = (GetActiveUserNEFTDetailsResponse) transportService.executeRequest(url, request, null,
                GetActiveUserNEFTDetailsResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error caught in UserNEFTDetailsClientService#getActiveUserNEFTDetails(). Message: {}, error {}",
                e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));
        }
        return response;

    }

    /**
     * De-activates the user NEFT details as represented by the ID the in
     * request.
     */
    @Override
    public DeactivateUserNEFTDetailsResponse deActivateUserNEFTDetails(DeactivateUserNEFTDetailsRequest request)
    {

        DeactivateUserNEFTDetailsResponse response = new DeactivateUserNEFTDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/deActivateUserNEFTDetails";
            response = (DeactivateUserNEFTDetailsResponse) transportService.executeRequest(url, request, null,
                DeactivateUserNEFTDetailsResponse.class);
        }
        catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error(
                "Error caught in UserNEFTDetailsClientService#deActivateUserNEFTDetails(). Message: {}, error {}",
                e.getMessage(), e);
            response.addValidationError(new ValidationError(ErrorConstants.UNEXPECTED_ERROR.getCode(), e.getMessage()));
        }
        return response;

    }

}
