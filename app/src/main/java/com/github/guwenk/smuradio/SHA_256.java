package com.github.guwenk.smuradio;

import java.security.MessageDigest;


public class SHA_256 {
    String hashing(String password) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        /**
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Hex format : " + sb.toString());
         */

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
