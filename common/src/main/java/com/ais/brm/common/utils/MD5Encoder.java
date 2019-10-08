package com.ais.brm.common.utils;

import java.security.MessageDigest;

/**
 * @author renjb
 * @version 1.0.0, 2008-9-19
 * @since 1.0
 */
public class MD5Encoder {

    private static MessageDigest mDigest = null;
    private static MD5Encoder instance = null;

    /**
     * ��ȡʵ��.
     *
     * @return MD5Encoder instance
     * @throws Exception Exception
     */
    public static MD5Encoder getInstance() throws Exception {
        if (instance == null) {
            instance = new MD5Encoder();
        }
        return instance;
    }

    /**
     * ˽�еĹ��췽��.
     *
     * @throws Exception Exception
     */
    private MD5Encoder() throws Exception {
        mDigest = MessageDigest.getInstance("MD5");
    }

    /**
     * �����ַ��MD5ֵ.
     *
     * @param s String
     * @return md5 of the string
     * @throws Exception Exception
     */
    public String md5Encode(String s) throws Exception {

        byte[] strTemp = s.getBytes();
        mDigest.update(strTemp);
        byte[] md = mDigest.digest();
        return (new BASE64Encoder()).encode(md);
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("MD5 �����㷨...");
        try {
            System.out.println("md5_base64: " +
                    MD5Encoder.getInstance().md5Encode("123475969708-993832123475969708-993832"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/**
 * @author zhaocw.
 */
class BASE64Encoder {

    private static char[] codecTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '+', '/'};

    /**
     *
     */
    public BASE64Encoder() {

    }

    /**
     * @param a
     * @return
     */
    public String encode(byte[] a) {
        int totalBits = a.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;// process bits
        StringBuffer toReturn = new StringBuffer();
        while (curPos < totalBits) {
            int bytePos = curPos / 8;
            switch (curPos % 8) {
                case 0:
                    toReturn.append(codecTable[(a[bytePos] & 0xfc) >> 2]);
                    break;
                case 2:

                    toReturn.append(codecTable[(a[bytePos] & 0x3f)]);
                    break;
                case 4:
                    if (bytePos == a.length - 1) {
                        toReturn
                                .append(codecTable[((a[bytePos] & 0x0f) << 2) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
                        toReturn.append(codecTable[pos]);
                    }
                    break;
                case 6:
                    if (bytePos == a.length - 1) {
                        toReturn
                                .append(codecTable[((a[bytePos] & 0x03) << 4) & 0x3f]);
                    } else {
                        int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
                        toReturn.append(codecTable[pos]);
                    }
                    break;
                default:
                    //never hanppen
                    break;
            }
            curPos += 6;
        }
        if (nn == 2) {
            toReturn.append("==");
        } else if (nn == 4) {
            toReturn.append("=");
        }
        return toReturn.toString();

    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        BASE64Encoder encoder = new BASE64Encoder();
        sun.misc.BASE64Encoder sunEncoder = new sun.misc.BASE64Encoder();
        byte[] testBytes = "hello".getBytes();
        long start = System.currentTimeMillis();

        System.out.println(sunEncoder.encode(testBytes));


        System.out.println("[sun encoder]use time :" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        System.out.println(encoder.encode(testBytes));


        System.out.println("[our encoder]use time :" + (System.currentTimeMillis() - start));

    }

}
