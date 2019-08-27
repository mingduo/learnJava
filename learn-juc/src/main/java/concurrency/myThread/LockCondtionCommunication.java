package concurrency.myThread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <table border="1">
 * <tr><th>@Description: 子线程输出 主线程阻塞
 * 主线程输出 子线程阻塞</th></tr>
 * <tr><td>@Date:Created in 2018-9-17</td>
 * </tr>
 * </table>
 * 两个线程通信
 *
 * @author :    weizc
 */
public class LockCondtionCommunication implements SynchronizedCommunication {

    ReentrantLock lock = new ReentrantLock();


    Condition subCondtion = lock.newCondition();
    Condition mainCondtion = lock.newCondition();

    volatile boolean subStart = false;

    public static void main(String[] args) throws InterruptedException {
        new LockCondtionCommunication().execute();
    }

    @Override
    public void execute() throws InterruptedException {


        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            while (true) {
                try {
                    lock.lock();
                    subAction(subCondtion, mainCondtion);

                } finally {
                    lock.unlock();
                }

            }
        });

        executorService.submit(() -> {
                    while (true) {
                        try {
                            lock.lock();

                            mainAction(subCondtion, mainCondtion);

                        } finally {
                            lock.unlock();
                        }

                    }
                }
        );

        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
    }


    private void subAction(Condition subCondtion, Condition mainCondtion) {

        while (subStart) {
            try {
                subCondtion.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SynchronizedCommunication.foreachPrint();
        subStart = true;
        mainCondtion.signal();
    }

    private void mainAction(Condition subCondtion, Condition mainCondtion) {
        while (!subStart) {
            try {
                mainCondtion.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SynchronizedCommunication.foreachPrint();
        subStart = false;
        subCondtion.signal();
    }
}


