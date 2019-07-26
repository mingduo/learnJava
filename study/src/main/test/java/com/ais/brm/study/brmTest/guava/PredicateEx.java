package com.ais.brm.study.brmTest.guava;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class PredicateEx {
    @Test
    public void test1(){
        List<Integer> values = Lists.newArrayList(
                3, null, 4, 7,
                8, null, 7);

        Iterable<Integer> filtered = Iterables.filter(values,
                Predicates.notNull());


        for (Integer i: filtered) {
            System.out.println(i);
        }
    }

    @Test
    public void test2(){
        List<String> items = Lists.newArrayList(
                "coin", "book",
                "cup", "purse", "bottle");
        Collection<String> result  = Collections2.filter(items,
                Predicates.containsPattern("o"));

        for (String item: result) {
            System.out.println(item);
        }
    }
}
