package com.ais.brm.study.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * 
 * @description:
 * @since 2018/12/31
 * @author : weizc 
 */
@Slf4j
public class RxjavaTest {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> source = Flowable.fromCallable(() -> {
            sleep(1000); //  imitate expensive computation
            return "Done";
        });

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

        showForeground.subscribe(log::info, Throwable::printStackTrace);

        sleep(2000);
    }
}