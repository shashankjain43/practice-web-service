/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Aug-2010
 *  @author bala
 */
package com.snapdeal.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.core.utils.RequestContext;
import com.snapdeal.web.security.SnapdealUser;
import com.snapdeal.web.utils.WebContextUtils;

@Controller
public class StaticPagesController {

    @RequestMapping("/info/error")
    public String staticPages(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, value = "code") Integer code, ModelMap model) {
        /*Map<String, String> parameters = new HashMap<String, String>();
        for (Object parameterName : request.getParameterMap().keySet()) {
            if (!parameterName.toString().equals("code")) {
                parameters.put(parameterName.toString(), request.getParameter(parameterName.toString()));
            }
        }
        if (code != null && code.equals(HttpStatus.NOT_FOUND.value())) {
            StringBuilder sb = new StringBuilder();
            sb.append("redirect:").append(MAIN_SITE_REDIRECT);
            sb.deleteCharAt(sb.length() - 1).append(RequestContext.current().getRequestUrl()).append("?");
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(parameters.get(name)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }*/
        return "common/error";
    }

    @RequestMapping(value = "about")
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value = "help")
    public String help(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "help";
    }

    @RequestMapping(value = "terms")
    public String terms(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user);
        }
        return "terms";
    }

    @RequestMapping(value = "termsSale")
    public String termsSale(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user);
        }
        return "termSale";
    }

    @RequestMapping(value = "termsWap")
    public String termsWap(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user);
        }
        return "termsWap";
    }
    @RequestMapping(value = "copyrightpolicy")
    public String copyrightpolicy(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user);
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        return "copyrightpolicy";
    }

    @RequestMapping(value = "feedBackThankYou")
    public String feedBackThankYou(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user.getUser());
        }
        
        return "feedBackThankYou";
    }
    @RequestMapping(value = "termsofsale")
    public String termsofsale(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user.getUser());
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        
        return "termsofsale";
    }
    @RequestMapping(value = "termsofuse")
    public String termsofuse(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user.getUser());
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        
        return "termsofuse";
    }

    @RequestMapping(value = "termsPrivacy")
    public String termsPrivacy(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("sdcash", user.getUser().getSdCash());
            model.addAttribute("user", user);
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        return "termsPrivacy";
    }
    @RequestMapping(value = "wapFaq")
    public String faq(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        return "faq";
    }
    @RequestMapping(value = "privacyPolicy")
    public String privacyPolicy(ModelMap model) {
        SnapdealUser user = WebContextUtils.getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("utm_source", RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
        return "privacyPolicy";
    }
}
