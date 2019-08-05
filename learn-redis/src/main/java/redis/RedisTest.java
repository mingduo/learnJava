package redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <table border="1">
 * <tr><th>@Description: redis秒杀</th></tr>
 * <tr><td>@Date:Created in 2018-8-22</td>
 * </tr>
 * </table>
 * watch
 * 事务
 *
 * @author :    weizc
 */
public class RedisTest {

    static Jedis jedis = new Jedis("127.0.0.1", 6379);
    static String watchkeys = "watchkeys";
    static ExecutorService executor = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {

        // 重置watchkeys为0
        jedis.set(watchkeys, "0");
        // 清空抢成功的，与没有成功的
        jedis.del("setsucc", "setfail");
        jedis.close();
    // 测试一万人同时访问
        for (int i = 0; i < 5000; i++) {
            executor.execute(new SecKill());
        }
        executor.shutdown();
    }


}
