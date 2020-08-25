package common.buessiness.problems.java8;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

/**
 * 
 *  
 * @since 2020/8/17
 * @author : weizc 
 */
public class LambdaTest {

    @Test
    public void lambdavsanonymousclass(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();

        new Thread(() -> System.out.println("hello2")).start();

    }

    @Test
    public void functionalInterfaces() {
        //可以看一下java.util.function包//可以看一下java.util.function包
        Supplier<String> supplier=String::new;
        Supplier<String> stringSupplier=()->"OK";
        //Predicate的例子
        Predicate<Integer> positiveNumber=i->i>0;
        Predicate<Integer> evenNumber=i->i%2==0;
        Assert.assertTrue(positiveNumber.and(evenNumber).test(2));

        //Consumer的例子，输出两行abcdefg
        Consumer<String>println=System.out::println;
        println.andThen(println).accept("asdsd");

        //Function的例子
        Function<String,String> toUpperCase=String::toUpperCase;
        Function<String,String> duplicate=s->s.concat(s);
        Assert.assertTrue(toUpperCase.andThen(duplicate).apply("test").equals("TESTTEST"));

        //Supplier的例子
        Supplier<Integer> random=()-> ThreadLocalRandom.current().nextInt(10);
        System.out.println(random.get());

        //BinaryOperator
        BinaryOperator<Integer> sum=Integer::sum;
        BinaryOperator<Integer>subtract=(a,b)->a-b;
        Assert.assertThat(subtract.apply(sum.apply(1,2),3), CoreMatchers.is(0));
    }
}
