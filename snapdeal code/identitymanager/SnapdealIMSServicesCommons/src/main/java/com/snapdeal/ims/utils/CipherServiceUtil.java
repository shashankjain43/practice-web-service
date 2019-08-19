package com.snapdeal.ims.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.InternalServerException;

/**
 * Utility class to encrypt and decrypt.
 *
 */
public class CipherServiceUtil {
    
    private static String UNIQUE_KEY;

    private static final String KEY_ALGORITHM_V1 = "AES";

    private static final String PASSWORD_HASH_ALGORITHM = "SHA-256";

    private static Cipher encryptCipher;
    private static Cipher decryptCipher;
    
    private CipherServiceUtil(){}

    
    static {
        try {
            UNIQUE_KEY = Configuration.getCipherKey();
            Key secretKeyV1 = buildKeyAES(UNIQUE_KEY, KEY_ALGORITHM_V1);
            encryptCipher = Cipher.getInstance(secretKeyV1.getAlgorithm());
            decryptCipher = Cipher.getInstance(secretKeyV1.getAlgorithm());
            // Set the mode for encrypt and decrypt.
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeyV1);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKeyV1);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException
                 e) {
            throw new InternalServerException(
                    IMSInternalServerExceptionCodes.CIPHER_ERROR.errCode(),
                    IMSInternalServerExceptionCodes.CIPHER_ERROR.errMsg(), e);
        } 
    }

    private static Key buildKeyAES(String password, String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digester = MessageDigest
                .getInstance(PASSWORD_HASH_ALGORITHM);
        digester.reset();
        digester.update(password.getBytes("UTF-8"));
        byte[] key = digester.digest();
        SecretKeySpec spec = new SecretKeySpec(key,0,16, algorithm);
        return spec;
    }

    public static String decrypt(String token) throws CipherException {
        byte[] decodedToken = org.apache.commons.codec.binary.Base64.decodeBase64(token);
        byte[] decryptedToken = null;
        try {
            decryptedToken = decryptCipher.doFinal(decodedToken);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new CipherException(IMSInternalServerExceptionCodes.DECRTYPTION_ERROR.errMsg());
        }
        String textDecryptped = new String(decryptedToken);
        return textDecryptped;
    }

    public static String encrypt(String uniqueId) throws CipherException {
        byte[] text = uniqueId.getBytes();
        byte[] textEncrypted;
        try {
            textEncrypted = encryptCipher.doFinal(text);
            byte[] token =org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(textEncrypted);
            return new String(token);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new CipherException(IMSInternalServerExceptionCodes.ENCRYPTION_ERROR.errMsg());
        }
    }

    /**
     * Test method.
     * 
     * @param args
     * @throws CipherException 
     */
    public static void main(String[] args) throws CipherException {
        String data = "SnapdealUniqueKey";
        String eVal = CipherServiceUtil.encrypt(data);
        String dVal = CipherServiceUtil.decrypt("ot39X_1NhtZ5ldclhkZWBvv9yePaNG9q0DQFfQdGqX-QvmSwHe5pf4s0YP4cInR6xtIq_CyRl9yNTRHfn9k2wbRP0XMqUErrcJLEHr7MRpKi7eiknPJG7URe1WweJED5");
        System.out.println(new Date(1451283780601L));
        /* dVal = CipherServiceUtil.decrypt("ot39X_1NhtZ5ldclhkZWBvv9yePaNG9q0DQFfQdGqX-QvmSwHe5pf4s0YP4cInR6xtIq_CyRl9yNTRHfn9k2wbRP0XMqUErrcJLEHr7MRpKi7eiknPJG7URe1WweJED5");
        System.out.println(new Date(145128378060L));
        
         dVal = CipherServiceUtil.decrypt("ot39X_1NhtZ5ldclhkZWBiALv8T1Xe4HW3bfX3UYQ5xw5e70E_p_nlDmd4mJrtkqyljjIyu7ugeMzSvzFNXq3AI1K09SBGJVAho19Jt9WASi7eiknPJG7URe1WweJED5");
        System.out.println(new Date(1451283598739L));
        
         dVal = CipherServiceUtil.decrypt("ot39X_1NhtZ5ldclhkZWBiALv8T1Xe4HW3bfX3UYQ5xw5e70E_p_nlDmd4mJrtkqyljjIyu7ugeMzSvzFNXq3AI1K09SBGJVAho19Jt9WASi7eiknPJG7URe1WweJED5");
        System.out.println(new Date(1451283598739L));
        
         dVal = CipherServiceUtil.decrypt("ot39X_1NhtZ5ldclhkZWBunQMgUu32hGR5FPRM-z4p-fyE1oz5ybI-Kn_EdXIOSMtLyXfo557uUDMmMiThYvtOLFJuh9DCWjLMnJ3T0B7pSi7eiknPJG7URe1WweJED5");
        System.out.println(new Date(1451283705151L));
        
        System.out.println(new Date(1451283673243L));
        byte[] de = Base64Utils.encode(data.getBytes());
        String en = new String(Base64Utils.decode(de));
        System.out.println(Base64Utils.encodeToString(data.getBytes()));
        System.out.println(en.equals(data) ? "success" : "fail");*/
    }

}
