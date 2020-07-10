package concurrency.communication;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/11
 */
public class BlockingQueueDemo {

    static BlockingQueue<Integer> isFull = new ArrayBlockingQueue<>(1, true);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        Random r = new Random();

        //生产者
        executorService.execute(() -> {
            while (true) {

                int i = r.nextInt(10);
                try {
                    print("生产者:" + i);
                    isFull.put(i);

                    Thread.sleep(2000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //消费者
        executorService.execute(() -> {
            while (true) {

                try {

                    Integer value = isFull.take();
                    print("消费者" + value);

                    Thread.sleep(2000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.awaitTermination(1, TimeUnit.SECONDS);

        executorService.shutdown();
    }

    private static void print(String s) {
        System.out.printf("%s:%s\n", Thread.currentThread().getName(), s);
    }
}
