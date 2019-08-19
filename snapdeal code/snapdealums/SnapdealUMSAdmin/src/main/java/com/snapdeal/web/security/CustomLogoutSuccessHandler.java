/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 29, 2011
 *  @author anudeep
 */
package com.snapdeal.web.security;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.snapdeal.web.utils.CookieManager;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private RedirectStrategy    redirectStrategy          = new DefaultRedirectStrategy();

    private static final String MOBILE_POP_UA             = "|acs-|alav|alca|amoi|audi|aste|avan|benq|bird|blac|blaz|brew|cell|cldc|cmd-|dang|doco|eric|hipt|inno|ipaq|java|jigs|kddi|keji|leno|lg-c|lg-d|lg-g|lge-|maui|maxo|midp|mits|mmef|mobi|mot-|moto|mwbp|nec-|newt|noki|opwv|palm|pana|pant|pdxg|phil|play|pluc|port|prox|qtek|qwap|sage|sams|sany|sch-|sec-|send|seri|sgh-|shar|sie-|siem|smal|smar|sony|sph-|symb|t-mo|teli|tim-|tosh|tsm-|upg1|upsi|vk-v|voda|w3c |wap-|wapa|wapi|wapp|wapr|webc|winw|winw|xda|xda-|";

    private static final String MOBILE_USER_AGENT_PATTERN = ".*(up\\.browser|up\\.link|windows ce|iphone|iemobile|mini|mmp|symbian|midp|wap|phone|pocket|mobile|pda|psp).*";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CookieManager.deleteCookie(request, response, "cartId");
        CookieManager.deleteCookie(request, response, "cartQty");
        if (isMobileRequest(request.getHeader("user-agent"))) {
            redirectStrategy.sendRedirect(request, response, "/m");
        } else {
            redirectStrategy.sendRedirect(request, response, "/login");
        }

    }

    private static boolean isMobileRequest(String userAgent) {
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("windows") && !userAgent.contains("windows ce")) {
            return false;
        }
        if (Pattern.matches(MOBILE_USER_AGENT_PATTERN, userAgent)) {
            return true;
        }

        if (userAgent.length() > 3 && MOBILE_POP_UA.contains("|" + userAgent.substring(0, 4) + "|")) {
            return true;
        }

        return false;
    }
}
