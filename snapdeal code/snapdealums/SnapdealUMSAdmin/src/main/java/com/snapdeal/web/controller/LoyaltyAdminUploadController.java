/*
 * Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @version 1.0, 27-Aug-2010
 * 
 * @author Karan Sachdeva
 */
package com.snapdeal.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyProgram;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyStatus;
import com.snapdeal.ums.loyalty.LoyaltyUploadRq;
import com.snapdeal.ums.loyalty.LoyaltyUploadRs;
import com.snapdeal.ums.services.loyalty.LoyaltyUploadManager;

@Controller
@RequestMapping("/admin/loyalty")
public class LoyaltyAdminUploadController
{

    private static final Logger log = LoggerFactory.getLogger(LoyaltyAdminUploadController.class);

    @Autowired
    private LoyaltyUploadManager loyaltyUploadManager;

    @RequestMapping("/uploadSnapBoxEligibilitySheet")
    public String uploadLoyaltySheet(ModelMap model)
    {

        return "admin/loyalty/uploadSnapBoxEligibilitySheet";
    }

    @RequestMapping(value = "uploadLoyaltyFile", method = RequestMethod.POST)
    public String uploadLoyaltyFile(@RequestParam("uploadedFile") CommonsMultipartFile file, ModelMap model)
    {

        log.info(Thread.currentThread().getName() + " entered upload()");

        LoyaltyUploadRq loyaltyUploadRq = new LoyaltyUploadRq();
        loyaltyUploadRq.setLoyaltyProgram(LoyaltyProgram.SNAPBOX);
        loyaltyUploadRq.setLoyaltyStatus(LoyaltyStatus.ELIGIBLE);
        loyaltyUploadRq.setFileName(file.getFileItem().getName());
        loyaltyUploadRq.setFileContent(file.getBytes());

        LoyaltyUploadRs loyaltyUploadRs = loyaltyUploadManager.processLoyaltyUploads(loyaltyUploadRq);

        log.info("Uploaded FileName: " + file.getFileItem().getName() + " of size:" + file.getSize());

        model.addAttribute("loyaltyUploadRs", loyaltyUploadRs);
        model.addAttribute("uploadedFile", file.getFileItem().getName());
        return "admin/loyalty/uploadSnapBoxEligibilitySheet";
    }

}
