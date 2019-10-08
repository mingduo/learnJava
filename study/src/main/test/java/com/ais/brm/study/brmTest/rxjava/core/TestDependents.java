package com.ais.brm.study.brmTest.rxjava.core;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * rxjava 依赖问题
 *
 * @author : weizc
 * @description:
 * @since 2018/12/31
 */
@Slf4j
public class TestDependents {
    //执行A->执行B
    @Test
    public void Dependent() throws InterruptedException {
        Observable<Integer> integerObservable = Observable.range(1, 3).map(t -> t * 3).map(t -> t * t).doOnNext(System.out::println)
                .doOnNext(System.out::println);
        integerObservable.subscribe(t -> log.info("stop"));

        System.out.println("--------Dependent--------------");

        Disposable subscribe = Observable.just(1, 3).map(t -> t * 3).map(t -> t * t).subscribe(System.out::println);

    }

    //无依赖  忽略A的运行
    @Test
    public void NDependent() throws InterruptedException {
        Observable continued = Observable.just(1, 3).flatMapSingle(ignored -> Single.just(ignored * 2));

        continued.map(v -> v.toString())
                .subscribe(System.out::println);
        System.out.println("-------non-Dependent--------------");

        Observable.just(1, 3)
                .ignoreElements()           // returns Completable
                .andThen(Observable.just(4, 6))
                .map(v -> v.toString()).subscribe(log::info);
    }


    //递延依赖
    @Test
    public void Deferreddependent() throws InterruptedException {
        AtomicInteger count = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count.incrementAndGet())
                .ignoreElements()
                .andThen(Single.just(count.get()))
                .subscribe(t -> log.info(t.toString()));


        System.out.println("--------postive example   1--------------");


        AtomicInteger count2 = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count2.incrementAndGet())
                .ignoreElements()
                .andThen(Single.defer(() -> Single.just(count2.get())))
                .subscribe(System.out::println);


        System.out.println("--------postive example   2--------------");

        AtomicInteger count3 = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count3.incrementAndGet())
                .ignoreElements()
                .andThen(Single.fromCallable(() -> count3.get()))
                .subscribe(System.out::println);


    }

}
