package com.ais.brm.study.brmTest.guava;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import javax.annotation.Nullable;

/**
 * @author : weizc
 * @description: https://github.com/google/guava/wiki/OrderingExplained
 * @since 2019/1/22
 */
public class OrderingTest {

    @Test
    public void CreationTest() {
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

    }
}

class Foo {
    @Nullable
    String sortedBy;
    int notSortedBy;
}