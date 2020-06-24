package concurrency.myThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @apiNode:
 * @since 2020/4/12
 * @author : weizc 
 */
public class AtomicCountDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for(AtomicInteger integer = new AtomicInteger(1); integer.get()<100; integer.incrementAndGet()){
            int val = integer.get();
            executorService.execute(()->printAdd(val));
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    private static void printAdd(AtomicInteger integer){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print(""+integer.get());

    }

    private static void printAdd(Integer val){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print(""+val);

    }

    private static void print(String msg){
        System.out.printf("%s:%s\n",Thread.currentThread().getName(),msg);
    }

}
