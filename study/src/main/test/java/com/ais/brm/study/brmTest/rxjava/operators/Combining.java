package com.ais.brm.study.brmTest.rxjava.operators;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import org.junit.Test;
import scala.Tuple2;

import java.util.concurrent.TimeUnit;

/**
 * @author : weizc
 * @description: 组合类操作符  important
 * @since 2019/1/3
 * https://www.jianshu.com/p/546fe44a6e22
 */
public class Combining {
    /**
     * This section explains operators you can use to combine multiple Observables.
     */

    /**
     * Emit a specified sequence of items before beginning to emit the items from the Observable.
     */
    @Test
    public void test() {
        Observable<String> names = Observable.just("Spock", "McCoy");
        names.startWith("Kirk").subscribe(item -> System.out.println(item));
    }

    /**
     * 将两个Observable发射的事件序列组合并成一个事件序列，
     * 就像是一个Observable发射的一样。
     * 你可以简单的将它理解为两个Obsrvable合并成了一个Observable，合并后的数据是无序的
     * Combines multiple Observables into one. Any onError notifications passed from any of
     * the source observables will immediately be passed through to through to the
     * observers and will terminate the merged Observable.
     */
    @Test
    public void test2() {
        System.out.print("merge =>");

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        Observable<String> letterSequence = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(position -> letters[position.intValue()]).take(letters.length);

        Observable<Long> numberSequence = Observable.interval(500, TimeUnit.MILLISECONDS).take(5);

        Observable.merge(letterSequence, numberSequence).blockingSubscribe(item -> System.out.println(item));


        System.out.print("concat =>");

        Observable.concat(letterSequence, numberSequence).blockingSubscribe(item -> System.out.println(item));


        System.out.print("mergeWith =>");
        Observable.just(1, 2, 3)
                .mergeWith(Observable.just(4, 5, 6))
                .subscribe(item -> System.out.println(item));

        System.out.print("mergeDelayError =>");
        /**
         *Combines multiple Observables into one. Any onError notifications passed from
         * any of the source observables will be withheld until all merged Observables
         * complete, and only then will be passed along to the observers.
         */


        Observable<String> observable1 = Observable.error(new IllegalArgumentException(""));
        Observable<String> observable2 = Observable.just("Four", "Five", "Six");
        Observable.mergeDelayError(observable1, observable2)
                .subscribe(item -> System.out.println(item));
        // emits 4, 5, 6 and then the IllegalArgumentException (in this specific
        // example, this throws an `OnErrorNotImplementedException`).

    }

    /**
     * Combines sets of items emitted by two or more Observables
     * together via a specified function and emit items based on the results of this function.
     */
    @Test
    public void test3() {
        Observable<String> firstNames = Observable.just("James", "Jean-Luc", "Benjamin", "more 1");
        Observable<String> lastNames = Observable.just("Kirk", "Picard", "Sisko");
        firstNames.zipWith(lastNames, (first, last) -> first + " " + last)
                .subscribe(item -> System.out.println(item));
    }

    /**
     * When an item is emitted by either of two Observables,
     * combine the latest item emitted by each Observable via a specified
     * function and emit items based on the results of this function.
     * 用于将两个Observale最近发射的数据已经Func2函数的规则进展组合
     */
    @Test
    public void test4() {
        Observable<Long> newsRefreshes = Observable.interval(100, TimeUnit.MILLISECONDS);
        Observable<Long> weatherRefreshes = Observable.interval(50, TimeUnit.MILLISECONDS);

        Observable.combineLatest(newsRefreshes, weatherRefreshes,
                (newsRefreshTimes, weatherRefreshTimes) ->
                        "Refreshed news " + newsRefreshTimes + " times and weather " + weatherRefreshTimes)
                .blockingSubscribe(item -> System.out.println(item));

    }

    /**
     * Convert an Observable that emits Observables into
     * a single Observable that emits the items emitted by the most-recently emitted of those Observables.
     */
    @Test
    public void test5() {
        Observable<Observable<String>> timeIntervals =
                Observable.interval(1, TimeUnit.SECONDS)
                        .map(ticks ->
                                Observable.interval(100, TimeUnit.MILLISECONDS).map(innerInterval -> "outer: " + ticks + " - inner: " + innerInterval));
        Observable.switchOnNext(timeIntervals)
                .blockingSubscribe(item -> System.out.println(item));

    }


