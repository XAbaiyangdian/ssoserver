package com.xaicif.sso.common;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtils {
    public static String sha256Hex(String str){
        return DigestUtils.sha256Hex(str);
    }
    public static String md5Hex(String str){
        return DigestUtils.md5Hex(str);
    }

}
