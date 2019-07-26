package com.ais.brm.study.brmTest.jdk8.concurrent;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @description:
 * @since 2019/1/2
 * @author : weizc 
 */
public class TestCompletableFuture{
    /**
     * 在Java8中，CompletableFuture提供了非常强大的Future的扩展功能，
     * 可以帮助我们简化异步编程的复杂性，并且提供了函数式编程的能力，
     * 可以通过回调的方式处理计算结果，也提供了转换和组合 CompletableFuture 的方法。
     * 它可能代表一个明确完成的Future，也有可能代表一个完成阶段（ CompletionStage ）
     * ，它支持在计算完成以后触发一些函数或执行某些动作。
     * 它实现了Future和CompletionStage接口
     */
    @Test
    public void test(){
        //thenApply相当于回调函数（callback）

        CompletableFuture.supplyAsync(()->1).thenApply(i->i+1).thenApply(i->i * 5).whenComplete((i, throwable) -> System.out.println(i));

    }

    /**
     * 可以看到，thenAccept和thenRun都是无返回值的。
     * 如果说thenApply是不停的输入输出的进行生产，那么thenAccept和thenRun就是在进行消耗。它们是整个计算的最后两个阶段。
     * 同样是执行指定的动作，同样是消耗，二者也有区别：
     *
     * thenAccept接收上一阶段的输出作为本阶段的输入 　　
     *
     * thenRun根本不关心前一阶段的输出，根本不不关心前一阶段的计算结果，因为它不需要输入参数
     * thenCombine整合两个计算结果
     */
    @Test
    public void test2(){
        //thenApply相当于回调函数（callback）

        CompletableFuture.supplyAsync(()->1).thenApply(i->i+1).thenApply(i->i * 5)
                .thenCombineAsync(CompletableFuture.completedFuture("combine"),(s,s2)->s+s2)
                .thenAcceptAsync(System.out::println);

    }
}
