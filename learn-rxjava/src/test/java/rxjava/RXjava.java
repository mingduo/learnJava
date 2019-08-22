package rxjava;

import com.ais.brm.common.utils.CollectionUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date::Created in 2018-3-2</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RXjava {

    @Test
    public void test1() {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

    }


    @Test
    public void test2() {
        Flowable.just("1", "2").subscribe(new Subscriber<String>() {


            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete ");

            }

            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
                System.out.println("onSubscribe ");

            }

            @Override
            public void onNext(String strings) {
                System.out.println("same hello " + strings);
            }

        });
    }

    @Test
    public void test3() {

        Flowable.fromCallable(() -> {
            List<String> collect = IntStream.range(0, 10).boxed().map(t -> String.valueOf(t)).collect(Collectors.toList());
            return collect;
        }).subscribe(new Subscriber<List<String>>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("OnSubscribe start");
                subscription.request(Long.MAX_VALUE);
                System.out.println("OnSubscribe end");
            }

            @Override
            public void onNext(List<String> strings) {
                System.out.println("onNext " + strings);

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete end");

            }
        });
    }

    @Test
    public void test4() throws InterruptedException {

        Flowable.range(1, 1000000).buffer(500).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(new Subscriber<List<Integer>>() {

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("OnSubscribe start");
                s.request(Long.MAX_VALUE);
                System.out.println("OnSubscribe end");
            }

            @Override
            public void onNext(List<Integer> v) {
                System.out.println(v);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        });
        Thread.sleep(2000);
    }

    @Test
    public void test5() throws InterruptedException {


 /*       t -> {
            System.out.println(Thread.currentThread().getName() + "" + t);

        }*/

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                int i = 1;
                while (true) {
                    if (e.requested() == 0) {
                        Thread.sleep(2000);
                        continue;
                    }
                    System.out.println("emitter = [" + e.requested() + "]");
                    i++;
                    e.onNext("i am :" + i);

                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
                .subscribe(getSub());

        Thread.sleep(5000);


    }

    private Subscriber<String> getSub() {
        return new Subscriber<String>() {
            private Subscription sub;

            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(128);
                sub = subscription;
            }

            @Override
            public void onNext(String s) {
                try {
                    Thread.sleep(40);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
                sub.request(100);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Test
    public void test5_1() throws InterruptedException {
        String[] strArray = IntStream.range(0, 100).boxed().map(t -> t + "").toArray(String[]::new);
        Disposable subscribe = Flowable.fromArray(strArray
        ).map(t -> {
            System.out.println(Thread.currentThread().getName() + "=>" + t);
            return t;
        }).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.computation()).buffer(10).doOnNext(t -> {

            System.out.println(Thread.currentThread().getName() + "<=" + t);

        }).subscribe();
        Thread.sleep(2000);

    }

    @Test
    public void test6() throws InterruptedException {
        Flowable.just("ss", "23").subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(t -> System.out.println(t), t -> System.out.println(t), () -> System.out.println("on complete"));
        Thread.sleep(2000);
    }


    private Subscriber<String> getSubscriber() {
        return new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                // subscription.request(Long.MAX_VALUE);
                subscription.request(129);
            }

            @Override
            public void onNext(String t) {
                System.out.println("onNext " + t);

            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        };

    }


    private Callable<String> getTask() {
        return new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "" + new Random().nextInt();
            }
        };
    }

    @Test
    public void test7() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final FlowableEmitter<Integer> emitter) throws Exception {
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        System.out.println("取消订阅了噢噢噢噢");
                    }
                });


            }
        }, BackpressureStrategy.BUFFER); //增加了一个参数

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                //  s.request(20);//策略BUFFER ,消费能力20 结果只收到20条，但是数据依然在发射
                // s.request(0);//策略BUFFER ,消费能力0 结果收不到数据,但是数据依然在发射
                s.request(129);
                //策略DROP,消费能力20,结果只收到连续的20条,但是数据依然在发射
                //策略DROP,消费能力10000,结果数据128条之后，发现直接抛到844了，这说明默认缓冲区满了之后
                //129条没有能进入到缓冲区，为什么呢，因为缓冲区满了，库存就那么大，根据DROP策略，只能丢弃
                //简单的说,DROP可能导致新产生的事件被丢弃，收到的事件可能会断层
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError: " + t);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        /**
         * .doOnCancel(new Action() {
        @Override public void run() throws Exception {
        Log.d(TAG, "取消注册了");
        }
        })
         */

        flowable.doOnCancel(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("取消注册了");
            }
        })
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        System.out.println("您订阅了");
                    }
                })/*.subscribeOn(Schedulers.io())*/.observeOn(Schedulers.newThread()).subscribe(subscriber);
    }


    @Test
    public void threadSimple() throws InterruptedException {
        Flowable//流
                .fromCallable(new Callable<String>() {//子线程调用
                    public String call() throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                        return "true";
                    }
                })
                .subscribeOn(Schedulers.io())//io线程
                .observeOn(Schedulers.single())//单线程
                .subscribe(new Consumer<String>() {//主线程订阅

                    public void accept(String t) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(t);
                    }
                }, new Consumer<Throwable>() {

                    public void accept(Throwable t) throws Exception {
                        System.out.println(t);
                    }
                });
        Thread.sleep(2000);
    }


    @Test
    public void Tu() {
        List<Integer> integerList = IntStream.range(0, 101).boxed().collect(Collectors.toList());
        System.out.println(integerList);
        List<List> lists = CollectionUtils.split2parts(integerList, 20);
        System.out.println(lists);
    }


}
