package com.ais.brm.study.brmTest.guava;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 * https://github.com/google/guava/wiki/ServiceExplained
 *
 * @author :    weizc
 */
public class InitializeCollectionEx {
    public static void main(String[] args) {

        Map items = ImmutableMap.of("coin", 3, "glass", 4, "pencil", 1);

        items.entrySet()
                .stream()
                .forEach(System.out::println);

        List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi",
                "mandarin", "date", "quince");

        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }
}
