package concurrency.myThread;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/12
 */
public class CountAddDemo {
    //  static int count=0;
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        int num = 5000;
        ExecutorService executorService = Executors.newCachedThreadPool();


        for (int i = 0; i < num; i++) {
            executorService.execute(() -> {
                add();
                //
            });
        }


        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();

        //System.out.println("count = " +count.get());
        System.out.println("count = " + count);

    }

    private static void add() {
        // count++;
        count.incrementAndGet();
    }
}
