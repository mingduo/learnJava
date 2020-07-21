package common.buessiness.problems.threadpool.threadpoolmixuse;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static common.buessiness.problems.utils.Constants.right;
import static common.buessiness.problems.utils.Constants.wrong;

/**
 * @author : weizc
 * @since 2020/7/21
 */
@Slf4j
@RestController
@RequestMapping("threadpoolmixuse")
public class ThreadPoolMixuseController {

    private void printStatus(ThreadPoolExecutor threadPoolExecutor) {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("================");

            log.info("Pool Size :{}", threadPoolExecutor.getPoolSize());
            log.info("Active Threads:{}", threadPoolExecutor.getActiveCount());
            log.info("Number of Tasks Completed :{}", threadPoolExecutor.getCompletedTaskCount());
            log.info("Number of Tasks in Queue :{}", threadPoolExecutor.getQueue().size());

            log.info("================");
        }, 0,1,TimeUnit.SECONDS);


    }

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2,
            1, TimeUnit.HOURS, new LinkedBlockingQueue<>(100)
            , new ThreadFactoryBuilder().setNameFormat("batch-file-process-threadpool-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());


    ThreadPoolExecutor asyncThreadPoolExecutor = new ThreadPoolExecutor(200,
            200,
            1, TimeUnit.HOURS, new LinkedBlockingQueue<>(1000)
            , new ThreadFactoryBuilder().setNameFormat("batch-file-process-threadpool-%d").build());

    private Callable<Integer>calcTask(){
        return () -> {
            TimeUnit.MILLISECONDS.sleep(10);
            return 1;
        };
    }

    /**
     *  wrk 压测 tps=
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(wrong)
    public int wrong() throws ExecutionException, InterruptedException {
        return threadPoolExecutor.submit(calcTask()).get();
    }

    /**
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(right)
    public int right() throws ExecutionException, InterruptedException {
        return asyncThreadPoolExecutor.submit(calcTask()).get();
    }


    @PostConstruct
    public void init(){

        printStatus(threadPoolExecutor);

        new Thread(()->{
            //模拟需要写入的大量数据
            String payload = IntStream.rangeClosed(1, 1_000_000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining(""));
            while (true){
                Path path = Paths.get("learn-common-bussiness-problems/target/classes/" +
                        "common/buessiness/problems/threadpool/threadpoolmixuse/demo.txt");

                threadPoolExecutor.execute(()->{

                    try {
                        //每次都是创建并写入相同的数据到相同的文件
                        Files.write(path, Collections.singleton(LocalDateTime.now()+"："+payload),
                                StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        }).start();


    }
}
