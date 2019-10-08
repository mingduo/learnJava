package com.ais.brm.study.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 综合工具类
 *
 * @author lulj
 * @since 2018/03/22
 */
public class MiscUtils {
    static Logger logger = LoggerFactory.getLogger(MiscUtils.class);
    private static Gson gson = new Gson();

    //格式过date 的 gson
    private static Gson gsonPretty = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting()/*.serializeNulls()*/
            .create();


    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toPrettyJson(Object obj) {
        return gsonPretty.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 解析浮点数（无效数字不抛异常，返回0）
     *
     * @param numObj
     * @return
     */
    public static double parseDouble(Object numObj) {
        if (numObj == null) {
            return 0;
        }

        if (numObj instanceof Number) {
            return ((Number) numObj).doubleValue();
        }

        try {
            return Double.parseDouble(numObj.toString());
        } catch (NumberFormatException nfe) {
            logger.warn("试图解析无效数字: {}；已返回0！", numObj.toString());
            return 0;
        }
    }

    /**
     * 解析长整数（无效数字不抛异常，返回0）
     *
     * @param numObj
     * @return
     */
    public static long parseLong(Object numObj) {
        if (numObj == null) {
            return 0;
        }

        if (numObj instanceof Number) {
            return ((Number) numObj).longValue();
        }

        try {
            return Long.parseLong(numObj.toString());
        } catch (NumberFormatException nfe) {
            logger.warn("试图解析无效数字: {}；已返回0！", numObj.toString());
            return 0;
        }
    }

    /**
     * 解析数字值（无效数字不抛异常，返回null）
     * <p>
     * 注意：可能会返回null！
     *
     * @param numObj
     * @return
     */
    public static Number parseNumber(Object numObj) {
        if (numObj == null) {
            return null;
        }

        if (numObj instanceof Number) {
            return (Number) numObj;
        }

        try {
            String numStr = numObj.toString();
            if (numStr == null) {
                return null;
            }
            for (char ch : numStr.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    return Double.parseDouble(numStr);
                }
            }

            return Long.parseLong(numStr);
        } catch (NumberFormatException nfe) {
            logger.warn("试图解析无效数字: {}；已返回null！", numObj.toString());
            return null;
        }
    }

    public static byte[] sha256Digest(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes(StandardCharsets.UTF_8));
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("计算SHA256出错!", e);
            return null;
        }
    }


    public static String hexString(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .map(i -> bytes[i] & 0xFF)
                .mapToObj(i -> (i < 10 ? "0" : "") + Integer.toHexString(i))
                .collect(Collectors.joining());
    }


    /**
     * 释放分布式锁
     *
     * @param redis   Redis客户端
     * @param lockKey 锁
     * @param value
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(StringRedisTemplate redis, String lockKey, String value) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1])" +
                " else return 0 end";
        Integer results = redis.execute(new DefaultRedisScript<>(script, Integer.class),
                Collections.singletonList(lockKey),
                value
        );
        return results == 1 ? true : false;


    }
}
