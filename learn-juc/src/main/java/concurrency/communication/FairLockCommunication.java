package concurrency.communication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : weizc
 * @description:
 * @since 2019/8/28
 */
public class FairLockCommunication implements SynchronizedCommunication {

    //公平锁
    Lock lock = new ReentrantLock(true);


    public static void main(String[] args) throws InterruptedException {
        new FairLockCommunication().execute();
    }

    @Override
    public void execute() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);


        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                while (true) {
                    try {
                        if (lock.tryLock(3, TimeUnit.SECONDS)) {
                            SynchronizedCommunication.foreachPrint();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            });

        }


        executorService.awaitTermination(10, TimeUnit.SECONDS);
        executorService.shutdown();
    }
}
