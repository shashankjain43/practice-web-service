package com.snapdeal.opspanel.promotion.rp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freecharge.klickpay.client.KlickpayClient;
import com.freecharge.klickpay.vo.MerchantDetailsVO;
import com.freecharge.klickpay.vo.MerchantInfoVO;
import com.freecharge.klickpay.vo.MerchantSCConfigVO;
import com.freecharge.klickpay.vo.MerchantVO;
import com.freecharge.klickpay.vo.PanelGenericResponse;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;


@Controller
@Validated
@RequestMapping("klickpay")
@Slf4j
public class KlickpayController {
    
    @Autowired
    KlickpayClient klickpayClient;

    Logger logger = LoggerFactory.getLogger(KlickpayController.class);
    
    @PreAuthorize("(hasPermission('OPS_KLICKPAY'))")
    @RequestMapping(value = "/merchant/get/{merchantCode}/{merchantKey}", method = RequestMethod.GET)
    public @ResponseBody MerchantInfoVO  getMerchant(@PathVariable("merchantCode") String merchantCode, @PathVariable("merchantKey") String merchantKey) {
        MerchantInfoVO info = klickpayClient.getMerchant(merchantCode, merchantKey);
        return info;
    }

    @PreAuthorize("(hasPermission('OPS_KLICKPAY'))")
    @RequestMapping(value = "/merchant/create", method = RequestMethod.POST)
    public @ResponseBody MerchantDetailsVO createMerchant(@RequestBody MerchantVO merchant){
        return klickpayClient.createMerchant(merchant);
    }
    
    @PreAuthorize("(hasPermission('OPS_KLICKPAY'))")
    @RequestMapping(value = "/scmapping/create/{merchantCode}", method = RequestMethod.POST)
    public @ResponseBody PanelGenericResponse createSCMapping(@PathVariable("merchantCode") String merchantCode, @RequestBody MerchantSCConfigVO scConfigVO){
        return klickpayClient.createMerchantSCMapping(scConfigVO, merchantCode);
    }
}
