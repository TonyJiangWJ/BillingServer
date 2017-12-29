package com.tony.billing.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author jiangwj20966 on 2017/11/27.
 */
public class RSAUtil {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSAUtil(String filePath) throws Exception {
        publicKey = RSAEncrypt.loadPublicKeyByByteArray(RSAEncrypt.loadPublicKeyByFile(filePath));
        privateKey = RSAEncrypt.loadPrivateKeyByByteArray(RSAEncrypt.loadPrivateKeyByFile(filePath));
    }

    public RSAUtil(String publicBase64Str, String privateBase64Str) throws Exception {
        publicKey = RSAEncrypt.loadPublicKeyByByteArray(Base64.decodeBase64(publicBase64Str));
        privateKey = RSAEncrypt.loadPrivateKeyByByteArray(Base64.decodeBase64(privateBase64Str));
    }

    private final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    public String decrypt(String cipher) {
        try {
            byte[] cipherArray = RSAEncrypt.decrypt(privateKey, Base64.decodeBase64(cipher));
            if (cipherArray != null) {
                String cipherStr = new String(cipherArray, 0, cipherArray.length);
                if (cipherStr.length() > 13) {
                    Long timestamp = Long.valueOf(cipherStr.substring(0, 13));
                    if (System.currentTimeMillis() - timestamp < 30 * 1000) {
                        return cipherStr.substring(13);
                    } else {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        logger.error("验证信息超时：{}", simpleDateFormat.format(new Date(timestamp)));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("解析rsa加密内容失败 ", e);
        }
        return null;
    }

    public String encrypt(String content) {
        if (content != null) {
            long timestamp = System.currentTimeMillis();
            content = timestamp + content;
            try {
                return Base64.encodeBase64String(RSAEncrypt.encrypt(privateKey, content.getBytes()));
            } catch (Exception e) {
                logger.error("rsa加密失败 ", e);
            }
        }
        return null;
    }
}
