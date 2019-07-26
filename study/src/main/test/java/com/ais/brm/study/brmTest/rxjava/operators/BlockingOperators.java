package com.ais.brm.study.brmTest.rxjava.operators;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 
 * @description:
 * @since 2019/1/2
 * @author : weizc 
 */
@Slf4j
public class BlockingOperators {
    /**
     *  providing a set of operators on the items emitted by the Observable that block
     */
    @Test
    public void test(){

        System.out.print("blockingFirst: ");
        System.out.println(Observable.just("hello","tom").blockingFirst());

        System.out.print("firstOrDefault: ");
        System.out.println(Observable.just("first","last").blockingFirst("default"));

        System.out.print("blockingLast: ");
        System.out.println(Observable.just("first","last").blockingLast("default"));

        System.out.print("blockingForEach: ");
        Observable.just("first","last").blockingForEach(System.out::println);

        System.out.print("blockingIterable: ");
        Observable.just("first","last").blockingIterable().forEach(System.out::println);

        System.out.print("blockingNext: ");
        Observable.just("first","last").blockingNext().forEach(System.out::println);

        System.out.print("blockingSubscribe: ");
        Observable.just("first","last").blockingSubscribe(System.out::println);


        System.out.print("blockingMostRecent: ");
        Observable.just("first","last").blockingMostRecent("initValue").forEach(System.out::println);
    }


    @Test
    public void test2(){

        System.out.print("first: ");
        Single<String> first = Observable.just("hello", "tom").first("default");
        first.subscribe(System.out::println);

        System.out.print("lastElement: ");
        Maybe<String> lastElement = Observable.just("first", "last").lastElement();
        lastElement.subscribe(System.out::println);



    }

}
