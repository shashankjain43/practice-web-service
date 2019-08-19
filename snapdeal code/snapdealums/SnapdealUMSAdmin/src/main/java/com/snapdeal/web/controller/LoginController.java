/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 2, 2010
 *  @author singla
 */
package com.snapdeal.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snapdeal.web.controller.forms.SignupForm;
import com.snapdeal.web.utils.WebContextUtils;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage(@RequestParam(value = "authenticated", required = false) Boolean authenticated, @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "targetUrl", required = false) String targetUrl, ModelMap model) {
        SignupForm signupForm = new SignupForm();
        if (StringUtils.isNotEmpty(source)) {
            signupForm.setSource(source);
        }
        if (StringUtils.isNotEmpty(targetUrl)) {
            signupForm.setTargetUrl(targetUrl);
        }
        model.addAttribute("signupForm", signupForm);

        UserDetails user = WebContextUtils.getCurrentUser();
        if (user != null) {
            return "redirect:/";
        }

        if (authenticated != null && !authenticated) {
            return "redirect:/login?systemcode=508";
        } else {
            return "login/login";
        }
    }
    

}
