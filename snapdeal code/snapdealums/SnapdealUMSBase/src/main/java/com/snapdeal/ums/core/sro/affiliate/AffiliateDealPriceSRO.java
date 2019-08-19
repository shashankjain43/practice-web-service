/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 31-Oct-2012
 *  @author ghanshyam
 */
package com.snapdeal.ums.core.sro.affiliate;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.entity.Affiliate;
import com.snapdeal.core.entity.AffiliateDealPrice;

public class AffiliateDealPriceSRO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5825937929736722046L;
    @Tag(1)
    private Integer           id;
    @Tag(3)
    private Affiliate        affiliates;
    @Tag(4)
    private int               price;
    @Tag(5)
    private int               payToMerchant;
    @Tag(6)
    private int               voucherPrice;
    @Tag(7)
    private boolean           noPromoCode;
    
    public AffiliateDealPriceSRO(AffiliateDealPrice adp){
        this.id = adp.getId();
        this.affiliates =adp.getAffiliates();
        this.price = adp.getPrice();
        this.payToMerchant=adp.getPayToMerchant();
        this.voucherPrice=adp.getVoucherPrice();
        this.noPromoCode=adp.isNoPromoCode();
        
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Affiliate getAffiliates() {
        return affiliates;
    }
    public void setAffiliates(Affiliate affiliates) {
        this.affiliates = affiliates;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPayToMerchant() {
        return payToMerchant;
    }
    public void setPayToMerchant(int payToMerchant) {
        this.payToMerchant = payToMerchant;
    }
    public int getVoucherPrice() {
        return voucherPrice;
    }
    public void setVoucherPrice(int voucherPrice) {
        this.voucherPrice = voucherPrice;
    }
    public boolean isNoPromoCode() {
        return noPromoCode;
    }
    public void setNoPromoCode(boolean noPromoCode) {
        this.noPromoCode = noPromoCode;
    }
    
    
}
