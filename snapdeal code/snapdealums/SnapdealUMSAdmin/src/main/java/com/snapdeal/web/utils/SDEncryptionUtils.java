/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 24, 2012
 *  @author amit
 */
package com.snapdeal.web.utils;

import com.snapdeal.base.utils.MD5ChecksumUtils;

public class SDEncryptionUtils {

    private static final String MD5_SALT_PASSWORD_ENCRYPTION = "snapdealsaltforpassword123876heysaltie";

    public static String getMD5EncodedPassword(String text) {
        return MD5ChecksumUtils.md5Encode(text, MD5_SALT_PASSWORD_ENCRYPTION);
    }
}
