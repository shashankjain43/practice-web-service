package com.snapdeal.cps.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 *
 */

@Component("cpsProperty")
public class CPSProperties {

    @Value("${product.service.url}")
    private String productServiceUrl;
    
    @Value("${ipms.service.url}")
    private String ipmsServiceUrl;
    
    @Value("${snapdeal.site.url}")
    private String snapdealSiteUrl;
    
    @Value("${image.server.old.url}")
    private String oldImageServerUrl;
    
    @Value("${image.server.new.url}")
    private String newImageServerUrl;
    
    
    public String getProductServiceUrl() {
        return productServiceUrl;
    }

    public String getIpmsServiceUrl() {
        return ipmsServiceUrl;
    }

    public String getSnapdealSiteUrl() {
        return snapdealSiteUrl;
    }

    public String getOldImageServerUrl() {
        return oldImageServerUrl;
    }

    public String getNewImageServerUrl() {
        return newImageServerUrl;
    }

}
