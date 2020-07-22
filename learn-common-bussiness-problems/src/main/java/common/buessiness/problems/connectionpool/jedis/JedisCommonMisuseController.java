package common.buessiness.problems.connectionpool.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static common.buessiness.problems.utils.Constants.right;
import static common.buessiness.problems.utils.Constants.wrong;

/**
 * @author : weizc
 * @since 2020/7/22
 */
@Slf4j
@RestController
@RequestMapping("jedis")
public class JedisCommonMisuseController {


    @RequestMapping(wrong)
    public void wrong() throws InterruptedException {
        //jedis#get ->client  socket ->send command->thread unsafe
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        new Thread(() -> {
            for (int i = 0; i < 1_0000; i++) {
                String value = jedis.get("a");
                if (!"1".equals(value)) {
                    log.warn("expect a to be 1 but found {}", value);
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 1_0000; i++) {
                String value = jedis.get("b");
                if (!"2".equals(value)) {
                    log.warn("expect b to be 2 but found {}", value);
                    return;
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);

    }

    private static JedisPool jedisPool = new JedisPool(URI.create("redis://127.0.0.1:6379"));

    /**
     * 使用 Jedis 提供的另一个线程安全的类 JedisPool 来获得 Jedis 的实例。
     * JedisPool 可以声明为 static 在多个线程之间共享，扮演连接池的角色。使用时，按需使用
     * try-with-resources 模式从 JedisPool 获得和归还 Jedis 实例
     * @throws InterruptedException
     */
    @RequestMapping(right)
    public void right() throws InterruptedException {


        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {

                for (int i = 0; i < 1_0000; i++) {
                    String value = jedis.get("a");
                    if (!"1".equals(value)) {
                        log.warn("expect a to be 1 but found {}", value);
                        return;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {

                for (int i = 0; i < 1_0000; i++) {
                    String value = jedis.get("b");
                    if (!"2".equals(value)) {
                        log.warn("expect b to be 2 but found {}", value);
                        return;
                    }
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);

    }


    @PostConstruct
    public void init() {
        try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
            Assert.isTrue("OK".equals(jedis.set("a", "1")), "set a=1 return OK");
            Assert.isTrue("OK".equals(jedis.set("b", "2")), "set b=2 return OK");

        }
        //我们最好通过 shutdownhook，在程序退出之前关闭 JedisPool：
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            jedisPool.close();
        }));
    }
}
