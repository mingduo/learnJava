package concurrency.myThread;

import java.util.function.Consumer;

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
public class WaitAndNotifyCommunication implements SynchronizedCommunication {

    //子线程 开始状态  sync修饰保证可见性
    boolean subStart = false;

    boolean mainStart = true;


    public static void main(String[] args) {

        new WaitAndNotifyCommunication().execute();

    }

    @Override
    public void execute() {
        new Thread(() -> {
            doMutexLoop(b -> {
                subStart = !b;
                mainStart = b;
            });

        }, "sub").start();


        doMutexLoop(b -> {
            subStart = b;
            mainStart = !b;
        });
    }

    private  boolean getCondtion(String name) {
        if (name.equals("main")) {
            return mainStart;
        } else {
            return subStart;

        }
    }


    private  void doMutexLoop(Consumer<Boolean> changeStatus) {

        while (true) {

            Object monitor = WaitAndNotifyCommunication.class;

            synchronized (monitor) {
                try {    //condition
                    while (getCondtion(Thread.currentThread().getName())) {

                        monitor.wait();

                    }
                    //do logic
                    SynchronizedCommunication.foreachPrint();

                    changeStatus.accept(false);

                    monitor.notify();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}


