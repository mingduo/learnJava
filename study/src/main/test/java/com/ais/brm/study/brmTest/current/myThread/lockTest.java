package com.ais.brm.study.brmTest.current.myThread;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

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
public class lockTest {
    Lock lock = new ReentrantLock();
    volatile int state = 1;

    Condition subCondition = lock.newCondition();
    Condition sub2Condition = lock.newCondition();
    Condition mainCondition = lock.newCondition();

    //非公平锁
    public static void main(String[] args) throws InterruptedException {

        lockTest t = new lockTest();
        Thread sub = new Thread(() -> {
            try {
                for (int k = 0; k < 5; k++) {
                    t.sub(k);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "sub");

        Thread sub2 = new Thread(() -> {
            try {
                for (int k = 0; k < 5; k++) {
                    t.sub2(k);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "sub2");

        sub.start();
        sub2.start();


        for (int k = 0; k < 5; k++) {

            t.main(k);
        }
    }


    void sub(int k) {
        lock.lock();
        try {
            while (state != 1) {
                subCondition.await();

            }

            IntStream.range(0, 3).forEach(i ->
                    System.out.println(Thread.currentThread().getName()
                            + "->" + i + "==>loop of " + k)
            );
            state = 2;
            sub2Condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void sub2(int k) {
        lock.lock();
        try {
            while (state != 2) {
                sub2Condition.await();

            }

            IntStream.range(0, 5).forEach(i ->
                    System.out.println(Thread.currentThread().getName()
                            + "->" + i + "==>loop of " + k)
            );
            state = 3;
            mainCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
    }

    void main(int k) {
        lock.lock();
        try {
            while (state != 3) {
                mainCondition.await();
            }
            IntStream.range(0, 7).forEach(i ->
                    System.out.println(Thread.currentThread().getName()
                            + "->" + i + "==>loop of " + k)
            );
            state = 1;
            subCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
    }
}


