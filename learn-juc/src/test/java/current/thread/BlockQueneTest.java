package current.thread;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
public class BlockQueneTest {

    private BlockingQueue mainQ = new ArrayBlockingQueue(1);
    private BlockingQueue subQ = new ArrayBlockingQueue(1);

    {
        subQ.add(new Object());
    }


    //非公平锁
    public static void main(String[] args) throws InterruptedException {

        BlockQueneTest t = new BlockQueneTest();

        Thread sub = new Thread(() -> {
            try {
                for (int k = 0; k < 5; k++) {
                    t.sub(k);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "sub");


        sub.start();


        for (int k = 0; k < 5; k++) {

            t.main(k);
        }
    }


    void sub(int k) {
        try {
            subQ.put(new Object());


            IntStream.range(0, 3).forEach(i ->
                    System.out.println(Thread.currentThread().getName()
                            + "->" + i + "==>loop of " + k)
            );
            mainQ.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void main(int k) {
        try {
            mainQ.put(new Object());

            IntStream.range(0, 7).forEach(i ->
                    System.out.println(Thread.currentThread().getName()
                            + "->" + i + "==>loop of " + k)
            );
            subQ.take();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}


