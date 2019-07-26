package com.ais.brm.study.brmTest.current.myThread;

import java.util.stream.IntStream;

/**
 * <table border="1">
 * <tr><th>@Description: 子线程输出 主线程阻塞
 * 主线程输出 子线程阻塞</th></tr>
 * <tr><td>@Date:Created in 2018-9-17</td>
 * </tr>
 * </table>
 *两个线程通信
 *
 * @author :    weizc
 */
public class waitNotifyTest {
    private  volatile boolean wait_flag = false;


    public static void main(String[] args) throws InterruptedException {
        waitNotifyTest t = new waitNotifyTest();
        Thread sub = new Thread(() -> {
            try {
                for (int k = 0; k < 5; k++) {
                    t.sub(k);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "sub");

        sub.start();
        for (int k = 0; k < 5; k++) {

            t.main(k);
        }
    }


    synchronized void sub(int k) throws InterruptedException {
        while (wait_flag) {
            this.wait();
        }

        IntStream.range(0, 5).forEach(i ->
                System.out.println(Thread.currentThread().getName()
                        + "->" + i + "==>loop of " + k)
        );
        wait_flag = true;
        this.notify();
    }

    synchronized void main(int k) throws InterruptedException {
        while (!wait_flag) {
            this.wait();
        }
        IntStream.range(0, 20).forEach(i ->
                System.out.println(Thread.currentThread().getName()
                        + "->" + i + "==>loop of " + k)
        );
        wait_flag = false;
        this.notify();


    }
}


