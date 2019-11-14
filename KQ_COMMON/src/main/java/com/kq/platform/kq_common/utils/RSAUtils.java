/**
 *
 */
package com.kq.platform.kq_common.utils;


import com.kq.platform.kq_common.utils.cipher.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: RSA加密算法工具类
 * @program: sso-authority-server
 * @author: 寻添俊
 * @create: 2018-08-01 09:10
 **/
public class RSAUtils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        return getPrivateString(Cipher.ENCRYPT_MODE, data, privateKey);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        return getPrivateString(Cipher.DECRYPT_MODE, data, privateKey);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        return getPublicString(Cipher.DECRYPT_MODE, data, publicKey);
    }


    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        return getPublicString(Cipher.ENCRYPT_MODE, data, publicKey);
    }

    /**
     * 公钥加密/解密私有方法
     *
     * @param model     加密解密方式
     * @param data      数据
     * @param publicKey 公钥
     * @return
     */
    private static String getPublicString(int model, String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(model, publicKey);
            return new String(rsaSplitCodec(cipher, model, Base64.decode(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("加密/机密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * 私钥加密/解密私有方法
     *
     * @param model      加密解密方式
     * @param data       数据
     * @param privateKey 私钥
     * @return
     */
    private static String getPrivateString(int model, String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(model, privateKey);
            return Base64.encode(rsaSplitCodec(cipher, model, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密/机密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        //IOUtils.closeQuietly(out);
        return resultDatas;
    }

}
