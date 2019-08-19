/* 
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 22-Oct-2012
*  @author naveen

package com.snapdeal.ums.email.ext.email;

import java.io.Serializable;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.core.cache.SystemPropertiesCache;
import com.snapdeal.core.dto.ProductWorkflowDTO;
import com.snapdeal.core.entity.Product;
import com.snapdeal.core.entity.ProductOffer;

public class ProductWorkflowSRO implements Serializable {


    *//**
	 * 
	 *//*
	private static final long serialVersionUID = 8924748489883114029L;
	private String            productName;
    private String            actionBy;
    private String            sku;
    private String            comments;
    private String            categoryURL;
    private String            url;

    public ProductWorkflowSRO() {

    }
    public ProductWorkflowSRO(ProductWorkflowDTO dto){
        this.productName=dto.getProductName();
        this.actionBy=dto.getActionBy();
        this.sku=dto.getSku();
        this.comments=dto.getComments();
        this.categoryURL=dto.getCategoryURL();
        this.url=dto.getUrl();
        
    }
    public ProductWorkflowSRO(ProductOffer offer, String comments) {
        this.setProductName(offer.getName());
        this.setActionBy(offer.getBdEmail());
        Product p = offer.getProductOfferProductMappings().iterator().next().getProduct();
        //  this.setSku(p.getVendorSku());
        this.setCategoryURL(p.getProductCategoryProductMappings().iterator().next().getProductCategory().getUrl());
        this.setComments(comments);
        String url = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getContextPath("http://www.snapdeal.com") + offer.getCalculatedPageUrl(null);
        this.setUrl(url);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategoryURL(String categoryURL) {
        this.categoryURL = categoryURL;
    }

    public String getCategoryURL() {
        return categoryURL;
    }
}

 */