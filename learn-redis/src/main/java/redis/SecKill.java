package redis;

import org.apache.commons.collections.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * <table border="1">
 * <tr><th>@Description: 秒杀活动</th></tr>
 * <tr><td>@Date:Created in 2018-8-22</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class SecKill implements Runnable {
    final Jedis jedis = new Jedis("127.0.0.1", 6379);
    // 监视keys
    String watchkeys = "watchkeys";


    @Override
    public void run() {

        try {
            // watchkeys
            jedis.watch(watchkeys);

            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint < 10) {
                // 开启事务
                Transaction tx = jedis.multi();

                tx.incr("watchkeys");
                // 提交事务，如果此时watchkeys被改动了，则返回null
                List<Object> list = tx.exec();
                if (CollectionUtils.isNotEmpty(list)) {

                    System.out.println("用户：" + userifo + "抢购成功，当前抢购成功人数:"
                            + (valint + 1));
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("用户：" + userifo + "抢购失败");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }
}

