package com.itcast.actviti7demo.utils;

import org.springframework.util.DigestUtils;

import java.io.*;

public class MD5Util {
    static String MD5 = "MD5";
    static String UTF8 = "UTF-8";

    public static String MD5File(String filePath) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            String md5 = DigestUtils.md5DigestAsHex(inputStream);
            return md5;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String MD5File(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            String md5 = DigestUtils.md5DigestAsHex(inputStream);
            return md5;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String MD5File(InputStream fileInputStream){
        try {
            String md5 = DigestUtils.md5DigestAsHex(fileInputStream);
            return md5;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String MD5String(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String MD5StringU8(String str){
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes(UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String MD5Bytes(byte[] bytes){
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
