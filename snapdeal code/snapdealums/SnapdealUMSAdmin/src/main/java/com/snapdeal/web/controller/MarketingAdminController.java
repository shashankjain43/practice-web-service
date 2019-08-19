/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 4, 2010
 *  @author aarti
 */
package com.snapdeal.web.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.fileutils.SDFileUploadUtils;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.s4.base.exception.S4Exception;
import com.snapdeal.s4.base.sro.storage.PutObjectResponse;
import com.snapdeal.s4.client.service.S4ClientService;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.convertor.UMSConvertorServiceImpl;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;
import com.snapdeal.web.utils.SystemResponse;
import com.snapdeal.web.utils.SystemResponse.ResponseStatus;

@Controller
@RequestMapping("/admin")
@Path(MarketingAdminController.URL)
public class MarketingAdminController {
    public static final String           URL = "/json";
    
    private static final Logger LOG = LoggerFactory.getLogger(MarketingAdminController.class);
    
    @Autowired
    private ISubscriptionsServiceInternal subscriptionService;
    
    @Autowired
    private IUMSConvertorService  umsConvertorService;
    
    @Autowired
    private S4ClientService s4Client;

    
    @RequestMapping("/marketing/affiliateSubscription")
    public ModelAndView affiliateSubscription(ModelMap model) {
        AffiliateSubscriptionOffer subscriptionOffer = new AffiliateSubscriptionOffer();
        List<AffiliateSubscriptionOffer> offers = subscriptionService.getAllAffiliateSubscriptionOffers();
        model.addAttribute("offers", offers);
        model.addAttribute("subscriptionOffer", subscriptionOffer);
        return new ModelAndView("admin/affiliateSubscription",model);
    }
    

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/subscriptionOffer/{id}")
    @GET
    public AffiliateSubscriptionOfferSRO getSubscriptionOffer(@PathParam("id") String id) {
        AffiliateSubscriptionOfferSRO offer = umsConvertorService.getAffiliateSubscriptionOfferSROFromEntity(subscriptionService.getSubscriptionOfferById(Integer.parseInt(id)));
        return offer;
    }
    
    private String getUploadFilePath(String path, String originalFilename) {
        StringBuilder builder = new StringBuilder();

        builder.append(path).append(originalFilename);

        return builder.toString();
    }


    @RequestMapping("/marketing/affiliateSubscription/upload")
    @ResponseBody
    public String uploadImageForAffiliate(@RequestParam(value = "imageMobileFile" , required = false) MultipartFile desktopFile,@RequestParam(value = "imageFile" , required = false) MultipartFile mobileFile,
            ModelMap modelMap) {
        
        MultipartFile file = desktopFile == null ?mobileFile : desktopFile;
        LOG.info(file.getOriginalFilename());
        LOG.info(file.getContentType());
        SystemResponse response = null;
        if (!file.isEmpty()) {
            String filePath = getUploadFilePath("img/subscription/", file.getOriginalFilename());
            try {
                String rootDirectoryPath = CacheManager.getInstance().getCache(UMSPropertiesCache.class).getStaticContentRootDirectoryPath();
                String uploadFilePath = rootDirectoryPath + filePath;
                File uploadFile = new File(uploadFilePath);
                if (uploadFile.exists()) {
                    response = new SystemResponse(ResponseStatus.FAIL, " upload failed");
                    response.addItem("result", "A file with same name was uploaded before. Please check and upload again");
                    return new Gson().toJson(response);
                }
                  SDFileUploadUtils.upload(file.getInputStream(), null, uploadFilePath);
                PutObjectResponse s4Response = s4Client.putObjectWithFile(uploadFile);


                response = new SystemResponse(ResponseStatus.SUCCESS, "file uploaded");
                response.addItem("contentPath", s4Response.getResponseSRO().getContentPath());
                response.addItem("imageName", file.getOriginalFilename());
                response.addItem("uploadPath", uploadFilePath);
                response.addItem("result", "File uploaded successfully");
            } catch (IOException e) {
                e.printStackTrace();
                response = new SystemResponse(ResponseStatus.FAIL, " upload failed");
            } catch (S4Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = new SystemResponse(ResponseStatus.FAIL, " upload failed");
            } catch (TransportException e) {
                response = new SystemResponse(ResponseStatus.FAIL, " upload failed");
            }
        }
        return new Gson().toJson(response);

    }
    
    @RequestMapping("/marketing/affiliateSubscription/addOffer")
    public String addAffiliateSubscriptionOffer(AffiliateSubscriptionOffer subscriptionOffer, BindingResult bindingResult, ModelMap modelMap) {
        Date date = DateUtils.getCurrentTime();
        if (subscriptionOffer.getId() == null) {
            subscriptionOffer.setCreated(date);
        } else {
            subscriptionOffer.setCreated(subscriptionService.getSubscriptionOfferById(subscriptionOffer.getId()).getCreated());
        }
        subscriptionService.mergeAffiliateSubscriptionOffer(subscriptionOffer);
        return "redirect:/admin/marketing/affiliateSubscription?systemcode=820";
    }
    
}