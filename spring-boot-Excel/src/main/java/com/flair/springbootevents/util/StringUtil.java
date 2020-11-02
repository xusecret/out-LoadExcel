package com.flair.springbootevents.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public final class StringUtil {

    /**
     * Check if a string is empty
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        return s == null || s.trim().length() == 0;
    }

    /**
     * Encrypt string s with MD5.
     * @param s
     * @return
     */
    public static String encodeMD5(String s){
        if(isEmpty(s)){
            return null;
        }
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException ex) {
            //ignore ex
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        md.update(s.getBytes());
        byte[] datas = md.digest();
        int len = datas.length;
        char str[] = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = datas[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    public static String getArgument(String name, String[] args){
        if(args==null){
            return null;
        }
        for(int i=0; i<args.length; i++){
            String e = args[i];
            String[] ss = e.split("=");
            if(ss.length != 2){
                return null;
            }
            if(ss[0].equals(name)){
                return ss[1];
            }
        }
        return null;
    }

    public static String createRandomAesKey(){
        String src = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        int len = src.length();
        for(int i=0; i<16; i++){
            Random r = new Random();
            int index = r.nextInt(len);
            sb.append(src.charAt(index));
        }
        return sb.toString();
    }

}
