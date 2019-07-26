package com.ais.brm.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhaocw on 2016/6/3.
 * @author zhaocw
 */
public class StringUtils {
    /**
     * .
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s==null||s.trim().length()==0;
    }

    /**
     * .
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**check if all param is empty.
     * .
     * @param params
     * @return
     */
    public static boolean isNotEmpty(String ... params) {
        boolean foundEmpty = false;
        for(String param:params) {
            if(StringUtils.isEmpty(param)) {
                foundEmpty = true;
                break;
            }
        }
        return !foundEmpty;
    }

    /**
     * .
     * @param theMap
     * @return
     */
    public static String convertMap2String(Map<String, String> theMap) {
        if(theMap==null||theMap.size()==0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        if(theMap!=null&&theMap.size()>0) {
            Iterator<String> iter = null;
            for(iter = theMap.keySet().iterator();iter.hasNext();) {
                String key = iter.next();
                String value = theMap.get(key);
                if(StringUtils.isNotEmpty(key)) {
                    buffer.append(key+":"+value+",");
                }
            }
        }
        if(buffer.toString().endsWith(",")) {
            buffer = new StringBuffer(buffer.subSequence(0,buffer.length()-1));
        }
        buffer.append("}");
        return buffer.toString();
    }

    public static int countOccurances(String line,String sub) {
        return line.length() - line.replace(sub, "").length();
    }
    
    public static String ifNull(String s) {
    	return s == null ? "" : s;
    }

    public static String ifNull(String s, String alt) {
    	return s == null ? alt : s;
    }
    
    /**
    *根据某种编码方式将字节数组转换成字符串
    *@paramb字节数组
    *@paramoffset要转换的起始位置
    *@paramlen要转换的长度
    *@paramencoding编码方式
    *@return如果encoding不支持，返回一个缺省编码的字符串
    */
    public static String getStringByByte(byte[]b,int offset,int len,String encoding){
	    try{
	      return new String(b,offset,len,encoding);
	    }catch(UnsupportedEncodingException e){
	      return new String(b,offset,len);
	    }
    }
    
    /**
    *@paramip
    *ip的字节数组形式
    *@return字符串形式的ip
    */
    public static String getIpStringFromBytes(byte[]ip){
	    StringBuffer sb=new StringBuffer();
	    sb.append(ip[0]&0xFF);
	    sb.append('.');
	    sb.append(ip[1]&0xFF);
	    sb.append('.');
	    sb.append(ip[2]&0xFF);
	    sb.append('.');
	    sb.append(ip[3]&0xFF);
	    return sb.toString();
    }
}