    //定时器
    @Test
    public void test6() {
        Observable.interval(100, TimeUnit.MILLISECONDS).blockingSubscribe(item -> System.out.println(item));

    }


    /**
     * Observable：源Observable需要组合的Observable,这里我们姑且称之为目标Observable；
     * Func1：接收从源Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了源Obsrvable发射出来的数据的有效期；
     * Func1：接收目标Observable发射来的数据，并返回一个Observable，这个Observable的声明周期决定了目标Obsrvable发射出来的数据的有效期；
     * Func2：接收从源Observable和目标Observable发射出来的数据，并将这两个数据组合后返回。
     * <p>
     * 所以Join操作符的语法结构大致是这样的：
     * onservableA.join(observableB, 控制observableA发射数据有效期的函数， 控制observableB发射数据有效期的函数，两个observable发射数据的合并规则)
     * <p>
     * join操作符的效果类似于排列组合，把第一个数据源A作为基座窗口，他根据自己的节奏不断发射数据元素，
     * 第二个数据源B，每发射一个数据，我们都把它和第一个数据源A中已经发射的数据进行一对一匹配；
     * 举例来说，如果某一时刻B发射了一个数据“B”,此时A已经发射了0，1，2，3共四个数据，
     * 那么我们的合并操作就会把“B”依次与0,1,2,3配对，得到四组数据： [0, B] [1, B] [2, B] [3, B]
     */
    @Test
    public void test7() {
        Observable<Integer> left = Observable.range(1, 5000);
        Observable<Integer> right = Observable.range(5000, 2000);

        //时间窗口
        left.join(right, t -> Observable.timer(1, TimeUnit.SECONDS)
                , t -> Observable.timer(1, TimeUnit.SECONDS),
                (x, y) -> (x + ":" + y)).blockingSubscribe(System.out::println);

    }


    @Test
    public void test8() {
        //目标Observable
      /*  Observable<Integer> obs1 = Observable.create(
                observableEmitter -> {
                    for (int i = 1; i < 5; i++) {
                        observableEmitter.onNext(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );*/

        Observable<Integer> obs1=Observable.range(1, 5000);
        //join
        Observable.just("srcObs-").join(obs1,
                //接受从源Observable发射来的数据，并返回一个Observable，
                // 这个Observable的生命周期决定了源Observable发射出来数据的有效期
                s -> Observable.timer(3000, TimeUnit.MILLISECONDS),
                //接受从目标Observable发射来的数据，并返回一个Observable，
                // 这个Observable的生命周期决定了目标Observable发射出来数据的有效期
                s -> Observable.timer(2000, TimeUnit.MILLISECONDS),
                //接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据
                (x, y) -> (x + ":" + y))
                .blockingSubscribe(
                        o -> System.out.println("join:" + o)
                );



        //groupJoin
        Observable.just("srcObs-").groupJoin(obs1,
                //接受从源Observable发射来的数据，并返回一个Observable，
                // 这个Observable的生命周期决定了源Observable发射出来数据的有效期
                s -> Observable.timer(3000, TimeUnit.MILLISECONDS),
                //接受从目标Observable发射来的数据，并返回一个Observable，
                // 这个Observable的生命周期决定了目标Observable发射出来数据的有效期
                s -> Observable.timer(2000, TimeUnit.MILLISECONDS),
                //接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据
                 (s, integerObservable) -> Tuple2.apply(s,integerObservable.blockingLast()))
                .subscribe(
                        o -> System.out.println("groupJoin:" + o)
                );

    }


    /**
     * 将一个Observable分拆为一些Observables集合，它们中的每一个发射原始Observable的一个 子序列
     * ,哪个数据项由哪一个Observable发射是由一个函数判定 的，
     * 这个函数给每一项指定一个Key，Key相同的数据会被同一个Observable发射.
     */
    @Test
    public void test9() {
        Observable<Integer> newsRefreshes = Observable.range(1, 100);

        Observable<GroupedObservable<String, Integer>> groupedObservableObservable = newsRefreshes.groupBy(item -> (item % 2 == 0) ? "偶数组" : "奇数组");

        groupedObservableObservable.blockingSubscribe(t -> {
            System.out.println("group name:" + t.getKey());
            if (t.getKey().equalsIgnoreCase("奇数组")) {
                t.blockingSubscribe(integer -> {
                    System.out.println(t.getKey() + "'member: " + integer);

                });
            }
        });
    }

}
