package com.example.protected_root;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import android.util.Base64;
//http://www.jlancer.net/board/article_view.jsp?article_no=501&board_no=22
public class AES256Chiper {
    public String AES_Encode(String strKey){
        if(strKey==null||strKey.length()==0){
            return "";
        }
        int iLen = strKey.length();
        String strEncryption ="";
        for(int iCnt=0;iCnt<iLen;iCnt++){
            //암호화 (문자열의 짝수, 홀수 위치 구분)
            if(iCnt%2==0){  //홀수문자
                strEncryption +=  (char) ((byte) strKey.charAt(iCnt) - iLen + iCnt);
            }else{//짝수문자
                strEncryption +=  (char) ((byte) strKey.charAt(iCnt) + iLen + iCnt);
            }
        }
        return strEncryption;
    }

    public String AES_Decode(String strKey){
        if(strKey==null||strKey.length()==0){
            return "";
        }
        int iLen = strKey.length();
        String strDecryption ="";
        for(int iCnt=0;iCnt<iLen;iCnt++){
            //암호화 (문자열의 짝수, 홀수 위치 구분)
            if(iCnt%2==0){  //홀수문자
                strDecryption +=  (char) ((byte) strKey.charAt(iCnt) + iLen - iCnt);
            }else{//짝수문자
                strDecryption +=  (char) ((byte) strKey.charAt(iCnt) - iLen - iCnt);
            }
        }
        return strDecryption;
    }

//    public static void main(String[] args){
//        String key ="abc$%";
//        Encryption e = new Encryption();
//        System.out.println(key);
//        System.out.println(e.encryptionPassword(key));
//        System.out.println(e.decryptionPassword(e.encryptionPassword(key)));
//    }
/*
    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    public static String secretKey = "a";
    public void AES256Chiper(){};
    //AES256 암호화
    public static String AES_Encode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,    IllegalBlockSizeException, BadPaddingException {

        byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

        return Base64.encodeToString(cipher.doFinal(textBytes), 0);
    }

    //AES256 복호화
    public static String AES_Decode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        byte[] textBytes =Base64.decode(str,0);
        //byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), "UTF-8");
    }*/
}


//출처: https://niceman.tistory.com/91