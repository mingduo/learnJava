package common.buessiness.problems.threadpool.threadpoolreuse;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static common.buessiness.problems.utils.Constants.wrong;

@RestController
@RequestMapping("threadpoolreuse")
@Slf4j
public class ThreadPoolReuseController {


    @RequestMapping(wrong)
    public String wrong() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolHelper.getThreadPool();

        IntStream.rangeClosed(1,10).forEach(t->{
            threadPoolExecutor.execute(()->{
                String payload = IntStream.rangeClosed(1, 1000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining()) + UUID.randomUUID();

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(payload);
            });

        });

        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(1,TimeUnit.HOURS);

        return "OK";


    }


    static class ThreadPoolHelper {

        static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build()
        );

        static ThreadPoolExecutor getThreadPool() {
            return (ThreadPoolExecutor) Executors.newCachedThreadPool();
        }


        static ExecutorService getRightThreadPool() {
            return threadPoolExecutor;
        }
    }
}