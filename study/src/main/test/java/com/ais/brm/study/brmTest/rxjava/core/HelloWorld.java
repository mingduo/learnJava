package com.ais.brm.study.brmTest.rxjava.core;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author : weizc
 * RxJava2 五大重要角色介绍
 * https://blog.csdn.net/weixin_36709064/article/details/82911270#3.1%E3%80%81Single%E7%AE%80%E4%BB%8B
 * @description:
 * @since 2018/12/31
 */
@Slf4j
public class HelloWorld {

    @Test
    public void helloworld() {
        Flowable.just("Hello world").subscribe(System.out::println);

        System.out.println("---------------------");

        Flowable.fromArray("hello", "world").subscribe(s -> System.out.println("Hello " + s + "!"));

    }

    //流主动发射 数据，错误或完成信号时的状态
    @Test
    public void runtime() {
        Observable.create(emitter -> {
            while (!emitter.isDisposed()) {
                long time = System.currentTimeMillis();
                emitter.onNext(time);
                if (time % 2 != 0) {
                    emitter.onError(new IllegalStateException("Odd millisecond!"));
                    break;
                }
            }
        })
                .subscribe(t -> log.info(t.toString()), Throwable::printStackTrace);
    }


    //
    @Test
    public void backgroundComputation() throws InterruptedException {
        Flowable.fromCallable(() -> {
            log.info("wait...");
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(log::info, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }

    @Test
    public void ConcurrencywithinAflow() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(t -> log.info(t.toString()));


        System.out.println("------------并行执行------------");

        //concatMap  顺序执行
        //concatMapEager  出流程将按创建内部流程的顺序排列
        Flowable.range(1, 10)
                .concatMapEager(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);

        System.out.println("------------并行执行2------------");


        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);


    }


}
