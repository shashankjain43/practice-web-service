/*
 * Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @version 1.0, Sep 18, 2010
 * 
 * @author singla
 */
package com.snapdeal.ums.utils;

import java.security.spec.KeySpec;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtils
{

    private static final int ENCRPYT_DB_STRING_GARBAGE_PREFIX_COUNT = 3;

    private static final int ENCRPYT_DB_STRING_GARBAGE_POSTFIX_COUNT = 5;

    public static final String ENCRYPTION_SCHEME = "DESede";

    public static final String ENCRYPTION_KEY = "SNAPDEALUMS ENCRYPTION KEYX";

    private static final String UNICODE_FORMAT = "UTF8";

    private static KeySpec keySpec;

    private static SecretKeyFactory keyFactory;

    static {
        try {
            byte[] keyAsBytes = ENCRYPTION_KEY.getBytes(UNICODE_FORMAT);
            keySpec = new DESedeKeySpec(keyAsBytes);
            keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_SCHEME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String unencryptedString)
    {

        if (unencryptedString == null || unencryptedString.trim().length() == 0)
            return unencryptedString;

        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher;
            cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] ciphertext = cipher.doFinal(cleartext);
            return new String(Base64.encodeBase64(ciphertext), UNICODE_FORMAT);
        }
        catch (Exception e) {
            // Unable to encrypt
            return unencryptedString;
        }
    }

    public static String encrypt(int unencryptedInt)
    {

        return encrypt(String.valueOf(unencryptedInt));
    }

    public static String decrypt(String encryptedString)
    {

        if (encryptedString == null || encryptedString.trim().length() <= 0)
            return encryptedString;

        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher;
            cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] cleartext = Base64.decodeBase64(encryptedString.getBytes(UNICODE_FORMAT));
            byte[] ciphertext = cipher.doFinal(cleartext);

            return new String(ciphertext, UNICODE_FORMAT);
        }
        catch (Exception e) {
            // Unable to decrypt
            return encryptedString;
        }
    }

    /**
     * Generates a random string which can be used as a verification code
     * perhaps!
     * 
     * @return
     */
    public static String generateRandomString()
    {

        return UUID.randomUUID().toString().replaceAll("-", "");

    }

    public static void main(String[] args)
    {

        //
        String emailID = "shashank.jain#snapdeal.com";
        String encryptedEmailID = encrypt(emailID);
        
        String verificationCode = "assssekjash";
        String encryptedVerCode = encrypt(verificationCode);
        String dbEncryptedCode = encrpytionLevel2(verificationCode);
        
        String link = encryptedEmailID+"@@@"+encryptedVerCode;
        
        System.out.println("emailID: "+emailID+"\nLink: "+link+"\nDecrypted db ver: "+ dbEncryptedCode);
        
        
        
        // String code2 = "abc" + "qwer" + "xyz";
        // System.out.println("code:" + code);
        // System.out.println("code:" + code2);
        //
        // String l1E = encrypt(code);
        // System.out.println("l1E:" + l1E);
        //
        // String l1E2 = encrypt(code2);
        // System.out.println("l1E2:" + l1E2);
        //
        // String de = decrypt(l1E2);
        // System.out.println(de);
        // ;
        // System.out.println(de.substring(3, de.length() - 3));
        //
        // System.out.println("-------------");
        //
        // System.out.println(encrpytionLevel2(code));
        // System.out.println(encrypt(code));
        // System.out.println(decrpytionLevel2ToLevel1(encrpytionLevel2(code)));
        // System.out.println("-------------");
//
//        System.out.println(code);
//        System.out.println("Encrypted L1: " + encrypt(code));
//        System.out.println(decrypt(encrypt(code)));
//        System.out.println(encrpytionLevel2(code));
//
//        System.out.println("Formulated Encrypted L1: " + decrpytionLevel2ToLevel1(encrpytionLevel2(code)));
//
//        
//        System.out.println(decrypt("DMs8AIQiyS0="));
        //
        //
        // String l2E = encrpyt_level1(l1E);
        // System.out.println("l2E:"+l2E);
        //
        // String d2E = decrpyt_level2(l2E);
        // System.out.println("d2E:"+d2E);
        //
        // String d1E = decrpyt_level2(d2E);
        // System.out.println("d1E:"+d1E);
        // System.out.println(l1E.equals(d2E));
        //
        //
        //
        // String dec = encrypt("56655342");
        // System.out.println("encrpted: " + dec);
        //
        // System.out.println("decrpyted: " + decrypt(dec));
        //
        // String o1 = "ashish.saxena@snapdeal.com";
        // System.out.println("O1: " + o1);
        // String e1 = EncryptionUtils.encrypt(o1);
        // System.out.println("e1: " + e1);
        //
        // String e2 = EncryptionUtils.encrypt(e1);
        //
        // System.out.println("PARAm as in email - e2: " + e2);
        //
        // String gs = appendGarbageValues(e2);
        // System.out.println("garbage added: " + gs);
        //
        // String undoGS = removeGarbageValues(gs);
        //
        // System.out.println("Undo garbage: " + undoGS);
        //
        // String d1 = EncryptionUtils.decrypt(undoGS);
        // System.out.println("D1: " + d1);
        //
        // String d2 = EncryptionUtils.decrypt(d1);
        // System.out.println("D2: " + d2);
    }

    //
    // public static String encrpyt_level1(String s)
    // {
    //
    // if (UMSStringUtils.isNullOrEmpty(s)) {
    // return null;
    // }
    // return encrypt(s);
    // }
    //
    // public static String decrpyt_level1(String s)
    // {
    //
    // String val = null;
    //
    // if (UMSStringUtils.isNullOrEmpty(s)) {
    // return null;
    // }
    // return decrypt(val);
    // }

    public static String encrpytionLevel2(String s)
    {

        if (UMSStringUtils.isNullOrEmpty(s)) {
            return null;
        }

        String tmp = appendGarbageValues(s);
        return encrypt(tmp);
    }

    public static String decrpytionLevel2(String s)
    {

        if (UMSStringUtils.isNullOrEmpty(s)) {
            return null;
        }
        String tmp = decrypt(s);
        return removeGarbageValues(tmp);
    }

    /**
     * From a level2 de-crypted String, it formulates the same string as returned
     * by encrypt() method. Used in cases when two level of encryptions are
     * required!
     * 
     * @param s
     * @return
     */
    public static String decrpytionLevel2ToLevel1(String s)
    {

        if (UMSStringUtils.isNullOrEmpty(s)) {
            return null;
        }
        return encrypt(decrpytionLevel2(s));
    }

    // public static String decrypt_level2_

    /**
     * Prefixes and post fixes passed non-null String value with garbage Strings
     * 
     * @param value
     * @return
     */
    public static String appendGarbageValues(String value)
    {

        if (UMSStringUtils.isNullOrEmpty(value)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ENCRPYT_DB_STRING_GARBAGE_PREFIX_COUNT; i++) {
            builder.append(UMSStringUtils.getRandomChar());
        }
        builder.append(value);
        for (int i = 0; i < ENCRPYT_DB_STRING_GARBAGE_POSTFIX_COUNT; i++) {
            builder.append(UMSStringUtils.getRandomChar());
        }

        return builder.toString();
    }

    /**
     * Removes Prefixed and post fixed garbage values (added via
     * LoyaltyUserManager#appendGarbageValues())from the passed non-null String
     * value
     * 
     * @param value
     * @return
     */
    public static String removeGarbageValues(String value)
    {

        if (UMSStringUtils.isNullOrEmpty(value)) {
            return null;
        }

        if (value.length() < (ENCRPYT_DB_STRING_GARBAGE_PREFIX_COUNT + ENCRPYT_DB_STRING_GARBAGE_POSTFIX_COUNT))
        {
            throw new IllegalArgumentException("String paramater not generated by current handler!");
        }

        return value.substring(ENCRPYT_DB_STRING_GARBAGE_PREFIX_COUNT,
            (value.length() - ENCRPYT_DB_STRING_GARBAGE_POSTFIX_COUNT));
    }


    
    

}