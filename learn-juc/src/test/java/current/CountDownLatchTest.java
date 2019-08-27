package current;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-5-4</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class CountDownLatchTest {
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println(ThreadLocalRandom.current().nextInt());
        boolean await = latch.await(3, TimeUnit.SECONDS);
        System.out.println(await);
    }

    @Test
    public void test2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        ExecutorService service2 = Executors.newCachedThreadPool();
        ExecutorService service3 = Executors.newScheduledThreadPool(10);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }
}


