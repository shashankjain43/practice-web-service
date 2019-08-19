package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;

public class CartItemSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6289248803394826136L;

    @Tag(1)
    private int               shippingMethodCharges;

    @Tag(2)
    private int               quantity;

    @Tag(3)
    private int               availableQuantity;

    @Tag(5)
    private int               sdCash;

    @Tag(6)
    private int               promoValue;

    @Tag(7)
    private int               shippingCharges;

    @Tag(8)
    private int               sellingPrice;

    @Tag(9)
    private List<String>      freebie = new ArrayList<String>();

    @Tag(10)
    private String            name;
    
    @Tag(11)
    private int                 instantCashBack;
    
    public CartItemSRO(){
        super();
    }
    
    public CartItemSRO(int shippingMethodCharges, int quantity, int availableQuantity, int sdCash, int promoValue, int shippingCharges, int sellingPrice, List<String> freebie,
            String name, int instantCashBack) {
        super();
        this.shippingMethodCharges = shippingMethodCharges;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.sdCash = sdCash;
        this.promoValue = promoValue;
        this.shippingCharges = shippingCharges;
        this.sellingPrice = sellingPrice;
        this.freebie = freebie;
        this.name = name;
        this.instantCashBack = instantCashBack;
    }



    public int getShippingMethodCharges() {
        return shippingMethodCharges;
    }

    public void setShippingMethodCharges(int shippingMethodCharges) {
        this.shippingMethodCharges = shippingMethodCharges;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public int getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(int promoValue) {
        this.promoValue = promoValue;
    }

    public int getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(int shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<String> getFreebie() {
        return freebie;
    }

    public void setFreebie(List<String> freebie) {
        this.freebie = freebie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the instantCashBack
     */
    public int getInstantCashBack() {
        return instantCashBack;
    }

    /**
     * @param instantCashBack the instantCashBack to set
     */
    public void setInstantCashBack(int instantCashBack) {
        this.instantCashBack = instantCashBack;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CartItemSRO [shippingMethodCharges=" + shippingMethodCharges + ", quantity=" + quantity + ", availableQuantity=" + availableQuantity + ", sdCash=" + sdCash
                + ", promoValue=" + promoValue + ", shippingCharges=" + shippingCharges + ", sellingPrice=" + sellingPrice + ", freebie=" + freebie + ", name=" + name
                + ", instantCashBack=" + instantCashBack + "]";
    }
    
}
