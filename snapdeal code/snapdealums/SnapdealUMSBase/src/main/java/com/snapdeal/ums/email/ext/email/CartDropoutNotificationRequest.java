package com.snapdeal.ums.email.ext.email;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.CartItemSRO;
import com.snapdeal.ums.sro.email.vm.UMSPOGSRO;

public class CartDropoutNotificationRequest extends ServiceRequest {

    private static final long serialVersionUID = 7865875562585752094L;

    @Tag(3)
    private String            email;

    @Tag(4)
    private String            name;

    @Tag(5)
    private String            cartId;

    @Tag(6)
    private List<CartItemSRO> cartItemSRO      = new ArrayList<CartItemSRO>();

    @Tag(7)
    private String            catalogText;
    
    @Tag(8)
    private Set<UMSPOGSRO>   umsPOGSROs = new HashSet<UMSPOGSRO>();

    public CartDropoutNotificationRequest() {

    }

    public CartDropoutNotificationRequest(String email, String name, String cartId, List<CartItemSRO> cartItemSRO, String catalogText) {
        super();
        this.email = email;
        this.name = name;
        this.cartId = cartId;
        this.cartItemSRO = cartItemSRO;
        this.catalogText = catalogText;
    }

    public CartDropoutNotificationRequest(String email, String name, String cartId, List<CartItemSRO> cartItemSRO) {
        super();
        this.email = email;
        this.name = name;
        this.cartId = cartId;
        this.cartItemSRO = cartItemSRO;
    }

    public CartDropoutNotificationRequest(String email, String name, String cartId, List<CartItemSRO> cartItemSRO, Set<UMSPOGSRO> umsPOGSROs) {
        super();
        this.email = email;
        this.name = name;
        this.cartId = cartId;
        this.cartItemSRO = cartItemSRO;
        this.umsPOGSROs=umsPOGSROs;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<CartItemSRO> getCartItemSRO() {
        return cartItemSRO;
    }

    public void setCartItemSRO(List<CartItemSRO> cartItemSRO) {
        this.cartItemSRO = cartItemSRO;
    }

    public void setCatalogText(String catalogText) {
        this.catalogText = catalogText;
    }

    public String getCatalogText() {
        return catalogText;
    }

    /**
     * @return the umsPOGSROs
     */
    public Set<UMSPOGSRO> getUmsPOGSROs() {
        return umsPOGSROs;
    }

    /**
     * @param umsPOGSROs the umsPOGSROs to set
     */
    public void setUmsPOGSROs(Set<UMSPOGSRO> umsPOGSROs) {
        this.umsPOGSROs = umsPOGSROs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CartDropoutNotificationRequest [email=" + email + ", name=" + name + ", cartId=" + cartId + ", cartItemSRO=" + cartItemSRO.size() + ", catalogText=" + catalogText
                + ", umsPOGSROs=" + umsPOGSROs.size() + "]";
    }

    
}
