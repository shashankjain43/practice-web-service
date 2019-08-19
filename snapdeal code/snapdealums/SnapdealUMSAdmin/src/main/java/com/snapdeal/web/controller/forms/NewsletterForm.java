/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 27, 2010
 *  @author Vikash
 */
package com.snapdeal.web.controller.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.admin.model.NewsletterFilterDTO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.web.model.NewsletterDTO;

public class NewsletterForm {

    @Valid
    private Newsletter                newsletter;
    private String                    state                = "all";
    private String                    selectedCityIds;
    private String                    testEmails;
    private String                    emailTemplateType;
    private String                    newsletterDate;
    private Integer                   newsletterTimeHour;
    private Integer                   newsletterTimeMin;
    private String                    dealType;
    private List<ZoneSRO>             zoneList             = new ArrayList<ZoneSRO>();
    private String                    zoneIdList;
    private List<NewsletterDTO>       newsletterDTOs       = new ArrayList<NewsletterDTO>();
    private List<NewsletterFilterDTO> newsletterFilterDTOs = new ArrayList<NewsletterFilterDTO>();

    private MultipartFile             topBanner;
    private String                    topBannerAltText;
    private String                    topBannerLink;

    private MultipartFile             footerBanner;
    private String                    footerBannerAltText;
    private String                    footerBannerLink;

    private String                    customPogIds;

    public NewsletterForm() {
    }

    public NewsletterForm(Newsletter newsletter) {
        this.newsletter = newsletter;
    }

    public Newsletter getNewsletter() {
        return this.newsletter;
    }

    public void setNewsletter(Newsletter newsletter) {
        this.newsletter = newsletter;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplateType = emailTemplate;
    }

    public String getEmailTemplateType() {
        return this.emailTemplateType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getDealType() {
        return dealType;
    }

    public void setNewsletterDTOs(List<NewsletterDTO> newsletterDTO) {
        this.newsletterDTOs = newsletterDTO;
    }

    public List<NewsletterDTO> getNewsletterDTOs() {
        return newsletterDTOs;
    }

    public void setZoneList(List<ZoneSRO> zoneList) {
        this.zoneList = zoneList;
    }

    public List<ZoneSRO> getZoneList() {
        return zoneList;
    }

    public void setNewsletterDate(String newsletterDate) {
        this.newsletterDate = newsletterDate;
    }

    public String getNewsletterDate() {
        return newsletterDate;
    }

    public void setTestEmails(String testEmails) {
        this.testEmails = testEmails;
    }

    public String getTestEmails() {
        return testEmails;
    }

    public void setNewsletterTimeHour(Integer newsletterHour) {
        this.newsletterTimeHour = newsletterHour;
    }

    public Integer getNewsletterTimeHour() {
        return newsletterTimeHour;
    }

    public void setNewsletterTimeMin(Integer newsletterTimeMin) {
        this.newsletterTimeMin = newsletterTimeMin;
    }

    public Integer getNewsletterTimeMin() {
        return newsletterTimeMin;
    }

    public void setZoneIdList(String zoneIdList) {
        this.zoneIdList = zoneIdList;
    }

    public String getZoneIdList() {
        return zoneIdList;
    }

    public void setSelectedCityIds(String selectedCityIds) {
        this.selectedCityIds = selectedCityIds;
    }

    public String getSelectedCityIds() {
        return selectedCityIds;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public List<NewsletterFilterDTO> getNewsletterFilterDTOs() {
        return newsletterFilterDTOs;
    }

    public void setNewsletterFilterDTOs(List<NewsletterFilterDTO> newsletterFilterDTOs) {
        this.newsletterFilterDTOs = newsletterFilterDTOs;
    }

    public void setEmailTemplateType(String emailTemplateType) {
        this.emailTemplateType = emailTemplateType;
    }

    public MultipartFile getTopBanner() {
        return topBanner;
    }

    public void setTopBanner(MultipartFile topBanner) {
        this.topBanner = topBanner;
    }

    public String getTopBannerAltText() {
        return topBannerAltText;
    }

    public void setTopBannerAltText(String topBannerAltText) {
        this.topBannerAltText = topBannerAltText;
    }

    public String getTopBannerLink() {
        return topBannerLink;
    }

    public void setTopBannerLink(String topBannerLink) {
        this.topBannerLink = topBannerLink;
    }

    public MultipartFile getFooterBanner() {
        return footerBanner;
    }

    public void setFooterBanner(MultipartFile footerBanner) {
        this.footerBanner = footerBanner;
    }

    public String getFooterBannerAltText() {
        return footerBannerAltText;
    }

    public void setFooterBannerAltText(String footerBannerAltText) {
        this.footerBannerAltText = footerBannerAltText;
    }

    public String getFooterBannerLink() {
        return footerBannerLink;
    }

    public void setFooterBannerLink(String footerBannerLink) {
        this.footerBannerLink = footerBannerLink;
    }

    public String getCustomPogIds() {
        return customPogIds;
    }

    public void setCustomPogIds(String customPogIds) {
        this.customPogIds = customPogIds;
    }

}
