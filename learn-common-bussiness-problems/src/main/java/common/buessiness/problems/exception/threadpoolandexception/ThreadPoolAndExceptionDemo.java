package common.buessiness.problems.exception.threadpoolandexception;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 *  
 * @since 2020/7/30
 * @author : weizc 
 */
@Slf4j
public class ThreadPoolAndExceptionDemo {

    public static void main(String[] args) throws InterruptedException {

        executeWrong();

        println();

        executeRight();

        println();

        executeRight2();

        println();

        submitWrong();

        println();

        submitRight();

    }


    private static void println(){
        System.out.println("======");
    }

    /**
     * 未捕获线程池的异常 因此不知道哪个线程出现异常
     * @throws InterruptedException
     */
    private static void executeWrong() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-%d").build();
        execute(threadFactory);
    }
    //最好在任务内部做好异常处理
    private static void executeRight() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-%d")
                .setUncaughtExceptionHandler((t,e)->{
                    log.error("thread:{},got error",t.getName(),e);
                })
                .build();
        execute(threadFactory);
    }
    //设置全局的默认未捕获异常处理程序：
    private static void executeRight2() throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t,e)->{
            log.error("thread:{},got error",t.getName(),e);
        });

        executeWrong();
    }

    private static void execute(ThreadFactory threadFactory) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1, threadFactory);
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    service.execute(() -> {
                        if (i == 5) {
                            throw new RuntimeException("error");
                        }
                        log.info("i am done:{}", i);
                    });
                });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
    }



    private static void submitWrong() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-%d")
                .setUncaughtExceptionHandler((t,e)->{
                    log.error("thread:{},got error",t.getName(),e);
                })
                .build();
        submit(threadFactory);
    }

    private static void submitRight() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-%d")
                .build();
        ExecutorService service = Executors.newFixedThreadPool(1, threadFactory);
        List<Future> tasks = IntStream.rangeClosed(1, 10)
                .mapToObj(i ->
                    service.submit(() -> {
                        if (i == 5) {
                            throw new RuntimeException("error");
                        }
                        log.info("i am done:{}", i);
                    })
                ).collect(Collectors.toList());

        tasks.forEach(task->{
            try {
                task.get();
            } catch (Exception e) {
                log.error("Got exception",e);
            }

        });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);    }


    private static void submit(ThreadFactory threadFactory) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1, threadFactory);
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    service.submit(() -> {
                        if (i == 5) {
                            throw new RuntimeException("error");
                        }
                        log.info("i am done:{}", i);
                    });
                });

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
    }
}


