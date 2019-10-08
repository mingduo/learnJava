package com.ais.brm.study.brmTest.rxjava.core;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : weizc
 * @description:
 * @since 2019/1/1
 */
public class TestObservables {
    /**
     * 数据源操作
     * Asynchronous  or Synchronous  Observable Example
     * 通过设计自己的Observable并使用create（）方法实现它来实现
     * 异步i / o，计算操作甚至“无限”数据流。
     */
    @Test
    public void testObservablesCreate() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


        ObservableOnSubscribe<String> handler = emitter -> {

            Future<Object> future = executor.schedule(() -> {
                //回调
                emitter.onNext("Hello");
                emitter.onNext("World");
                emitter.onComplete();

                return null;
            }, 1, TimeUnit.SECONDS);

            emitter.setCancellable(() -> future.cancel(false));
        };

        Observable<String> observable = Observable.create(handler);

        // To see output:
        observable.blockingSubscribe(item -> System.out.println(item), error -> error.printStackTrace(),
                () -> System.out.println("Done"));

        executor.shutdown();
    }


    /**
     * 数据源操作
     * from   just
     * 从现有数据格式创建 Observable
     *
     * @throws InterruptedException
     */
    @Test
    public void testObservablesExistingDataStructure() throws InterruptedException {
        Observable<String> o = Observable.fromArray("a", "b", "c");

        Observable<String> o2 = Observable.fromIterable(Arrays.asList("c", "d"));

        Observable<String> o3 = Observable.just("one object");

        System.out.println("o1-------------");
        // To see output:
        o.blockingSubscribe(item -> System.out.println(item), error -> error.printStackTrace(),
                () -> System.out.println("Done"));
        System.out.println("o2-------------");


        o2.blockingSubscribe(item -> System.out.println(item), error -> error.printStackTrace(),
                () -> System.out.println("Done"));
        System.out.println("o3-------------");

        o3.blockingSubscribe(item -> System.out.println(item), error -> error.printStackTrace(),
                () -> System.out.println("Done"));

    }


    /**
     * 转换操作
     * <p>
     * skip 跳过
     * take  获取5个
     * map 转换
     *
     * @throws InterruptedException
     */
    @Test
    public void testObservablesTransform() throws InterruptedException {
        Observable<Integer> o = Observable.range(1, 100);
        o.skip(10).take(5).map(t -> t * 3).blockingSubscribe(System.out::println);
    }

}
