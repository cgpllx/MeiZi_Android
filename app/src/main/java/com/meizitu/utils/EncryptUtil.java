package com.meizitu.utils;
import java.io.UnsupportedEncodingException;  
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec; 
public class EncryptUtil {
	private final static String ENCRYPTKEY="0123456789";  
    /** 
     *  
      * @Title: getEncryptKey 
      * @Description: 检验加密key  
      * @param encryptKey 
      * @return 
      * String 
      * 
     */  
    private static String getEncryptKey(String encryptKey){  
        if(null==encryptKey || "".equals(encryptKey)) return ENCRYPTKEY;  
        return encryptKey;  
    }  
      
    /** 
     *  
      * @Title: encrypt 
      * @Description: 加密:普通java字串加密成16进制字串(String -> Byte -> HexStr) 
      * @param content 要加密处理的字串 
      * @param encryptKey 加密密钥 
      * @return 
      * String 
      * 
     */  
    public static String encrypt(String content, String encryptKey){  
        byte[] encryptResult = encryptStrToByte(content, getEncryptKey(encryptKey));  
        return parseByte2HexStr(encryptResult);  
    }  
    /** 
     *  
      * @Title: decrypt 
      * @Description: 加密:16进制字串解密成普通java字串(HexStr -> Byte ->String)  
      * @param content 要解密处理的16进制字串 
      * @param encryptKey 解密密钥 
      * @return 
      * String 
      * 
     */  
    public static String decrypt(String content, String encryptKey){  
        byte[] decryptFrom = parseHexStr2Byte(content);  
        byte[] decryptResult = decrypt(decryptFrom,getEncryptKey(encryptKey));  
        return new String(decryptResult);  
    }  
    /** 
    * 加密:字串 --> 二进制 
    * @param content 需要加密的内容 
    * @param password 加密密码 
    * @return 
    */  
    private static byte[] encryptStrToByte(String content, String password) {  
        try {
            KeyGenerator kgen = null;
            try {
                kgen = KeyGenerator.getInstance("AES", "Crypto");
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            kgen.init(128, new SecureRandom(password.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            SecureRandom.getInstance("SHA1PRNG", "Crypto");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /**解密 
    * @param content 待解密内容 
    * @param password 解密密钥 
    * @return 
    */  
    private static byte[] decrypt(byte[] content, String password) {
        try {  
            KeyGenerator kgen =KeyGenerator.getInstance("AES");
            SecureRandom sr;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            } else {
                sr = SecureRandom.getInstance("SHA1PRNG");
            }
            sr.setSeed(password.getBytes());
            // 256 bits or 128 bits,192bits
            kgen.init(256, sr);
//            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
        } catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;  
    }  
      
    /**将二进制转换成16进制 
    * @param buf 
    * @return 
    */  
    private static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 将16进制转换为二进制 
     *  
     * @param hexStr 
     * @return 
     */  
    private static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length() / 2];  
        for (int i = 0; i < hexStr.length() / 2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),  
                    16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }
    public static void main(String []args)throws Exception{
        String str = "julian.zhu";
        String key = "qwertyuiopasdfghjklzxcvbnm";
        String text = SimpleCrypto.encrypt(key, str);
        System.out.println(text);
        text = SimpleCrypto.decrypt(key, text);
        System.out.println(text);
    }
}
