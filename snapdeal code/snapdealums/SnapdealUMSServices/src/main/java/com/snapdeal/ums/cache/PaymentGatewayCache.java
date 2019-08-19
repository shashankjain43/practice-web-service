/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 11-Feb-2013
 *  @author umang
 */
package com.snapdeal.ums.cache;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.oms.base.sro.payment.PaymentGatewaySRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSubtypeEmiMappingSRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSubtypeMappingSRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSubtypeSRO;
import com.snapdeal.oms.base.vo.EmiModeSubtypesMappingVO;
import com.snapdeal.oms.base.vo.EmiMonthVO;
import com.snapdeal.oms.base.vo.PaymentModeVO;

@Cache(name = "umsPaymentGatewayCache")
public class PaymentGatewayCache {

    private List<PaymentModeVO>                         paymentModes                               = new ArrayList<PaymentModeVO>();
    private List<PaymentGatewaySRO>                     paymentGateways                            = new ArrayList<PaymentGatewaySRO>();
    private Map<String, PaymentModeVO>                  codeToPaymentModes                         = new HashMap<String, PaymentModeVO>();
    private Map<String, PaymentModeSubtypeSRO>             codeToPaymentModeSubtypes                  = new HashMap<String, PaymentModeSubtypeSRO>();
   //TODO private Map<String, AbstractPaymentGatewayProvider> paymentGatewayProviders                    = new HashMap<String, AbstractPaymentGatewayProvider>();
    private Map<String, Map<String, String>>            paymentGatewayToSubtypeToSubtypeMappingMap = new HashMap<String, Map<String, String>>();
    private Map<String, PaymentGatewaySRO>              nameToPaymentGatewayMap                    = new HashMap<String, PaymentGatewaySRO>();
    private Map<Integer, PaymentGatewaySRO>             idToPaymentGateway                         = new HashMap<Integer, PaymentGatewaySRO>();
    private Map<String, PaymentGatewaySRO>              paymentModeSubtypeToPaymentGateway         = new HashMap<String, PaymentGatewaySRO>();
    private Map<String, PaymentGatewaySRO>              paymentModeSubtypeToAlgoPaymentGateway     = new HashMap<String, PaymentGatewaySRO>();
    private List<EmiModeSubtypesMappingVO>              emiModeSubtype                             = new ArrayList<EmiModeSubtypesMappingVO>();

    public void addPaymentMode(PaymentModeSRO paymentMode) {
        paymentModes.add(toPaymentModeVO(paymentMode));
        codeToPaymentModes.put(paymentMode.getCode(), toPaymentModeVO(paymentMode));
    }
    
    public void addEmiModeSubtypeMapping(PaymentModeSubtypeEmiMappingSRO paymentModeSubtypeEmiMapping,List<PaymentModeSubtypeEmiMappingSRO> emiMonthsList){
        emiModeSubtype.add(toEmiModeSubtypesMappingVO(paymentModeSubtypeEmiMapping,emiMonthsList));
    }

    private EmiModeSubtypesMappingVO toEmiModeSubtypesMappingVO(PaymentModeSubtypeEmiMappingSRO paymentModeSubtypeEmiMapping, List<PaymentModeSubtypeEmiMappingSRO> emiMonthsList) {
        EmiModeSubtypesMappingVO emiVO=new EmiModeSubtypesMappingVO();    
        emiVO.setEmiDisplayName(paymentModeSubtypeEmiMapping.getDisplayName());
        emiVO.setId(paymentModeSubtypeEmiMapping.getId());
        emiVO.setPreferred(paymentModeSubtypeEmiMapping.getPaymentModeSubtype().isPreferred());
        emiVO.setEnabled(paymentModeSubtypeEmiMapping.getPaymentModeSubtype().isEnabled());
//        emiVO.setMinAmount(paymentModeSubtypeEmiMapping.getPaymentModeSubtype().getMinAmount());
        for(PaymentModeSubtypeEmiMappingSRO monthElement: emiMonthsList){
            EmiMonthVO monthsVO =new EmiMonthVO();
            monthsVO.setEmiMonths(monthElement.getEmiMonths());
            monthsVO.setCode(monthElement.getPaymentModeSubtype().getCode());
            monthsVO.setEnabled(monthElement.getPaymentModeSubtype().isEnabled());
            emiVO.getEmiMonths().add(monthsVO);
        }       
        return emiVO;
    }

    private PaymentModeVO toPaymentModeVO(PaymentModeSRO paymentMode) {
        PaymentModeVO modeVO = new PaymentModeVO();
        modeVO.setCode(paymentMode.getCode());
        modeVO.setDisplayName(paymentMode.getDisplayName());
        modeVO.setEnabled(paymentMode.isEnabled());
        modeVO.setId(paymentMode.getId());
        modeVO.setPaymentGatewayName(paymentMode.getPaymentGateway().getName());
        modeVO.setAllowedGateways(Arrays.asList(paymentMode.getAllowedGateways().split(",")));
        modeVO.setHelpText(paymentMode.getHelpText());
        modeVO.setSubtypeDisplayText(paymentMode.getSubtypeDisplayText());
        modeVO.setDisplay(paymentMode.isDisplay());
        modeVO.setDeviceType(paymentMode.getDeviceType());
        List<PaymentModeSubtypeSRO> modeSubtypes = new ArrayList<PaymentModeSubtypeSRO>();
        paymentModeSubtypeToPaymentGateway.put(modeVO.getCode(), paymentMode.getPaymentGateway());
        paymentModeSubtypeToAlgoPaymentGateway.put(modeVO.getCode(), paymentMode.getAlgoPaymentGateway());
        for (PaymentModeSubtypeSRO modeSubtype : paymentMode.getPaymentModeSubtypes()) {
            if (modeSubtype.isEnabled()) {
                modeSubtypes.add(modeSubtype);
                codeToPaymentModeSubtypes.put(modeSubtype.getCode(), modeSubtype);
                if (modeSubtype.getPaymentGateway() != null) {
                    paymentModeSubtypeToPaymentGateway.put(modeVO.getCode() + modeSubtype.getCode(), modeSubtype.getPaymentGateway());
                    paymentModeSubtypeToAlgoPaymentGateway.put(modeVO.getCode() + modeSubtype.getCode(), modeSubtype.getAlgoPaymentGateway());
                }
            }
        }
        modeVO.setModeSubtypes(modeSubtypes);
        return modeVO;
    }

