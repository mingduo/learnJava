package com.ais.brm.study.brmTest.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-19</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class StringJoin {

    @Test
    public void join1() {
        String joinedString = String.join(", ", "How", "To", "Do", "In", "Java");
        System.out.println(joinedString);
    }


    @Test
    public void join2() {
        List<String> strList = Arrays.asList("How", "To", "Do", "In", "Java");

        String joinedString = String.join(", ", strList);

        System.out.println(joinedString);
    }

    @Test
    public void join3() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        joiner.add("How")
                .add("To")
                .add("Do")
                .add("In")
                .add("Java");

        System.out.println("joiner=>" + joiner);
    }

    @Test
    public void join4() {
        List<String> numbers = Arrays.asList("How", "To", "Do", "In", "Java");

        String joinedString = numbers
                .stream()
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(joinedString);
    }
}
