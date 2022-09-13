package com.xaicif.sso.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class EncryptUtils {
    public static String encodeSHA256(String str){
        return encrypt(str,"SHA-256");
    }

    private static String encrypt(String src,String type){
        if(src==null){
            return null;
        }
        MessageDigest md=null;
        String result=null;
        byte[] b=src.getBytes();
        try {
            md=MessageDigest.getInstance(type);
            md.update(b);

            result=new BigInteger(1,md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    private EncryptUtils(){}

}
