package leecode.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : weizc
 * @apiNode: 1115. 交替打印FooBar
 * 我们提供一个类：
 * <p>
 * class FooBar {
 * public void foo() {
 * for (int i = 0; i < n; i++) {
 * print("foo");
 * }
 * }
 * <p>
 * public void bar() {
 * for (int i = 0; i < n; i++) {
 * print("bar");
 * }
 * }
 * }
 * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 * <p>
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: n = 1
 * 输出: "foobar"
 * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * 示例 2:
 * <p>
 * 输入: n = 2
 * 输出: "foobarfoobar"
 * 解释: "foobar" 将被输出两次。
 * @since 2020/4/8
 */
public class FooBar {

    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        AtomicBoolean isFooWait = new AtomicBoolean(false);
        AtomicBoolean isBarWait = new AtomicBoolean(true);
        FooBar fooBar = new FooBar(10);

        executorService.execute(() -> {

            try {
                fooBar.bar(() -> {
                    try {
                        synchronized (fooBar) {
                            while (isBarWait.get()) {
                                fooBar.wait();
                            }

                            println("bar");
                            Thread.sleep(1000);
                            isBarWait.set(true);
                            isFooWait.set(false);
                            fooBar.notify();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        executorService.execute(() -> {

            try {
                fooBar.foo(() -> {
                    try {
                        synchronized (fooBar) {
                            while (isFooWait.get()) {
                                fooBar.wait();
                            }

                            println("foo");
                            Thread.sleep(1000);
                            isFooWait.set(true);
                            isBarWait.set(false);

                            fooBar.notify();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });






        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }


    private static void println(String msg) {
        System.out.printf("%s:%s\n", Thread.currentThread().getName(), msg);
    }
}
