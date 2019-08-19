 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 11-Nov-2012
*  @author naveen
*/
package com.snapdeal.ums.client.services.impl;

import org.springframework.stereotype.Service;

import com.snapdeal.ums.client.services.IUMSClientService;

@Service("umsClientService")
public class UMSClientServiceImpl implements IUMSClientService {

    private String webServiceBaseURL;

    @Override
    public void setWebServiceBaseURL(String webServiceBaseURL) {
        this.webServiceBaseURL = webServiceBaseURL;
    }

    @Override
    public String getWebServiceBaseURL() {
        return webServiceBaseURL;
    }
}

 