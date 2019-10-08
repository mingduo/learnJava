package com.ais.brm.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

/**
 * 加解密工具类.
 * Created by xuechen on 2016-10-13.
 *
 * @author xuechen
 */
public class EncryptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);

    private static EncryptUtil epu_ = new EncryptUtil();
    public static int _DES = 1;
    public static int _DESede = 2;
    public static int _Blowfish = 3;
    private static String hexKey = "B5584A5D9B61C23BE52CA1168C9110894C4FE9ABC8E9F251";
    private Cipher p_Cipher;
    private SecretKey p_Key;
    private String p_Algorithm;

    /**
     * @return
     * @description TODO
     */
    public static EncryptUtil getInstance() {
        return epu_;
    }

    private EncryptUtil() {
    }

    /**
     * @param data
     * @return
     * @throws Exception
     * @throws
     * @description 解密
     * @see
     */
    public String decryptBASE64(String data) {
        try {
            byte[] b = Base64.decodeBase64(data);
            return new String(b, "utf-8");
        } catch (Exception e) {
            LOGGER.error("BASE64解密失败", e);
        }
        return null;
    }

    /**
     * @param data
     * @return
     * @throws Exception
     * @throws
     * @description BASE64加密
     * @see
     */
    public String encryptBASE64(String data) {
        try {
            byte[] b = Base64.encodeBase64(data.getBytes("utf-8"));
            return new String(b, "utf-8");
        } catch (Exception e) {
            LOGGER.error("BASE64加密失败", e);
        }
        return null;
    }

    /**
     * @param s 要加密的字符串
     * @return
     * @throws
     * @description DES加密
     * @see
     */
    public String encryptDES(String s) {
        String hexenc = null;
        try {
            selectAlgorithm(_DESede);
            String jdkvs = System.getProperty("java.vm.vendor");
            if (null != jdkvs && jdkvs.startsWith("IBM")) {
                Security.addProvider((Provider)
                        Class.forName("com.ibm.crypto.provider.IBMJCE").newInstance());
            } else {
                Security.addProvider((Provider)
                        Class.forName("com.sun.crypto.provider.SunJCE").newInstance());
            }
            this.p_Cipher = Cipher.getInstance(this.p_Algorithm);
            setKey(hex2byte(hexKey));
            byte[] enc = encode(s.getBytes());
            hexenc = byte2hex(enc);
        } catch (Exception e) {
            LOGGER.error("DES加密失败", e);
        }
        return hexenc;
    }

    /**
     * @param s 要解密的字符串
     * @return
     * @throws
     * @description des解密
     * @see
     */
    public String decryptDES(String s) {
        String ret = null;
        try {
            selectAlgorithm(_DESede);
            String jdkvs = System.getProperty("java.vm.vendor");
            if (null != jdkvs && jdkvs.startsWith("IBM")) {
                Security.addProvider((Provider)
                        Class.forName("com.ibm.crypto.provider.IBMJCE").newInstance());
            } else {
                Security.addProvider((Provider)
                        Class.forName("com.sun.crypto.provider.SunJCE").newInstance());
            }
            this.p_Cipher = Cipher.getInstance(this.p_Algorithm);
            ret = new String(decode(hex2byte(s), hex2byte(hexKey)));
        } catch (Exception e) {
            LOGGER.error("DES解密失败", e);
        }
        return ret;
    }

    /**
     * @param str
     * @return
     * @description TODO
     */
    public String encryptMD5(String str) {
        StringBuffer md5StrBuff = new StringBuffer();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (Exception e) {
            LOGGER.error("MD5加密失败", e);
        }
        return md5StrBuff.toString().toUpperCase();
    }

    /**
     * @param str
     * @return
     * @description TODO
     */
    public String encryptSHA256(String str) {
        MessageDigest messageDigest = null;
        byte[] hash = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            hash = messageDigest.digest(str.getBytes("UTF-8"));
        } catch (Exception e) {
            LOGGER.error("SHA256加密失败", e);
        }
        return Hex.encodeHexString(hash);
    }

    /**
     * @param ssoToken
     * @return
     * @description TODO
     */
    public String encryptSX(String ssoToken) {
        try {
            byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
            String name = new String();
            for (int i = 0; i < _ssoToken.length; i++) {
                int asc = _ssoToken[i];
                _ssoToken[i] = (byte) (asc + 27);
                name = name + (asc + 27) + "%";
            }
            return name;
        } catch (Exception e) {
            LOGGER.error("加密失败", e);
            return null;
        }
    }

    /**
     * @param ssoToken
     * @return
     * @description TODO
     */
    public String decryptSX(String ssoToken) {
        try {
            String name = new String();
            java.util.StringTokenizer st = new java.util.StringTokenizer(ssoToken, "%");
            while (st.hasMoreElements()) {
                int asc = Integer.parseInt((String) st.nextElement()) - 27;
                name = name + (char) asc;
            }
            return name;
        } catch (Exception e) {
            LOGGER.error("解密失败", e);
            return null;
        }
    }

    /**
     * @param al
     * @description TODO
     */
    private void selectAlgorithm(int al) {
        switch (al) {
            case 1:
            default:
                this.p_Algorithm = "DES";
                break;
            case 2:
                this.p_Algorithm = "DESede";
                break;
            case 3:
                this.p_Algorithm = "Blowfish";
        }
    }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     * @description TODO
     */
    private SecretKey checkKey() throws NoSuchAlgorithmException {
        if (this.p_Key == null) {
            KeyGenerator keygen = KeyGenerator.getInstance(this.p_Algorithm);
            this.p_Key = keygen.generateKey();
        }
        return this.p_Key;
    }

    /**
     * @param enckey
     * @description TODO
     */
    private void setKey(byte[] enckey) {
        this.p_Key = new SecretKeySpec(enckey, this.p_Algorithm);
    }

    /**
     * @param data
     * @return
     * @throws Exception
     * @description TODO
     */
    private byte[] encode(byte[] data) throws Exception {
        this.p_Cipher.init(1, checkKey());
        return this.p_Cipher.doFinal(data);
    }

    /**
     * @param encdata
     * @param enckey
     * @return
     * @throws Exception
     * @description TODO
     */
    private byte[] decode(byte[] encdata, byte[] enckey) throws Exception {
        setKey(enckey);
        this.p_Cipher.init(2, this.p_Key);
        return this.p_Cipher.doFinal(encdata);
    }

    /**
     * @param b
     * @return
     * @description TODO
     */
    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * @param hex
     * @return
     * @throws IllegalArgumentException
     * @description TODO
     */
    private byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            LOGGER.debug("hex:" + hex + "\nlength:" + hex.length());
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        int i = 0;
        int j = 0;
        for (int l = hex.length(); i < l; j++) {
            String swap = "" + arr[(i++)] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
            i++;
        }
        return b;
    }
}