    public PaymentGatewaySRO getPaymentGatewayByModeAndSubtype(String paymentMode, String paymentModeSubtype) {
        String paymentSubtypeIdentifier = paymentMode;
        if (paymentModeSubtype != null) {
            paymentSubtypeIdentifier = paymentMode + paymentModeSubtype;
        }
        PaymentGatewaySRO paymentGateway = paymentModeSubtypeToPaymentGateway.get(paymentSubtypeIdentifier);
        if (paymentGateway != null) {
            return paymentGateway;
        } else {
            return paymentModeSubtypeToPaymentGateway.get(paymentMode);
        }
    }

    public PaymentGatewaySRO getAlgoPaymentGatewayByModeAndSubtype(String paymentMode, String paymentModeSubtype) {
        String paymentSubtypeIdentifier = paymentMode;
        if (paymentModeSubtype != null) {
            paymentSubtypeIdentifier = paymentMode + paymentModeSubtype;
        }
        PaymentGatewaySRO paymentGateway = paymentModeSubtypeToAlgoPaymentGateway.get(paymentSubtypeIdentifier);
        if (paymentGateway != null) {
            return paymentGateway;
        } else {
            return paymentModeSubtypeToAlgoPaymentGateway.get(paymentMode);
        }
    }

    public void addPaymentGateway(PaymentGatewaySRO gateway) {
        paymentGateways.add(gateway);
        nameToPaymentGatewayMap.put(gateway.getName(), gateway);
        idToPaymentGateway.put(gateway.getId(), gateway);
      //TODO
        /* if (!paymentGatewayProviders.containsKey(paymentGateway.getName())) {
            AbstractPaymentGatewayProvider provider = PaymentGatewayProviderBuilder.buildProvider(paymentGateway.getClassName());
            provider.setPaymentGateway(paymentGateway);
            paymentGatewayProviders.put(paymentGateway.getName(), provider);
        }*/
    }

    public List<PaymentGatewaySRO> getPaymentGateways() {
        return paymentGateways;
    }
    public List<EmiModeSubtypesMappingVO> getEmiModeSubtype(){
        return emiModeSubtype;
    }

    public void addPaymentModeSubtypeMapping(PaymentModeSubtypeMappingSRO mapping) {
        Map<String, String> subtypeMappings = paymentGatewayToSubtypeToSubtypeMappingMap.get(mapping.getPaymentGateway().getName());
        if (subtypeMappings == null) {
            subtypeMappings = new HashMap<String, String>();
            paymentGatewayToSubtypeToSubtypeMappingMap.put(mapping.getPaymentGateway().getName(), subtypeMappings);
        }
        subtypeMappings.put(mapping.getPaymentModeSubtype().getCode(), mapping.getPgValue());
    }

    //TODO
    /*public AbstractPaymentGatewayProvider getPaymentGatewayProvider(String name) {
        AbstractPaymentGatewayProvider provider = paymentGatewayProviders.get(name);
        if (provider == null) {
            throw new IllegalStateException("provider not loaded:" + name);
        } else {
            return provider;
        }
    }*/

    
    //TODO
    /*public Collection<AbstractPaymentGatewayProvider> getAllPaymentGatewayProviders() {
        return this.paymentGatewayProviders.values();
    }*/

    public List<PaymentModeVO> getPaymentModes() {
        return paymentModes;
    }

    public PaymentModeVO getPaymentModeByCode(String code) {
        return codeToPaymentModes.get(code);
    }

    public PaymentModeSubtypeSRO getPaymentModeSubtypeByCode(String code) {
        return codeToPaymentModeSubtypes.get(code);
    }

    public Map<String, PaymentModeVO> getCodeToPaymentModes() {
        return codeToPaymentModes;
    }

    public String getPaymentGatewaySubtypeMapping(String paymentModeSubtype, String paymentGateway) {
        Map<String, String> subtypeMappings = paymentGatewayToSubtypeToSubtypeMappingMap.get(paymentGateway);
        if (subtypeMappings != null) {
            return subtypeMappings.get(paymentModeSubtype);
        }
        return null;
    }

    public PaymentGatewaySRO getPaymentGatewayById(int paymentGatewayId) {
        return idToPaymentGateway.get(paymentGatewayId);
    }

    
    public PaymentGatewaySRO getPaymentGatewayByName(String paymentGatewayName) {
        return nameToPaymentGatewayMap.get(paymentGatewayName);
    }

}

