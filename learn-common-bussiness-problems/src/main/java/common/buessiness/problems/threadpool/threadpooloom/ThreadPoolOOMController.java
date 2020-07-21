package common.buessiness.problems.threadpool.threadpooloom;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/21
 */
@RequestMapping("threadpooloom")
@Slf4j
@RestController
public class ThreadPoolOOMController {


    private static void printStatus(ThreadPoolExecutor poolExecutor) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("===============");
            log.info("Pool Size:{}", poolExecutor.getPoolSize());
            log.info("Active Threads:{}", poolExecutor.getActiveCount());
            log.info("Number of Tasks Completed :{}", poolExecutor.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", poolExecutor.getQueue().size());
            log.info("===============");
        }, 0, 1, TimeUnit.SECONDS);


    }

    //循环 1 亿次向线程池提
    //交任务，每个任务都会创建一个比较大的字符串然后休眠一小时
    @GetMapping("oom1")
    public void oom1() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        printStatus(threadPool);

        for (int i = 0; i < 1_0000_0000; i++) {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 100000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining()) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(payload);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }


    @GetMapping("oom2")
    public void oom2() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        printStatus(threadPool);

        for (int i = 0; i < 1_0000_0000; i++) {
            threadPool.execute(() -> {
                String payload = IntStream.rangeClosed(1, 100000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining()) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(payload);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }


    @GetMapping("right")
    public int right() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5,
                5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)
                , new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build()
                , new ThreadPoolExecutor.AbortPolicy());

        printStatus(poolExecutor);

        IntStream.rangeClosed(1, 20)
                .forEach(__ -> {


                    int id = atomicInteger.incrementAndGet();
                    try {
                        poolExecutor.submit(() -> {
                            log.info("{} stared", id);

                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                log.error("", e);
                            }
                            log.info("{} finished", id);

                        });
                    } catch (Exception e) {
                        log.error("error submitted task {}", id, e);
                        atomicInteger.decrementAndGet();
                    }
                });

        poolExecutor.shutdown();
        poolExecutor.awaitTermination(1, TimeUnit.HOURS);

        return atomicInteger.intValue();
    }

    /**
     * 我们有没有办法让线程池更激进一点，优先开启更多的线程，而把队列当成一个后备
     * 方案呢？
     * tomcat 实现
     * @see org.apache.tomcat.util.threads.ThreadPoolExecutor
     * @return
     */
    //https://stackoverflow.com/questions/19528304/how-to-get-the-threadpoolexecutor-to-increase-threads-to-max-before-queueing
    @GetMapping("better")
    public int better() throws InterruptedException {
        //这里开始是激进线程池的实现
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>() {
            @Override
            public boolean offer(Runnable runnable) {
                //先返回false，造成队列满的假象，让线程池优先扩容
                return false;
            }
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,
                5,
                5, TimeUnit.SECONDS
                , queue,
                new ThreadFactoryBuilder().setNameFormat("better-thread-factory-%d").build(),
                (r, executor) -> {
                    try {
                        //等出现拒绝后再加入队列
                        //如果希望队列满了阻塞线程而不是抛出异常，那么可以注释掉下面三行代码，修改为executor.getQueue().put(r);
                        if (!executor.getQueue().offer(r, 0, TimeUnit.SECONDS)) {
                            throw new RejectedExecutionException("Thread pool is full, failed to offer " + r.toString());
                        }
                        //executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        //任务编号计数器
        AtomicInteger atomicInteger = new AtomicInteger();

        printStatus(poolExecutor);
        //每秒提交一个任务，每个任务耗时10秒执行完成，一共提交20个任务

        IntStream.rangeClosed(1,20).forEach(__->{
            int id = atomicInteger.incrementAndGet();
            try {
                poolExecutor.submit(()->{
                    log.info("{} started ",id);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    log.info("{} finished ",id);

                });

            }catch (Exception e){
                log.error("error submitted task :{}",id,e);
                atomicInteger.decrementAndGet();
            }
        });

        poolExecutor.shutdown();
        poolExecutor.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.intValue();
    }


}
