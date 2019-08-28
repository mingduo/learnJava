package concurrency.communication;

import java.util.stream.IntStream;

/**
 * @author : weizc
 * @description:
 * @since 2019/8/27
 */
public interface SynchronizedCommunication {



    void execute() throws InterruptedException;

    static void foreachPrint() {
        IntStream.rangeClosed(0, 10).forEachOrdered(i -> {
            System.out.printf("线程[%s] :输出结果:%d\n", Thread.currentThread().getName(), i);
        });
    }
}
