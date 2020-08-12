package common.buessiness.problems.httpinvoke.routelimit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 爬虫项目，整体爬取数据的效率很低，增加线程池数量也无济于事，只能
 * 堆更多的机器做分布式的爬虫 问题在于没有 控制 httpclient 并发数
 *
 * @author : weizc
 * @since 2020/7/23
 */
@RequestMapping("routelimit")
@RestController
@Slf4j
public class RouteLimitController {

    @Value("http://localhost:${server.port}/routelimit/server")
    private String testUrl;
    static CloseableHttpClient client1;
    static CloseableHttpClient client2;

    static {
        client1 = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager())
                .evictIdleConnections(60, TimeUnit.SECONDS).build();

        client2 = HttpClients.custom().setMaxConnPerRoute(10)
                .setMaxConnTotal(20)
                .evictIdleConnections(60, TimeUnit.SECONDS).build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private int sendRequest(int count, Supplier<CloseableHttpClient> httpClientS) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();

        long begin = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.rangeClosed(1, count).forEach(__ -> {
            executorService.submit(() -> {
                try {
                    String rsp = EntityUtils.toString(httpClientS.get().execute(new HttpGet(testUrl)).getEntity());
                    atomicInteger.getAndAdd(Integer.parseInt(rsp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        log.info("请求次数：{}, 总耗时 :{} ms", atomicInteger.get(), System.currentTimeMillis() - begin);
        return atomicInteger.get();
    }

    private int sendRequest2(int count, Supplier<CloseableHttpClient> httpClientSupplier) {
        AtomicInteger atomicInteger = new AtomicInteger();
        long begin = System.currentTimeMillis();

        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(Executors.newCachedThreadPool());
        IntStream.rangeClosed(1, count)
                .forEach(__ -> {
                    completionService.submit(() -> {
                        CloseableHttpResponse response = httpClientSupplier.get().execute(new HttpGet(testUrl));
                        return Integer.parseInt(EntityUtils.toString(response.getEntity()));
                    });
                });

        for (int i = 0; i < count; i++) {

            try {
                Integer result = completionService.take().get();
                if (result != null) {
                    atomicInteger.addAndGet(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        log.info("请求次数：{}, 总耗时 :{} ms", atomicInteger.get(), System.currentTimeMillis() - begin);
        return atomicInteger.get();
    }


    //模拟10次请求  closeableHttpClient 默认并发数2个
    @GetMapping("wrong")
    public int wrong(@RequestParam(defaultValue = "10") int count) throws InterruptedException {
        return sendRequest(count, () -> client1);
    }


    @GetMapping("right")
    public int right(@RequestParam(defaultValue = "10") int count) throws InterruptedException {
        return sendRequest2(count, () -> client2);

    }

    @GetMapping("server")
    public int server() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return 1;
    }
}
