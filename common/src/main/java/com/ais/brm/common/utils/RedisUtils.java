package com.ais.brm.common.utils;

import com.ais.brm.common.domain.IRedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Slowlog;

import java.util.List;

import static org.apache.commons.lang.StringUtils.defaultIfEmpty;


/**
 * 在没有spring的环境使用redis.
 * Created by zhaocw on 2016/6/7.
 * @author zhaocw
 */
public final class RedisUtils {
    private static Logger log = LoggerFactory.getLogger(RedisUtils.class);
    private String redisHost;
    private String redisPort;
    private String redisPassword;
    
    private JedisPool pool = null;
    private Object lock = new Object();

    public RedisUtils(IRedisConfig redisConfig) {
        this.redisHost = redisConfig.getRedisHost();
        this.redisPort = redisConfig.getRedisPort();
        this.redisPassword = redisConfig.getRedisPassword();
    }

    public RedisUtils(String redisHost,String redisPort) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }


    private void setPool(JedisPool pool) {
        this.pool = pool;
    }

    private void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //config.setMaxActive(Integer.parseInt(maxActive));
        config.setMaxIdle(400);
        config.setMaxTotal(1000);
        config.setMinIdle(1);
        //config.setMaxWait(Long.parseLong(maxWait));
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        
        setPool(new JedisPool(config, redisHost, Integer.parseInt(redisPort), Protocol.DEFAULT_TIMEOUT,
        		defaultIfEmpty(redisPassword, null), Protocol.DEFAULT_DATABASE, null));
    }

    /**
     * .
     * @return
     */
    public  Jedis getJedis() {
        if(pool ==null) {
            synchronized (lock) {
                if(pool == null) {
                    initialPool();
                }
            }
        }
        return pool.getResource();
    }



    /**
     * <table border="1">
     * <tr><th>@Description:获取redis 服务器信息</th></tr>
     * <tr><td>@Date:Created in 2018-1-5</td></tr>
     * <tr><td>@param:[]</td></tr>
     * <tr><td>@return java.lang.String</td></tr>
     * <tr><td>@Author:weizc</td></tr>
     * </table>
     *
     *
     */
    public  String getRedisInfo() {

        Jedis jedis = getJedis();
        try {
            Client client = jedis.getClient();
            client.info();
            String info = client.getBulkReply();
            return info;
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }


    /**
     * <table border="1">
     * <tr><th>@Description:获取日志列表</th></tr>
     * <tr><td>@Date:Created in 2018-1-5</td></tr>
     * <tr><td>@param:[entries]</td></tr>
     * <tr><td>@return java.util.List<redis.clients.util.Slowlog></td></tr>
     * <tr><td>@Author:weizc</td></tr>
     * </table>
     */
    public List<Slowlog> getLogs(long entries) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            List<Slowlog> logList = jedis.slowlogGet(entries);
            return logList;
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    /**
     * <table border="1">
     * <tr><th>@Description:获取占用内存大小</th></tr>
     * <tr><td>@Date:Created in 2018-1-5</td></tr>
     * <tr><td>@param:</td></tr>
     * <tr><td>@return </td></tr>
     * <tr><td>@Author:weizc</td></tr>
     * </table>
     */
    public Long dbSize() {
        Jedis jedis = getJedis();
        try {
            // TODO 配置redis服务信息
            Client client = jedis.getClient();
            client.dbSize();
            return client.getIntegerReply();
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }

    public Long getLogsLen() {
        Jedis jedis = getJedis();
        try {
            long logLen = jedis.slowlogLen();
            return logLen;
        } finally {
            // 返还到连接池
            jedis.close();
        }
    }





    /**
     * .
     * @param jedis
     */
    public void releaseJedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

    /**
     * 向队列里插入数据
     *
     * @param key
     * @param value
     */
    public void rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.rpush(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
    }

    /**
     * 查询出队列中有多少数组
     *
     * @param key
     * @return
     */
    public long llen(String key) {
        Long res = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            res = jedis.llen(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return res == null ? 0 : res.longValue();
    }

    /**
     * 查询出队列中的数据
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        String res = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            res = jedis.lpop(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return res;
    }

    /**
     * .
     * @param key
     * @param value
     * @return
     */
    public boolean lrem(String key,String value) {//remove from list
        long res = -1;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            res = jedis.lrem(key,0,value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return res>0;
    }

    /**
     * .
     * @param key
     * @param start
     * @param end
     */
    public void ltrim(String key,int start ,int end) {//remove from lis
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.ltrim(key,start,end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
    }

    /**
     * 查询出队列中的数据
     *
     * @param key
     * @param start 0表示头部的第一个元素
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> listres = null;
        Jedis jedis = null;
        try {
            //log.debug("got jedis 0");
            jedis = getJedis();
            //log.debug("got jedis 1");
            listres = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return listres;
    }

    /**
     * .
     * @param key
     * @param field
     * @param data
     * @return
     */
    public long setStringData(String key, String field, String data) {
        long ret = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            ret = jedis.hset(key, field, data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return ret;
    }

    /**
     * .
     * @param key
     * @param field
     * @return
     */
    public long delStringData(String key, String field) {
        long ret = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            ret = jedis.hdel(key, field);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return ret;
    }

    /**
     * .
     * @param key
     * @param field
     * @return
     */
    public String getStringData(String key, String field) {
        String data = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            data = jedis.hget(key, field);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            releaseJedis(jedis);
        }
        return data;
    }

    /**
     * 添加元素到集合
     *
     * @param key key
     * @param member 值
     * @return 添加结果（-1为非redis返回结果，表示出现为定义错误）
     */
    public long sadd(String key, String member) {
        Jedis jedis = getJedis();
        try {
            Long addResult = jedis.sadd(key, member);
            if (addResult != null) {
                return addResult;
            } else {
                return -1;
            }
        } catch (Exception e) {
            log.error("redis sadd exception.", e);
            return -1;
        } finally {
            releaseJedis(jedis);
        }
    }
    /**
     * 删除集合中的指定元素
     *
     * @param key key
     * @param member 值
     * @return 删除结果（-1为非redis返回结果，表示出现为定义错误）
     */
    public long sremove(String key, String member) {
        Jedis jedis = getJedis();
        try {
            Long addResult = jedis.srem(key, member);
            if (addResult != null) {
                return addResult;
            } else {
                return -1;
            }
        } catch (Exception e) {
            log.error("redis sremove exception.", e);
            return -1;
        } finally {
            releaseJedis(jedis);
        }
    }


    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }
}
