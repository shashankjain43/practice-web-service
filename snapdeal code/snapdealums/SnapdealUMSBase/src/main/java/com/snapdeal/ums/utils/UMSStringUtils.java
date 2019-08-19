/*
 * Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @version 1.0, 19-Dec-2013
 * 
 * @author ghanshyam
 */
package com.snapdeal.ums.utils;

import java.util.Random;

import com.snapdeal.base.utils.StringUtils;

public class UMSStringUtils extends StringUtils
{

    public static String trim(String input)
    {

        if (input == null)
            return null;

        return input.trim();
    }

    public static boolean isNotNullNotEmpty(String stringValue)
    {

        boolean result = false;
        if (stringValue != null && !stringValue.trim().equals(""))
        {
            result = true;
        }
        return result;
    }

    public static boolean isNullOrEmpty(String string)
    {

        return !isNotNullNotEmpty(string);
    }

    /**
     * Returns true if both strings in request are NOT NULL, NOT EMPTY and EQUAL
     * without trimming.
     * 
     * @param string1
     * @param string2
     * @return
     */
    public static boolean areEqualNotEmptyNotNull(String string1, String string2)
    {

        boolean result = false;

        if (isNotNullNotEmpty(string1) && isNotEmpty(string2) && string1.equals(string2)) {
            result = true;
        }

        return result;

    }

    public static String generateRandomCode(int length)
    {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            stringBuffer.append(getRandomChar());
        }

        return stringBuffer.toString();

    }

    public static char getRandomChar()
    {

        Random r = new Random();
        char randomChar = (char) (48 + r.nextInt(47));
        return randomChar;
    }
}
