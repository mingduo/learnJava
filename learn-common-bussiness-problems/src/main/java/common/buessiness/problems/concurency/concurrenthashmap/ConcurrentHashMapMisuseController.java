package common.buessiness.problems.concurency.concurrenthashmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static common.buessiness.problems.utils.Constants.right;
import static common.buessiness.problems.utils.Constants.wrong;

/**
 *
 *   01-concurrent hashmap 每个操作是原子的 但是 几个操作组合 不是原子的
 *
 * @since 2020/7/10
 * @author : weizc
 */
@RestController
@RequestMapping("concurrenthashmapmisuse")
@Slf4j
public class ConcurrentHashMapMisuseController {

    private static int THREAD_COUNT = 10;
    private static int ITEM_COUNT = 1000;


    //帮助方法，用来获得一个指定元素数量模拟数据的ConcurrentHashMap
    private ConcurrentMap<String,Long> getData(int count){
       return LongStream.rangeClosed(1,count)
                .boxed()
                .collect(Collectors.toConcurrentMap(k-> UUID.randomUUID().toString(),
                        Function.identity(),(a,b)->a));
    }
    /**
     * 模拟  有一个含 900 个元素的
     * Map，现在再补充 100 个元素进去，这个补充操作由 10 个线程并发进行
     * @return
     */
    @GetMapping(wrong)
    public String wrong() throws InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ConcurrentMap<String, Long> concurrentHashMap = getData(990);
        //使用线程池并发处理逻辑
        forkJoinPool.execute(()->{
            IntStream.rangeClosed(1,THREAD_COUNT).parallel().forEach(i->{
                //查询还需要补充多少个元素
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.info("data'size:{}",gap);
                //补充元素
                concurrentHashMap.putAll(getData(gap));
            });
        });
        //等待所有任务完成
        forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
        //最后元素个数会是1000吗？
        log.info("finish size:{}", concurrentHashMap.size());

        return wrong;
    }


    @GetMapping(right)
    public String right() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ConcurrentMap<String, Long> concurrentHashMap = getData(990);
        forkJoinPool.execute(()->{
            IntStream.rangeClosed(1,THREAD_COUNT).parallel().forEach(i->{
                synchronized (concurrentHashMap) {
                    int gap = ITEM_COUNT - concurrentHashMap.size();
                    log.info("data'size:{}", gap);
                    concurrentHashMap.putAll(getData(gap));
                }
            });

        });

        forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
        log.info("finish size:{}", concurrentHashMap.size());

        return right;
    }

}
