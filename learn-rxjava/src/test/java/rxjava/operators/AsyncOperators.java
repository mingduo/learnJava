package rxjava.operators;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 * @description:
 * @since 2019/1/1
 * @author : weizc 
 */
@Slf4j
public class AsyncOperators {

    /**
     * start( ) — create an Observable that emits the return value of a function
     */
    @Test
    public void test(){
        System.out.println("fromCallable");

        Observable.fromCallable(()->{
            for(int i=0;i<10;i++){
                log.info("print => "+i);
            }
            return "ok";
        }).blockingSubscribe(log::info);
    }

    /**
     * toAsync( ) or asyncAction( ) or asyncFunc( )
     * — convert a function or Action into an Observable that executes the function and emits its return value
     */
    @Test
    public void test2(){
        System.out.println("fromPublisher");

        Observable.fromPublisher( s -> s.onNext("ojbk")).blockingSubscribe(System.out::println);
    }
    @Test
    public void test3(){
        System.out.println("fromArray");

        Observable.fromArray("a","b").blockingSubscribe(System.out::println);
    }
    @Test
    public void test4(){
        System.out.println("fromIterable");

        Observable.fromIterable(Arrays.asList("a","b")).blockingSubscribe(System.out::println);
    }
    @Test
    public void test5(){
        System.out.println("fromFuture");

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Observable.fromFuture(service.submit(()->{
            log.info("futrue");
            return "done";

        }))
                .blockingSubscribe(log::info);

    }
}
