package com.ais.brm.study.brmTest.guava;

import com.google.common.base.Preconditions;
import org.junit.Test;

/**
 * 
 * @description: https://github.com/google/guava/wiki/PreconditionsExplained
 * @since 2019/1/22
 * @author : weizc 
 */
public class PreconditionsTest {

    @Test
    public void  test(){
        System.out.println("checkArgument");
        int i=15;
        Preconditions.checkArgument(i >= 10, "Argument was %s but expected nonnegative", i);

        System.out.println("checkNotNull");

        Preconditions.checkNotNull(i);
        System.out.println("checkState");

        Preconditions.checkState(true);

        System.out.println("checkElementIndex");

        int checkElementIndex = Preconditions.checkElementIndex(3, 5);

        System.out.println("checkElementIndex:"+checkElementIndex);


        System.out.println("checkPositionIndexes");

        int start=0,end=5,size=5;
        Preconditions.checkPositionIndexes(start,end,size);

    }
}
