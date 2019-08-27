package current;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-29</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class ExcutorServiceTest {
    @Test
    public void test1() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.submit(() -> {
            try {
                runMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public void runMethod() throws InterruptedException {

        Random r = new Random();
        int i = r.nextInt(10);
        Thread.sleep(1000 * i);

        System.out.println(i);

    }

    @Test
    public void test2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        ExecutorService pool = Executors.newFixedThreadPool(2);
        IntStream.range(0, 5).<Runnable>mapToObj(t -> () -> {
            try {
                runMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }

        }).forEachOrdered(pool::execute);
        latch.await();
    }
}
