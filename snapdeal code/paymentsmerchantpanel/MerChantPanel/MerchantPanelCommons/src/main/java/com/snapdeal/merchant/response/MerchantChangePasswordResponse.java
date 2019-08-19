package com.snapdeal.merchant.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantChangePasswordResponse extends AbstractResponse {

   private static final long serialVersionUID = 6856336241190999078L;

   private boolean success;
}
