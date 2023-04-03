package com.xaicif.sso.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {

    public static String sign(Map map, String secretKey) {
        String linkString = createLinkString(map);
        linkString += "&secretKey=" + secretKey;
        return EncryptUtils.sha256Hex(linkString).toUpperCase();
    }

    public static Boolean checkSign(Map map, String signature, String secretKey) {
        String linkString = createLinkString(map);
        linkString += "&secretKey=" + secretKey;
        if (EncryptUtils.sha256Hex(linkString).toUpperCase().equals(signature)) {
            return true;
        }
        System.out.println("checkSign: " + linkString);
        return false;
    }
    
    public static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuffer buffer = new StringBuffer();
        for (String key : keys) {
            Object value = params.get(key);
            if (!keys.get(0).equals(key)) {
                buffer.append("&");
            }
            buffer.append(key + "=" + value);
        }
        return buffer.toString();
    }
}
