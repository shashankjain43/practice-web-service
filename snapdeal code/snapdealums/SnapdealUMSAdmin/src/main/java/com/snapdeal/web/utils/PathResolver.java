/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 16, 2010
 *  @author rahul
 */
package com.snapdeal.web.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.MD5ChecksumUtils;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;

public class PathResolver {

    private Map<String, String> jsPaths  = new HashMap<String, String>();
    private Map<String, String> cssPaths = new HashMap<String, String>();
    private static final PathResolver pathResolver = new PathResolver();
    
    public static PathResolver getPathResolver() {
        return pathResolver;
    }

    public enum ResourceType {
        CSS("css"), JAVASCRIPT("js");

        private String type;

        private ResourceType(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }

    public String css(String initialPath) {
        String cssPath = cssPaths.get(initialPath);
        if (cssPath == null) {
            cssPath = getPath(initialPath, ResourceType.CSS);
            cssPaths.put(initialPath, cssPath);
        }
        return getStaticPath(cssPath);
    }

    public String js(String initialPath) {
        String jsPath = jsPaths.get(initialPath);
        if (jsPath == null) {
            jsPath = getPath(initialPath, ResourceType.JAVASCRIPT);
            jsPaths.put(initialPath, jsPath);
        }
        return getStaticPath(jsPath);
    }

    public String resources(String initialPath) {
        return getStaticPath(initialPath);
    }

    public String getScheme() {
        HttpServletRequest request = WebContextUtils.getRequest();
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme()).append("://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            builder.append(':').append(request.getServerPort());
        }
        builder.append(request.getContextPath());
        return builder.toString();
    }

    public String getHttp() {
        return getHttpPath();
    }

    public String getHttps() {
        return getHttpPath();
    }

    public static String getHttpPath() {
        HttpServletRequest request = WebContextUtils.getRequest();
        StringBuilder builder = new StringBuilder();
        builder.append("http://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            builder.append(':').append(request.getServerPort());
        }
        builder.append(request.getContextPath());
        return builder.toString();
    }

    public static String getHttpsPath() {
        HttpServletRequest request = WebContextUtils.getRequest();
        StringBuilder builder = new StringBuilder();
        builder.append("https://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            builder.append(':').append(request.getServerPort());
        }
        builder.append(request.getContextPath());
        return builder.toString();

    }

    private String getPath(String resourcePath, ResourceType resourceType) {
        if (resourcePath.startsWith("secure")) {
            resourcePath = resourcePath.substring(resourcePath.indexOf('/') + 1);
        }
        InputStream input = WebContextUtils.getServletContext().getResourceAsStream("/static/" + resourceType.type() + "/" + resourcePath);
        String md5Checksum;
        try {
            md5Checksum = MD5ChecksumUtils.getMD5Checksum(input);
        } catch (Exception e) {
            md5Checksum = StringUtils.getRandom();
        }
        String result = resourceType.type() + "/" + md5Checksum + "/" + resourcePath;
        return result;
    }

    public String getEncodedUrl(Object path) {
        return getEncodedUrl(path.toString());
    }

    public String getEncodedUrl(String path) {
        try {
            if (path.startsWith("http://")) {
                return URLEncoder.encode(path, "UTF-8");
            } else {
                return URLEncoder.encode(getHttpPath() + path, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            return getHttpPath() + path;
        }
    }

    public String getEncodedMessage(Object message) {
        return getEncodedMessage(message.toString());
    }

    public String getEncodedMessage(String message) {
        try {
            return URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return message;
        }
    }

    private static String getStaticPath(String path) {
        HttpServletRequest req = WebContextUtils.getRequest();

        if (!req.isSecure()) {
            StringBuilder sb = new StringBuilder();
            String staticResourcesPath = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getStaticResourcesPath();
            if (StringUtils.isEmpty(staticResourcesPath)) {
                return getHttpPath() + "/" + path;
            }
            sb.append(req.getScheme() + staticResourcesPath);
            sb.append(path);
            return sb.toString();
        } else {
            return getHttpPath() + "/" + path;
        }
    }
}
