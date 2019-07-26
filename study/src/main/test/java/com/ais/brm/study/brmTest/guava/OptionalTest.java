package com.ais.brm.study.brmTest.guava;

import com.google.common.base.Optional;
import org.junit.Test;

import java.util.Set;

/**
 * 
 * @description: https://github.com/google/guava/wiki/UsingAndAvoidingNullExplained#optional
 * @since 2019/1/22
 * @author : weizc 
 */
public class OptionalTest {

    @Test
    public void  test(){
        Optional<String> of = Optional.of("123");
        System.out.println("of="+of.get());
        Optional<Object> fromNullable = Optional.fromNullable(null);
        System.out.println("fromNullable="+fromNullable.isPresent());

        System.out.println("or="+fromNullable.or(of).get());

        System.out.println("orNull="+fromNullable.orNull());

        Set<String> stringSet = of.asSet();
        System.out.println("asSet="+stringSet);

    }
}
