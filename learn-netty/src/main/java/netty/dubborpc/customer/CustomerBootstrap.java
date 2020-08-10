package netty.dubborpc.customer;

import lombok.extern.slf4j.Slf4j;
import netty.dubborpc.api.HelloService;
import netty.dubborpc.netty.NettyDubboClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author weizc
 */
@Slf4j
public class CustomerBootstrap {
    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    private final static int count = 200;

    public static void main(String[] args) {
        //单线程测试
        tryWithResourceNettyDubboClient(CustomerBootstrap::runWithSingleThread);
        //多线程测试
        tryWithResourceNettyDubboClient(CustomerBootstrap::runWithMultiThread);

        tryWithResourceNettyDubboClient(CustomerBootstrap::runWithMultiThread2);


    }


    private static void tryWithResourceNettyDubboClient(Consumer<HelloService> consumer) {

        try (NettyDubboClient client = new NettyDubboClient();) {
            //创建代理对象
            HelloService service = client.getBean(HelloService.class);
            consumer.accept(service);
        }

    }

    private static void runWithMultiThread2(HelloService service) {
        CompletableFuture.runAsync(() -> {
            IntStream.rangeClosed(1, count).parallel().forEach(i ->
                    invokeServer(service, i));
        }).join();

        log.info("task runWithMutiThread2  done");

    }

    private static void runWithMultiThread(HelloService service) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        IntStream.rangeClosed(1, count).forEach(i -> {
            forkJoinPool.execute(() -> invokeServer(service, i));
        });

        forkJoinPool.shutdown();
        try {
            forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task runWithMutiThread done");

    }

    private static void runWithSingleThread(HelloService service) {
        IntStream.rangeClosed(1, count).forEach(i -> {
            invokeServer(service, i);
        });
        log.info("task runWithSingleThread done");

    }

    private static void invokeServer(HelloService service, int i) {
        //通过代理对象调用服务提供者的方法(服务)

        String result = service.sayHi("dubbo-" + i);

        log.info("result:{}", result);
    }


}