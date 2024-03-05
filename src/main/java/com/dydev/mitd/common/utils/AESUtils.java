package com.dydev.mitd.common.utils;

import com.dydev.mitd.config.AES256Config;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class AESUtils {

    private AESUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ALGORITHM = "AES";
    private static final String ALG = "AES/CBC/PKCS5Padding";
    private static final String KEY = AES256Config.getAES256_KEY();
    private static final String IV = KEY.substring(0, 16); // 16byte

    public static String encrypt(String text) {
        String encryptedStr = null;
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(IV.getBytes(), ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(UTF_8));
            encryptedStr = Base64.getUrlEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            ErrorLogUtils.printError(log, e);
        }
        return encryptedStr;
    }

    public static String decrypt(String cipherText) {
        String decryptedStr = null;
        try {
            Cipher cipher = Cipher.getInstance(ALG);
            SecretKeySpec keySpec = new SecretKeySpec(IV.getBytes(), ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getUrlDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            decryptedStr = new String(decrypted, UTF_8);
        } catch (Exception e) {
            ErrorLogUtils.printError(log, e);
        }
        return decryptedStr;
    }

}
