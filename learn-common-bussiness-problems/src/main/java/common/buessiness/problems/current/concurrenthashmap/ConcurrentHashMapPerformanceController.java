package common.buessiness.problems.current.concurrenthashmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 01 -没有充分了解并发工具的特性，从而无法发挥其威力
 *
 * @author : weizc
 * @since 2020/7/10
 */
@Slf4j
@RequestMapping("concurrenthashmapperformance")
@RestController
public class ConcurrentHashMapPerformanceController {

    private static int LOOP_COUNT = 10000000;
    private static int THREAD_COUNT = 10;
    private static int ITEM_COUNT = 10;


    /**
     * 使用 ConcurrentHashMap 来统计，Key 的范围是 10。
     * 使用最多 10 个并发，循环操作 1000 万次，每次操作累加随机的 Key。
     * 如果 Key 不存在的话，首次设置值为 1。
     * word count
     */


    private Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> data = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, LOOP_COUNT)
                    .parallel()
                    .forEach(i -> {
                        synchronized (data) {
                            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                            if (data.containsKey(key)) {
                                data.put(key, data.get(key) + 1);
                            } else {
                                data.put(key, 1L);
                            }
                        }
                    });
        });


        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        return data;
    }

    private Map<String, Long> gooduse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> data = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, LOOP_COUNT)
                    .parallel()
                    .forEach(i -> {
                        String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                        data.computeIfAbsent(key,k->new LongAdder()).increment();
                        });
                    });
   


        forkJoinPool.shutdown();
        //执行完毕 或者 超时
        forkJoinPool.awaitQuiescence(1, TimeUnit.HOURS);

        return data.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().longValue()));
    }


    /**
     * 比较性能
     * @return
     */
    @GetMapping("good")
    public String good() throws InterruptedException {
        StopWatch stopWatch=new StopWatch();

        stopWatch.start("normaluse");

        Map<String, Long> map = normaluse();
        stopWatch.stop();

        withCheck(map,"normaluse");

        stopWatch.start("gooduse");

        map = gooduse();

        stopWatch.stop();
        withCheck(map,"normaluse");

        log.info(stopWatch.prettyPrint());
        return "ok";
    }

    private void withCheck(Map<String, Long> map, String name) {
        //校验元素数量
        Assert.isTrue(map.size()==ITEM_COUNT,name+" size error");
        //累加为 10W
        Long sum = map.entrySet().stream().map(Map.Entry::getValue).reduce(0L, Long::sum);
        Assert.isTrue(sum==LOOP_COUNT,name+" count error");
    }
}
