package common.buessiness.problems.java8;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/8/17
 */
public class ParallelTest {

    @Test
    public void parallel() {
        IntStream.rangeClosed(1, 100)
                .parallel().forEach(i -> {
            System.out.println(LocalDateTime.now() + ":" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 为了测试这五种实现方式，我们设计一个场景：使用 20 个线程（threadCount）以并行方
     * 式总计执行 10000 次（taskCount）操作。因为单个任务单线程执行需要 10 毫秒（任务
     * 代码如下），也就是每秒吞吐量是 100 个操作，那 20 个线程 QPS 是 2000，执行完
     * 10000 次操作最少耗时 5 秒。
     */
    @Test
    public void allMethods() throws InterruptedException, ExecutionException {

        int threadCount = 20;
        int taskCount = 10_000;

        StopWatch stopWatch = new StopWatch();


        stopWatch.start("thread");
        Assert.assertEquals(taskCount, thread(taskCount, threadCount));
        stopWatch.stop();

        stopWatch.start("threadpool");
        Assert.assertEquals(taskCount, threadpool(taskCount, threadCount));
        stopWatch.stop();

        //先stream 否则 forkJoin 启动后 属性设置不生效
        stopWatch.start("stream");
        Assert.assertEquals(taskCount, stream(taskCount, threadCount));
        stopWatch.stop();

        stopWatch.start("forkJoin");
        Assert.assertEquals(taskCount, forkJoin(taskCount, threadCount));
        stopWatch.stop();
        stopWatch.start("completableFuture");
        Assert.assertEquals(taskCount, completableFuture(taskCount, threadCount));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }


    private int thread(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        IntStream.rangeClosed(1, threadCount)
                .mapToObj(i -> new Thread(() -> {
                    IntStream.rangeClosed(1, taskCount / threadCount).forEach(t -> increment(atomicInteger));
                    countDownLatch.countDown();
                })).forEach(Thread::start);
        countDownLatch.await();
        return atomicInteger.get();

    }

    private int threadpool(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        IntStream.rangeClosed(1, taskCount)
                .forEach(i -> executorService.execute(() -> increment(atomicInteger)));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.get();

    }

    private int forkJoin(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();

        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, taskCount).parallel().forEach(__ -> increment(atomicInteger)));

        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.get();
    }


    private int stream(int taskCount, int threadCount) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadCount));
        AtomicInteger atomicInteger = new AtomicInteger();

        IntStream.rangeClosed(1, taskCount)
                .parallel().forEach(__ -> increment(atomicInteger));

        return atomicInteger.get();
    }


    private int completableFuture(int taskCount, int threadCount) throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger();

        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        CompletableFuture.runAsync(()->{
            IntStream.rangeClosed(1,taskCount).parallel().forEach(__->increment(atomicInteger));
        },forkJoinPool).get();

        return atomicInteger.get();
    }

    private void increment(AtomicInteger atomicInteger) {
        try {
            atomicInteger.incrementAndGet();
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
