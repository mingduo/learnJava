package com.ais.brm.study.brmTest.jdk8;

import java.time.Instant;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-12</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class  InterfaceTest {

     interface  A{
        static void test(){
            System.out.println("1");
        }
        default void test2(){
            System.out.println("A");
        }
    }
     interface  B{
        static void test(){
            System.out.println("b test...");
        }
        default void test2(){
            System.out.println("B");
        }
    }
     static class C implements A,B{

        @Override
        public void test2() {
            A.super.test2();
        }
    }
    @FunctionalInterface
    interface  D{
         void test();


    }

    public static void main(String[] args) throws InterruptedException {
        C c=new C();
        c.test2();

        Consumer<String>consumer= s -> System.out.println("s = [" + s + "]");
        consumer.accept("你是谁？");

        D d= () -> System.out.println(88);
        d.test();

        B.test();

        Instant now = Instant.now();
        LongAdder adder=new LongAdder();




    }


}
