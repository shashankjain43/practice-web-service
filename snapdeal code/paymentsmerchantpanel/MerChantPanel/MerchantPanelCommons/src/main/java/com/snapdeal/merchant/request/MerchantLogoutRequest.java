package com.snapdeal.merchant.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantLogoutRequest extends AbstractMerchantRequest {

   private static final long serialVersionUID = 1L;

   private boolean hardLogout = false;
}
