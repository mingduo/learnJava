package common.buessiness.problems.connectionpool.twotimeoutconfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * 假设我们希望设置连接超时5s，获取连接超时10s：
 * @author weizc
 */
@RequestMapping("twotimeoutconfig")
@Slf4j
@RestController
public class TwoTimeoutConfigController {

    @Value("http://localhost:${server.port}/twotimeoutconfig/test")
    String testUrl;

    static CloseableHttpClient client;
    static {
        client= HttpClients.createSystem();
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("mysql")
    public String mysql() {
        //调试StandardSocketFactory进行验证
        return jdbcTemplate.queryForObject("SELECT 'OK'", String.class);
    }

    @GetMapping("jedis")
    public String jedis(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(1);
        config.setMaxWaitMillis(10000);
        try(JedisPool jedisPool=new JedisPool(config,"localhost",6379,5000);
            Jedis jedis=jedisPool.getResource();
        ) {
            return jedis.set("test","test");
        }
    }

    @GetMapping("http")
    public String http(){
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000)
                .setConnectTimeout(5000)
                .build();
        HttpGet httpGet = new HttpGet(testUrl);
        httpGet.setConfig(requestConfig);
        try(
                CloseableHttpResponse response=client.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

}