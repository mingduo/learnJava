package com.ais.brm.study.brmTest.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;

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
public class StringJoinerSpliterEx {
    @Test
    public void join() {

        List<String> myList = Lists.newArrayList(
                "8", "2", "7", "10");

        String result = Joiner.on(",").join(myList);

        System.out.println(result);
    }

    @Test
    public void split() {
        String input = "There is a dog in the garden.";

        Iterable<String> words = Splitter.on(" ").split(input);

        for (String word: words) {
            System.out.println(word);
        }
    }

    @Test
    public void split2() {
        String input = "coin,  pencil  , chair, bottle, soap";

        Iterable<String> words = Splitter.on(",")
                .trimResults()
                .limit(3)
                .split(input);

        for (String word: words) {
            System.out.println(word);
        }
    }

    @Test
    public void split3() {
        String input = "a,,c,d";

        Iterable<String> words = Splitter.on(",")
                .omitEmptyStrings()
                .split(input);

        for (String word: words) {
            System.out.println(word);
        }
    }

    @Test
    public void split4() {
        String input = "_a ,_b_ ,c__";

        Iterable<String> words = Splitter.on(",")
                .trimResults(CharMatcher.is('_'))
                .split(input);

        for (String word: words) {
            System.out.println(word);
        }
    }


}
