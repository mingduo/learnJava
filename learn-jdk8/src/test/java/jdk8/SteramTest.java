package jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-19</td>
 * </tr>
 * </table>
 * https://howtodoinjava.com/java8/java-8-tutorial-streams-by-examples/
 *
 * @author :    weizc
 */
public class SteramTest {

    //Using Stream.of(val1, val2, val3….)
    @Test
    public void generate1() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        stream.forEach(p -> System.out.println(p));


    }

    // Using Stream.of(arrayOfElements)
    @Test
    public void generate2() {
        Stream<Integer> stream = Stream.of(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        stream.forEach(p -> System.out.println(p));


    }

    //Using someList.stream()
    @Test
    public void generate3() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        Stream<Integer> stream = list.stream();
        stream.forEach(p -> System.out.println(p));


    }

    //Using Stream.generate() or Stream.iterate() functions
    @Test
    public void generate4() {
        Stream<Date> stream = Stream.generate(() -> new Date());
        stream.forEach(p -> System.out.println(p));
    }
    //Using String chars or String tokens

    @Test
    public void generate5() {
        IntStream stream = "12345_abcdefg".chars();
        stream.forEach(p -> System.out.println(p));

        //OR

        Stream<String> stream2 = Stream.of("A$B$C".split("\\$"));
        stream2.forEach(p -> System.out.println(p));
    }
}
