/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jan 31, 2011
 *  @author rahul
 */
package com.snapdeal.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.core.entity.CatalogSeo;
import com.snapdeal.base.utils.EncryptionUtils;
import com.snapdeal.base.utils.StringUtils;

public class SDWebUtils {

    public static String getSubscriptionConfirmationLink(String email, String code) {
        StringBuilder builder = new StringBuilder().append(PathResolver.getHttpPath()).append("/confirmsubsciberemail?email=");
        try {
            builder.append(URLEncoder.encode(email, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            builder.append(email);
        }
        builder.append("&code=").append(code);
        return builder.toString();
    }

    public static String getEmailVerificationLink(String url, String email, EmailVerificationCode emailVerificationCode) {
        if (emailVerificationCode != null) {
            return getEmailVerificationLink(url, email, emailVerificationCode.getCode(), emailVerificationCode.getSource(), emailVerificationCode.getTargetUrl());
        }
        return null;
    }

    public static String getEmailVerificationLink(String url, String email, String confirmationCode, String source, String targetUrl) {
        StringBuilder builder = new StringBuilder().append(PathResolver.getHttpPath()).append("/").append(url);
        if (url.indexOf("?") == -1) {
            builder.append("?email=");
        } else {
            builder.append("&email=");
        }
        try {
            builder.append(URLEncoder.encode(email, "UTF-8"));
            builder.append("&code=").append(confirmationCode);
            if (StringUtils.isNotEmpty(source)) {
                builder.append("&source=").append(source);
            }
            if (StringUtils.isNotEmpty(targetUrl)) {
                builder.append("&targetUrl=").append(URLEncoder.encode(targetUrl, "UTF-8"));

            }
        } catch (UnsupportedEncodingException e) {
            builder.append(email);
            builder.append("&code=").append(confirmationCode);
            if (StringUtils.isNotEmpty(source)) {
                builder.append("&source=").append(source);
            }
            if (StringUtils.isNotEmpty(targetUrl)) {
                builder.append("&targetUrl=").append(targetUrl);
            }
        }
        return builder.toString();
    }

    public static String getStandingDealsConfirmationLink(String email, String code, int zoneId) {
        StringBuilder builder = new StringBuilder().append(PathResolver.getHttpPath()).append("/confirmEmailStanding?email=");
        try {
            builder.append(URLEncoder.encode(email, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            builder.append(email);
        }
        builder.append("&code=").append(code);
        builder.append("&zoneId=").append(zoneId);
        return builder.toString();
    }

    /**
     * Returns the absolute URL of the Buy page. If the buy page is for some 
     * old transaction, then appends the encrypted orderId as request parameter.
     * 
     * @param dealPageUrl
     * @param zonePageUrl
     * @param orderId
     * @return BuyPageUrl
     * @throws UnsupportedEncodingException
     */
    public static String getAbsoluteBuyPageUrl(String dealPageUrl, String zonePageUrl, String orderId) throws UnsupportedEncodingException {
        String contextPath = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContextPath("http://www.snapdeal.com");
        String absoluteBuyPageUrl = contextPath + "/" + getRelativeBuyPageUrl(dealPageUrl, zonePageUrl, orderId);
        return absoluteBuyPageUrl;
    }
    
    public static String getRelativeBuyPageUrl(String dealPageUrl, String zonePageUrl, String orderId) throws UnsupportedEncodingException {
        String relativeBuyPageUrl = "deal-" + zonePageUrl + "-" + dealPageUrl + "/buy";
        if (orderId != null) {
            String encryptedId = EncryptionUtils.encrypt(orderId);
            encryptedId = URLEncoder.encode(encryptedId, "UTF-8");
            relativeBuyPageUrl += "?id=" + encryptedId;    
        }
        return relativeBuyPageUrl;
    }
    
    public static CatalogSeo getCatalogSeoClone(CatalogSeo catalogSeo) {
        CatalogSeo newCatalogSeo = new CatalogSeo();
        newCatalogSeo.setId(catalogSeo.getId());
        newCatalogSeo.setCatalogId(catalogSeo.getCatalogId());
        newCatalogSeo.setCatalogType(catalogSeo.getCatalogType());
        newCatalogSeo.setParentCategoryId(catalogSeo.getParentCategoryId());
        newCatalogSeo.setChildCategoryId(catalogSeo.getChildCategoryId());
        newCatalogSeo.setFacebookTitle(catalogSeo.getFacebookTitle());
        newCatalogSeo.setBuzzTitle(catalogSeo.getBuzzTitle());
        newCatalogSeo.setTwitterTitle(catalogSeo.getTwitterTitle());
        newCatalogSeo.setPageTitle(catalogSeo.getPageTitle());
        newCatalogSeo.setMetaDescription(catalogSeo.getMetaDescription());
        newCatalogSeo.setMetaKeywords(catalogSeo.getMetaKeywords());
        newCatalogSeo.setStaticText(catalogSeo.getStaticText());
        newCatalogSeo.setCreated(catalogSeo.getCreated());
        newCatalogSeo.setUpdated(catalogSeo.getUpdated());
        newCatalogSeo.setUpdateMode(catalogSeo.getUpdateMode());
        return newCatalogSeo; 
    }
}
